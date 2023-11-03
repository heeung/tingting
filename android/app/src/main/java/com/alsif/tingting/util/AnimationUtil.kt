package com.alsif.tingting.util

import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait

private const val TAG = "AnimationUtil"
// 공통적으로 사용될 AnimUtil
object AnimUtil {
    enum class AnimDirection {
        X,
        Y,
        XY
    }
    object Speed {
        const val FAST: Long = 125
        const val COMMON: Long = 250
        const val SLOW: Long = 500
    }
    object Size {
        const val MIN: Float = 0f
        const val HALF: Float = 0.5f
        const val ORIGIN: Float = 1f
        const val MAX: Float = 2f
    }
}

/**
 * View에 크기 변경 애니메이션을 적용합니다.
 */
fun View.scaleAnimation(direction: AnimUtil.AnimDirection, values: Float, speed: Long) {
    when(direction) {
        AnimUtil.AnimDirection.X -> {
            ObjectAnimator.ofFloat(this, "scaleX", values).apply {
                duration = speed
                start()
            }
        }
        AnimUtil.AnimDirection.Y -> {
            ObjectAnimator.ofFloat(this, "scaleY", values).apply {
                duration = speed
                start()
            }
        }
        AnimUtil.AnimDirection.XY -> {
            ObjectAnimator.ofFloat(this, "scaleX", values).apply {
                duration = speed
                start()
            }
            ObjectAnimator.ofFloat(this, "scaleY", values).apply {
                duration = speed
                start()
            }
        }
    }
}

/**
 * View에 위치 이동 애니메이션을 적용합니다. (속도 직접 입력)
 */
fun View.translateAnimation(direction: AnimUtil.AnimDirection, values: Float, speed: Long) {
    when(direction) {
        AnimUtil.AnimDirection.X -> {
            ObjectAnimator.ofFloat(this, "translationX", values).apply {
                duration = speed
                start()
            }
        }
        AnimUtil.AnimDirection.Y -> {
            ObjectAnimator.ofFloat(this, "translationY", values).apply {
                duration = speed
                start()
            }
        }
        AnimUtil.AnimDirection.XY -> {
            // XY 동시에 일어나는 일은 없다.
        }
    }
}

/**
 * alpha 애니메이션을 적용합니다.
 */
fun View.alphaAnimation(values: Float, speed: Long) {
    ObjectAnimator.ofFloat(this, "alpha", values).apply {
        duration = speed
        start()
    }
}

/**
 * View에 클릭 애니메이션을 적용합니다.
 */
fun View.clickAnimation(lifeCycleOwner: LifecycleOwner) {
    lifeCycleOwner.lifecycleScope.launch {
        this@clickAnimation.scaleAnimation(AnimUtil.AnimDirection.XY, 0.9f, 100)
        delay(100)
        this@clickAnimation.scaleAnimation(AnimUtil.AnimDirection.XY, 1f, 80)
        delay(80)
    }
}

/**
 * View에 나타나는 에니메이션을 적용합니다.
 */
fun View.hideAnimation(lifeCycleOwner: LifecycleOwner) {
    lifeCycleOwner.lifecycleScope.launch {
        this@hideAnimation.alphaAnimation(0f, AnimUtil.Speed.FAST)
        delay(AnimUtil.Speed.FAST)
        this@hideAnimation.visibility = View.INVISIBLE
    }
}

/**
 * View에 나타나는 에니메이션을 적용합니다.
 */
fun View.revealAnimation(lifeCycleOwner: LifecycleOwner) {
    lifeCycleOwner.lifecycleScope.launch {
        this@revealAnimation.visibility = View.VISIBLE
        this@revealAnimation.alphaAnimation(1f, AnimUtil.Speed.FAST)
    }
}

/**
 * view를 원하는 크기로 초기 세팅
 */
fun View.setScaleXY(valueX: Float = 1f, valueY: Float = 1f) {
    this.scaleX = valueX
    this.scaleY = valueY
}

//fun View.expand() {
//    val animation = this.expandAction()
//    this.startAnimation(animation)
//}

fun View.expand() {
    this.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val actualHeight = this.measuredHeight

    this.layoutParams.height = 0
    this.visibility = View.VISIBLE

    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            this@expand.layoutParams.height = if (interpolatedTime == 1f)
                ViewGroup.LayoutParams.WRAP_CONTENT
            else (actualHeight * interpolatedTime).toInt()

            this@expand.requestLayout()
        }
    }
    animation.duration = (actualHeight / this.context.resources.displayMetrics.density * 1.5).toLong()
    Log.d(TAG, "expand: ${animation.duration}")

    this.startAnimation(animation)
}

fun View.collapse() {
    val actualHeight = this.measuredHeight

    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                this@collapse.visibility = View.GONE
            } else {
                this@collapse.layoutParams.height = (actualHeight - (actualHeight * interpolatedTime)).toInt()
                this@collapse.requestLayout()
            }
        }
    }

    animation.duration = (actualHeight / this.context.resources.displayMetrics.density).toLong()
    this.startAnimation(animation)
}

fun View.flipAnimatinon(isFliped: Boolean): Boolean {
    if (isFliped) {
        this.animate().setDuration(200).rotation(180f)
        return true
    } else {
        this.animate().setDuration(200).rotation(0f)
        return false
    }
}