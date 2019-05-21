package com.mab.infinityview

import android.content.Context
import android.graphics.*
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.View


/**
 * Created by Mirza Ahmed Baig on 2019-05-21.
 * Avantari Technologies
 * mirza@avantari.org
 */

class InfinityView : View {
    private var iCurStep = 0
    private var firstCompleted = false
    private var secondCompleted = false
    private var thirdCompleted = false
    private var fourthCompleted = false

    private var pointerRadius = 10f
    private var pointerColor = Color.WHITE
    private var infinityColor = Color.WHITE

    private var ovalPath: Path? = null
    private var arcPath: Path? = null
    private var arcPath2: Path? = null
    private var ovalPath2: Path? = null


    private val arcPaint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            color = infinityColor
            strokeWidth = 5f
            setShadowLayer(5.0f, 0.0f, 2.0f, -0x1000000)
        }
    }
    private val arcPaint2 by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            color = infinityColor
            strokeWidth = 5f
            setShadowLayer(5.0f, 0.0f, 2.0f, R.color.shadow)
        }
    }

    private val ovalPaint by lazy {
        Paint().apply {
            color = infinityColor
            strokeWidth = 5f
            setShadowLayer(5.0f, 0.0f, 2.0f, R.color.shadow)
            style = Paint.Style.STROKE
        }
    }
    private val ovalPaint2 by lazy {
        Paint().apply {
            color = infinityColor
            strokeWidth = 5f
            style = Paint.Style.STROKE
            setShadowLayer(5.0f, 0.0f, 2.0f, R.color.shadow)
        }
    }
    private val circlePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = pointerColor
            setShadowLayer(10.0f, 0.0f, 2.0f, R.color.shadow)
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, @Nullable attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        obtainAttributes(attrs)
    }


    private fun obtainAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val a = context.theme.obtainStyledAttributes(
                it,
                R.styleable.InfinityView,
                0, 0
            )

            try {
                infinityColor = a.getColor(R.styleable.InfinityView_infinity_color, infinityColor)
                pointerColor = a.getColor(R.styleable.InfinityView_pointer_color, pointerColor)
                pointerRadius = a.getFloat(R.styleable.InfinityView_pointer_radius, pointerRadius)
            } finally {
                a.recycle()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val width = width.toFloat()
        val height = height.toFloat()

        val widthFactor = width / 4 * 0.9f
        val heightFactor = height / 4

        ovalPath = Path().apply {
            moveTo(widthFactor, heightFactor * 3)
            cubicTo(2f, heightFactor * 3, 2f, heightFactor, widthFactor, heightFactor)
        }
        ovalPath2 = Path().apply {
            moveTo(widthFactor * 3, heightFactor * 3)
            cubicTo(
                widthFactor * 4 + 5,
                heightFactor * 3,
                widthFactor * 4 + 5,
                heightFactor,
                widthFactor * 3,
                heightFactor
            )
        }

        arcPath = Path().apply {
            moveTo(widthFactor, heightFactor)
            cubicTo(
                widthFactor * 2,
                heightFactor,
                widthFactor * 2,
                heightFactor * 3,
                widthFactor * 3,
                heightFactor * 3
            )
        }

        arcPath2 = Path().apply {
            moveTo(widthFactor * 3, heightFactor)
            cubicTo(
                widthFactor * 2,
                heightFactor,
                widthFactor * 2,
                heightFactor * 3,
                widthFactor,
                heightFactor * 3
            )
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(ovalPath!!, ovalPaint)
        canvas.drawPath(ovalPath2!!, ovalPaint2)
        canvas.drawPath(arcPath!!, arcPaint)
        canvas.drawPath(arcPath2!!, arcPaint2)
        animate(canvas)
    }

    private fun animate(canvas: Canvas) {
        if (!firstCompleted) {
            val pm = PathMeasure(ovalPath, false)
            val fSegmentLen = pm.length / 100
            val afP = floatArrayOf(0f, 0f)
            pm.getPosTan(fSegmentLen * iCurStep, afP, null)
            canvas.drawCircle(afP[0], afP[1], pointerRadius, circlePaint)
            if (iCurStep <= 100) {
                iCurStep++
                invalidate()
            } else {
                iCurStep = 0
                firstCompleted = true
                secondCompleted = false
                invalidate()
            }
        } else if (!secondCompleted) {
            val pm2 = PathMeasure(arcPath, false)
            val fSegmentLen = pm2.length / 100
            val afP = floatArrayOf(0f, 0f)
            pm2.getPosTan(fSegmentLen * iCurStep, afP, null)
            canvas.drawCircle(afP[0], afP[1], pointerRadius, circlePaint)
            if (iCurStep <= 100) {
                iCurStep++
                invalidate()
            } else {
                iCurStep = 0
                secondCompleted = true
                thirdCompleted = false
                invalidate()
            }
        } else if (!thirdCompleted) {
            val pm3 = PathMeasure(ovalPath2, false)
            val fSegmentLen = pm3.length / 100
            val afP = floatArrayOf(0f, 0f)
            pm3.getPosTan(fSegmentLen * iCurStep, afP, null)
            canvas.drawCircle(afP[0], afP[1], pointerRadius, circlePaint)
            if (iCurStep <= 100) {
                iCurStep++
                invalidate()
            } else {
                iCurStep = 0
                thirdCompleted = true
                fourthCompleted = false
                invalidate()
            }
        } else if (!fourthCompleted) {
            val pm4 = PathMeasure(arcPath2, false)
            val fSegmentLen = pm4.length / 100
            val afP = floatArrayOf(0f, 0f)
            pm4.getPosTan(fSegmentLen * iCurStep, afP, null)
            canvas.drawCircle(afP[0], afP[1], pointerRadius, circlePaint)
            if (iCurStep <= 100) {
                iCurStep++
                invalidate()
            } else {
                iCurStep = 0
                fourthCompleted = true
                firstCompleted = false
                invalidate()
            }
        }
    }
}