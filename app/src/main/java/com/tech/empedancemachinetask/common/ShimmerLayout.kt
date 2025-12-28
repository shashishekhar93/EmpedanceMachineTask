package com.tech.empedancemachinetask.common

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.graphics.withTranslation
import com.tech.empedancemachinetask.R

class ShimmerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = true }
    private var shimmerLocation = 0f
    private var isShimmering = true

    // Customizable attributes
    private var baseColor = Color.LTGRAY
    private var highlightColor = Color.WHITE
    private var shimmerWidth = dpToPx(200f)
    private var shimmerDuration = 1500L
    private var angleDegrees = 20f

    private val shimmerAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = shimmerDuration
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
    }

    init {
        setWillNotDraw(false)
        setupAttributes(attrs)
        startShimmerAnimation()
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ShimmerLayout, 0, 0).use {
            baseColor = it.getColor(R.styleable.ShimmerLayout_shimmerBaseColor, baseColor)
            highlightColor =
                it.getColor(R.styleable.ShimmerLayout_shimmerHighlightColor, highlightColor)
            shimmerWidth = it.getDimension(R.styleable.ShimmerLayout_shimmerWidth, shimmerWidth)
            shimmerDuration =
                it.getInt(R.styleable.ShimmerLayout_shimmerDuration, shimmerDuration.toInt())
                    .toLong()
            angleDegrees = it.getFloat(R.styleable.ShimmerLayout_shimmerAngle, angleDegrees)
        }
    }

    private fun startShimmerAnimation() {
        shimmerAnimator.addUpdateListener { animation ->
            shimmerLocation = animation.animatedValue as Float
            invalidate()
        }
        shimmerAnimator.start()
    }

    fun startShimmer() {
        if (!isShimmering) {
            isShimmering = true
            shimmerAnimator.start()
        }
    }

    fun stopShimmer() {
        if (isShimmering) {
            isShimmering = false
            shimmerAnimator.cancel()
            invalidate()
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas) // Draw children first
        if (isShimmering) {
            drawShimmer(canvas) // Then draw shimmer on top
        }
    }

    private fun drawShimmer(canvas: Canvas) {
        val width = measuredWidth.toFloat()
        val height = measuredHeight.toFloat()

        // Calculate the translation based on the animated value (0 to 1)
        val totalWidth = width + shimmerWidth
        val dx = -shimmerWidth + totalWidth * shimmerLocation

        val shader = LinearGradient(
            0f, 0f, shimmerWidth, 0f,
            intArrayOf(Color.TRANSPARENT, highlightColor, Color.TRANSPARENT),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP
        )
        paint.shader = shader

        canvas.withTranslation(dx, 0f) {
            drawRect(0f, 0f, shimmerWidth, height, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var maxWidth = 0
        var maxHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
                val lp = child.layoutParams as MarginLayoutParams
                maxWidth = maxOf(maxWidth, child.measuredWidth + lp.leftMargin + lp.rightMargin)
                maxHeight = maxOf(maxHeight, child.measuredHeight + lp.topMargin + lp.bottomMargin)
            }
        }

        maxWidth += paddingLeft + paddingRight
        maxHeight += paddingTop + paddingBottom

        setMeasuredDimension(
            resolveSize(maxOf(maxWidth, suggestedMinimumWidth), widthMeasureSpec),
            resolveSize(maxOf(maxHeight, suggestedMinimumHeight), heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val parentLeft = paddingLeft
        val parentTop = paddingTop

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                val lp = child.layoutParams as MarginLayoutParams
                val left = parentLeft + lp.leftMargin
                val top = parentTop + lp.topMargin
                child.layout(left, top, left + child.measuredWidth, top + child.measuredHeight)
            }
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?) = MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams() =
        MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

    override fun generateLayoutParams(p: LayoutParams?) = MarginLayoutParams(p)

    override fun checkLayoutParams(p: LayoutParams?) = p is MarginLayoutParams

    private fun dpToPx(dp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}
