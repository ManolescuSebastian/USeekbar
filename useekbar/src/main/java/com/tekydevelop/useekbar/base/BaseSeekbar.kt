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
package com.tekydevelop.useekbar.base

import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar

abstract class BaseSeekbar : AppCompatSeekBar, SeekBar.OnSeekBarChangeListener {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    )

    protected var steps = 1

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    protected fun addStepsIfRequired(progress: Int): Int {
        val stepProgress = ((progress / steps) * steps)
        return round(stepProgress)
    }

    private fun round(n: Int): Int {
        val firstNumber = n / steps * steps
        val secondNumber = firstNumber + steps
        return if (n - firstNumber > secondNumber - n) secondNumber else firstNumber
    }
}