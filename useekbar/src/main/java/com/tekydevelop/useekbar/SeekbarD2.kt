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
import androidx.core.content.res.getColorOrThrow

import com.tekydevelop.useekbar.base.BaseSeekbar
import com.tekydevelop.useekbar.util.Constants

class SeekbarD2 : BaseSeekbar {

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

    //tooltip background
    private var tooltipBackgroundColor = 0
    private var tooltipBackgroundColorFill = 0


    //Background
    private val tooltipRectF = RectF()
    private val tooltipRect = Rect()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.SeekBarWithHint, 0, 0)
        recycleAttributes(a)
        init()
    }

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
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
        textColor = a.getColor(R.styleable.SeekBarWithHint_textColor, ContextCompat.getColor(context, R.color.default_color))
        textSize = a.getDimension(R.styleable.SeekBarWithHint_tooltipTextSize, resources.getDimension(R.dimen.medium_text))
        topTextAreaPadding = a.getDimensionPixelSize(R.styleable.SeekBarWithHint_topTextAreaPadding, resources.getDimensionPixelSize(R.dimen.val_30dp))
        showTooltipText = a.getBoolean(R.styleable.SeekBarWithHint_showTooltipText, true)

        //Background
        tooltipBackgroundColor = a.getColorOrThrow(R.styleable.SeekBarWithHint_backgroundTooltipColor)
        tooltipBackgroundColorFill = a.getColorOrThrow(R.styleable.SeekBarWithHint_backgroundTooltipColorFill)
        steps = a.getColor(R.styleable.SeekBarWithHint_steps, 1)
    }

    private fun init() {
        initDefaultAttributes()
        initPaintProperties()

        //init change progress listener
        setOnSeekBarChangeListener(this)

        tooltipWidth = tooltipPaint.measureText(progress.toString())

        hideThumb()
    }

    private fun hideThumb(){
        thumb.alpha = 0
        thumb.clearColorFilter()
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

        val x = thumb.current.bounds.centerX().toFloat()

        /**
         *  Add tooltip text offset in order to avoid overlap
         *  on the right side of the screen
         *
         *  startOfTooltip - left limit that contains the thumb X position and the tooltip width
         *  endOfTooltip - right limit that contains the thumb X position and the tooltip width
         */
        val startOfTooltip = x - tooltipWidth / 2 + paddingLeft - thumb.minimumWidth / 2
        val endOfTooltip = x + tooltipWidth / 2 + paddingRight - thumb.minimumWidth / 2

        tooltipPaint.getTextBounds(progress.toString(), 0, progress.toString().length, tooltipRect)
        val tooltipHeightBounds = tooltipRect.height()

        when {
            startOfTooltip < paddingLeft -> {
                val centerX = x - startOfTooltip + paddingLeft + thumb.minimumWidth / 6

                drawOutline(canvas, centerX - tooltipWidth / 2, centerX + tooltipWidth / 2, tooltipHeightBounds)
                drawText(canvas, centerX, tooltipHeightBounds)
            }
            endOfTooltip > width - paddingRight -> {
                val centerX = x - (endOfTooltip - width) - (thumb.minimumWidth / 2) + (thumb.minimumWidth / 3)

                drawOutline(canvas, centerX - tooltipWidth / 2, centerX + tooltipWidth / 2, tooltipHeightBounds)
                drawText(canvas, centerX, tooltipHeightBounds)
            }
            else -> {
                val centerX = x + (thumb.minimumWidth / 2) - thumb.minimumWidth / 8

                drawOutline(canvas, startOfTooltip, endOfTooltip, tooltipHeightBounds)
                drawText(canvas, centerX, tooltipHeightBounds)
            }
        }
    }

    /**
     * Draw tooltip text
     */
    private fun drawText(canvas: Canvas, x: Float, tooltipHeightBounds: Int) {
        canvas.drawText(progress.toString(), x, height / 2f + tooltipHeightBounds / 2f, tooltipPaint)
    }

    /**
     * Draw rectangle around tooltip
     */
    private fun drawOutline(canvas: Canvas, left: Float, right: Float, tooltipHeightBounds: Int) {
        val offset = tooltipHeightBounds / 3

        tooltipRectF.set(left - offset, height / 2f + tooltipHeightBounds, right + offset, height / 2f - tooltipHeightBounds)
        canvas.drawRoundRect(tooltipRectF, 10f, 10f, tooltipBackgroundPaintFill)
        canvas.drawRoundRect(tooltipRectF, 10f, 10f, tooltipBackgroundPaint)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        this.progress = addStepsIfRequired(progress)
        tooltipWidth = tooltipPaint.measureText(progress.toString())
    }
}