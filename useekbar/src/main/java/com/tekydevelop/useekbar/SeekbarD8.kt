/*
 * Copyright (C) 2021 tekydevelop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tekydevelop.useekbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.content.res.getBooleanOrThrow
import androidx.core.content.res.getColorOrThrow
import com.tekydevelop.useekbar.base.BaseSeekbar
import com.tekydevelop.useekbar.util.Constants

class SeekbarD8 : BaseSeekbar {

    //Paint
    private var tooltipPaint = TextPaint()
    private var tooltipBackgroundPaint = TextPaint()
    private var tooltipBackgroundPaintFill = TextPaint()

    //Attributes Properties
    private var textColor = ContextCompat.getColor(context, R.color.default_color)
    private var textSize = resources.getDimension(R.dimen.medium_text)
    private var topTextAreaPadding = resources.getDimensionPixelSize(R.dimen.val_30dp)

    private var tooltipWidth: Float = 0f

    private var showTooltipText = true
    private var botDisplayContent = false

    //tooltip background
    private var tooltipBackgroundColor = 0
    private var tooltipBackgroundColorFill = 0

    //Background
    private val tooltipRect = Rect()
    private val segmentRectF = RectF()

    private var showTextOnBottom = false
    private var textHeight = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.SeekBarWithHint, 0, 0)
        recycleAttributes(a)
        init()
    }

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.SeekBarWithHint, 0, 0)
        recycleAttributes(a)
        init()
    }

    private fun recycleAttributes(a: TypedArray) {
        try {
            attributes(a)
        } finally {
            a.recycle()
        }
    }

    private fun attributes(a: TypedArray) {
        // Default attributes
        textColor = a.getColor(
            R.styleable.SeekBarWithHint_textColor,
            ContextCompat.getColor(context, R.color.default_color)
        )
        textSize = a.getDimension(
            R.styleable.SeekBarWithHint_tooltipTextSize,
            resources.getDimension(R.dimen.medium_text)
        )
        topTextAreaPadding = a.getDimensionPixelSize(
            R.styleable.SeekBarWithHint_topTextAreaPadding,
            resources.getDimensionPixelSize(R.dimen.val_30dp)
        )
        showTooltipText = a.getBoolean(R.styleable.SeekBarWithHint_showTooltipText, true)

        //Background
        tooltipBackgroundColor =
            a.getColorOrThrow(R.styleable.SeekBarWithHint_backgroundTooltipColor)
        tooltipBackgroundColorFill =
            a.getColorOrThrow(R.styleable.SeekBarWithHint_backgroundTooltipColorFill)
        steps = a.getColor(R.styleable.SeekBarWithHint_steps, 1)

        // Custom attributes
        showTextOnBottom = a.getBooleanOrThrow(R.styleable.SeekBarWithHint_showTextOnBottom)
        botDisplayContent = a.getBoolean(R.styleable.SeekBarWithHint_botText, false)
    }

    private fun init() {
        initDefaultAttributes()
        initPaintProperties()

        //init change progress listener
        setOnSeekBarChangeListener(this)

        tooltipWidth = tooltipPaint.measureText(progress.toString())
        textHeight = getTextHeight()

        if (botDisplayContent) {
            setPadding(paddingLeft, textHeight, paddingRight, textHeight * 2)
        } else {
            setPadding(paddingLeft, textHeight * 2, paddingRight, textHeight)
        }
    }

    private fun initPaintProperties() {
        // Text paint
        tooltipPaint.flags = Paint.ANTI_ALIAS_FLAG
        tooltipPaint.color = textColor
        tooltipPaint.textAlign = Paint.Align.CENTER
        tooltipPaint.textSize = textSize

        // Background text paint
        tooltipBackgroundPaint.flags = Paint.ANTI_ALIAS_FLAG
        tooltipBackgroundPaint.color = tooltipBackgroundColor
        tooltipBackgroundPaint.style = Paint.Style.STROKE
        tooltipBackgroundPaint.strokeWidth = Constants.SHAPE_STROKE_WIDTH

        // Background text paint fill
        tooltipBackgroundPaintFill.flags = Paint.ANTI_ALIAS_FLAG
        tooltipBackgroundPaintFill.color = tooltipBackgroundColorFill
        tooltipBackgroundPaintFill.style = Paint.Style.FILL
    }

    fun showThumb() {
        thumb.alpha = 0
        thumb.clearColorFilter()
    }

    private fun initDefaultAttributes() {
        if (textColor == 0) {
            textColor = ContextCompat.getColor(context, R.color.default_color)
        }
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!showTooltipText) {
            return
        }

        val x = thumb.current.bounds.exactCenterX()
        val y = thumb.current.bounds.exactCenterY()

        val boundsWidth: Float = thumb.intrinsicWidth - thumbOffset.toFloat()

        val sections = (width - paddingLeft - paddingRight) / max
        var currentSectionValue = 0f

        for (i in 0..max) {
            currentSectionValue = if (i == 0) {
                paddingLeft.toFloat()
            } else {
                currentSectionValue + sections
            }

            if (progress / steps <= i - 1) {
                tooltipPaint.color = tooltipBackgroundColorFill
            } else {
                tooltipPaint.color = tooltipBackgroundColor
            }

            segmentRectF.set(
                currentSectionValue,
                height / 2f + boundsWidth / 2,
                currentSectionValue,
                height / 2f - boundsWidth / 2
            )

            // offset correction - if we remove this part each added segment will have a small incremented offset
            currentSectionValue += Constants.OFFSET_CORRECTION

            if (botDisplayContent) {
                canvas.drawText(
                    (i).toString(),
                    currentSectionValue,
                    textHeight.toFloat() * 4,
                    tooltipPaint
                )
            } else {
                canvas.drawText(
                    (i).toString(),
                    currentSectionValue,
                    textHeight.toFloat(),
                    tooltipPaint
                )
            }
        }
    }

    private fun getTextHeight(): Int {
        tooltipPaint.getTextBounds(progress.toString(), 0, progress.toString().length, tooltipRect)
        return tooltipRect.height()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        this.progress = addStepsIfRequired(progress)
        tooltipWidth = tooltipPaint.measureText(progress.toString())
    }
}