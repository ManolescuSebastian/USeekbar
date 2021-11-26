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
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.tekydevelop.useekbar.base.BaseSeekbar

class SeekbarD0 : BaseSeekbar {

    //Paint
    private var textPaint = TextPaint()

    //Attributes Properties
    private var textColor = ContextCompat.getColor(context, R.color.default_color)
    private var tooltipTextSize = resources.getDimension(R.dimen.medium_text)
    private var topTextAreaPadding = resources.getDimensionPixelSize(R.dimen.val_25dp)

    private var showTooltipText = true
    private var tooltipTextWidth: Float = 0.0f
    private val tooltipArea = Rect()

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
        tooltipTextSize = a.getDimension(
            R.styleable.SeekBarWithHint_tooltipTextSize,
            resources.getDimension(R.dimen.medium_text)
        )
        topTextAreaPadding = a.getDimensionPixelSize(
            R.styleable.SeekBarWithHint_topTextAreaPadding,
            resources.getDimensionPixelSize(R.dimen.val_25dp)
        )
        showTooltipText = a.getBoolean(R.styleable.SeekBarWithHint_showTooltipText, true)
        steps = a.getColor(R.styleable.SeekBarWithHint_steps, 1)
    }

    private fun init() {
        initDefaultAttributes()
        initPaintProperties()

        //init change progress listener
        setOnSeekBarChangeListener(this)

        tooltipTextWidth = textPaint.measureText(progress.toString())
        textPaint.getTextBounds(progress.toString(), 0, progress.toString().length, tooltipArea)
    }

    private fun initPaintProperties() {
        // Text paint
        textPaint.flags = Paint.ANTI_ALIAS_FLAG
        textPaint.color = textColor
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = tooltipTextSize
    }

    private fun initDefaultAttributes() {
        if (showTooltipText) {
            setPadding(paddingLeft, topTextAreaPadding, paddingRight, paddingBottom)
        }
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!showTooltipText) {
            return
        }

        // this method doesn't show the center of the thumb, it gets the left bounds value
        val thumbX = thumb.current.bounds.centerX().toFloat()
        val thumbY = tooltipArea.height().toFloat()

        val centerX = trackCenterX(thumbX)
        drawText(canvas, centerX, thumbY)
    }

    /**
     *  Add tooltip text offset in order to avoid overlap
     *  on the right side of the screen
     *
     *  startOfTooltip - left limit that contains the thumb X position and the tooltip width
     *  endOfTooltip - right limit that contains the thumb X position and the tooltip width
     */
    private fun trackCenterX(thumbX: Float): Float {
        val startOfTooltip = thumbX - tooltipTextWidth / 2 + paddingLeft - thumb.minimumWidth / 2
        val endOfTooltip = thumbX + tooltipTextWidth / 2 + paddingRight - thumb.minimumWidth / 2

        return when {
            startOfTooltip <= paddingLeft -> {
                thumbX - startOfTooltip + paddingLeft + thumb.minimumWidth / 6
            }
            endOfTooltip >= width - paddingRight -> {
                thumbX - (endOfTooltip - width) - (thumb.minimumWidth / 2) + (thumb.minimumWidth / 4)
            }
            else -> {
                thumbX + (thumb.minimumWidth / 2)
            }
        }
    }

    /**
     * Draw tooltip text
     */
    private fun drawText(canvas: Canvas, x: Float, y: Float) {
        canvas.drawText(progress.toString(), x, y, textPaint)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        this.progress = addStepsIfRequired(this.progress)
        tooltipTextWidth = textPaint.measureText(progress.toString())
    }
}