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
import com.tekydevelop.useekbar.base.BaseSeekbar
import com.tekydevelop.useekbar.util.Constants

class SeekbarD1 : BaseSeekbar {

    //Paint
    private var textPaint = TextPaint()
    private var tooltipBackgroundPaint = TextPaint()

    //Attributes Properties
    private var textColor = ContextCompat.getColor(context, R.color.default_color)
    private var textSize = resources.getDimension(R.dimen.medium_text)
    private var topTextAreaPadding = resources.getDimensionPixelSize(R.dimen.val_25dp)

    private var tooltipWidth: Float = 0.0f
    private var tooltipHeightBounds: Int = 0

    //default step is 1
    private var showTooltipText = true

    //tooltip background
    private var tooltipBackgroundColor = 0
    private val tooltipRectF = RectF()

    //Background
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
        topTextAreaPadding = a.getDimensionPixelSize(R.styleable.SeekBarWithHint_topTextAreaPadding, resources.getDimensionPixelSize(R.dimen.val_25dp))
        showTooltipText = a.getBoolean(R.styleable.SeekBarWithHint_showTooltipText, true)

        //Background
        tooltipBackgroundColor = a.getColor(R.styleable.SeekBarWithHint_backgroundTooltipColor, ContextCompat.getColor(context, R.color.default_color))

        // Properties
        steps = a.getColor(R.styleable.SeekBarWithHint_steps, 1)
    }

    private fun init() {
        initDefaultAttributes()
        initPaintProperties()

        //init change progress listener
        setOnSeekBarChangeListener(this)

        tooltipWidth = textPaint.measureText(progress.toString())
        textPaint.getTextBounds(progress.toString(), 0, progress.toString().length, tooltipRect)
        tooltipHeightBounds = tooltipRect.height()
    }

    private fun initPaintProperties() {
        // Text paint
        textPaint.flags = Paint.ANTI_ALIAS_FLAG
        textPaint.color = textColor
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = textSize

        // Background text paint
        tooltipBackgroundPaint.flags = Paint.ANTI_ALIAS_FLAG
        tooltipBackgroundPaint.color = tooltipBackgroundColor
        tooltipBackgroundPaint.style = Paint.Style.STROKE
        tooltipBackgroundPaint.strokeWidth = Constants.SHAPE_STROKE_WIDTH
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
        val y = tooltipHeightBounds.toFloat() + tooltipHeightBounds / 3 + Constants.SHAPE_STROKE_WIDTH / 2

        /**
         *  Add tooltip text offset in order to avoid overlap
         *  on the right side of the screen
         *
         *  startOfTooltip - left limit that contains the thumb X position and the tooltip width
         *  endOfTooltip - right limit that contains the thumb X position and the tooltip width
         */
        val startOfTooltip = x - tooltipWidth / 2 + paddingLeft - thumb.minimumWidth / 2
        val endOfTooltip = x + tooltipWidth / 2 + paddingRight - thumb.minimumWidth / 2

        when {
            startOfTooltip < paddingLeft -> {
                val centerX = x - startOfTooltip + paddingLeft + thumb.minimumWidth / 6

                drawText(canvas, centerX, y)
                drawOutline(canvas, centerX - tooltipWidth / 2, centerX + tooltipWidth / 2, y)
            }
            endOfTooltip > width - paddingRight -> {
                val centerX = x - (endOfTooltip - width) - (thumb.minimumWidth / 2) + (thumb.minimumWidth / 3)

                drawText(canvas, centerX, y)
                drawOutline(canvas, centerX - tooltipWidth / 2, centerX + tooltipWidth / 2, y)
            }
            else -> {
                val centerX = x + (thumb.minimumWidth / 2) - thumb.minimumWidth / 8

                drawText(canvas, centerX, y)
                drawOutline(canvas, startOfTooltip, endOfTooltip, y)
            }
        }
    }

    /**
     * Draw tooltip text
     */
    private fun drawText(canvas: Canvas, x: Float, y: Float) {
        canvas.drawText(progress.toString(), x, y, textPaint)
    }

    /**
     * Draw rectangle around tooltip
     */
    private fun drawOutline(canvas: Canvas, left: Float, right: Float, y: Float) {
        val offset = tooltipHeightBounds / 3

        tooltipRectF.set(left - offset, y - tooltipHeightBounds - offset, right + offset, y + offset / 3 + offset)
        canvas.drawRoundRect(tooltipRectF, 10f, 10f, tooltipBackgroundPaint)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        this.progress = addStepsIfRequired(progress)
        tooltipWidth = textPaint.measureText(progress.toString())
    }
}