package com.savebytes.campusbuddy.presentation.screens

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.savebytes.campusbuddy.MainActivity
import com.savebytes.campusbuddy.R
import com.savebytes.campusbuddy.core.constants.AppColor
import com.savebytes.campusbuddy.databinding.ActivityOnboardingBinding
import com.savebytes.campusbuddy.presentation.adapters.OnboardingPagerAdapter

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private val indicators = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupIndicators()
        setupViewPager()
        setupClickListeners()
    }

    private fun setupIndicators() {
        indicators.apply {
            add(binding.indicator1)
            add(binding.indicator2)
            add(binding.indicator3)
        }

        // Set initial state
        animateIndicator(0, -1)
    }

    private fun setupViewPager() {
        val adapter = OnboardingPagerAdapter()
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            private var previousPosition = 0

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                animateIndicator(position, previousPosition)
                previousPosition = position
                binding.btnNext.text = if (position == 2) "Get Started" else "Next"
            }
        })
    }

    private fun animateIndicator(newPosition: Int, oldPosition: Int) {
        val activeWidth = resources.getDimensionPixelSize(R.dimen.indicator_active_width)
        val inactiveWidth = resources.getDimensionPixelSize(R.dimen.indicator_inactive_size)
        val activeColor = AppColor.PrimaryActiveColor
        val inactiveColor = AppColor.PrimaryInactiveColor


        indicators.forEachIndexed { index, indicator ->
            when (index) {
                newPosition -> {
                    // Animate to active state
                    animateIndicatorToActive(
                        indicator,
                        inactiveWidth,
                        activeWidth,
                        inactiveColor,
                        activeColor
                    )
                }
                oldPosition -> {
                    // Animate to inactive state
                    animateIndicatorToInactive(
                        indicator,
                        activeWidth,
                        inactiveWidth,
                        activeColor,
                        inactiveColor
                    )
                }
                else -> {
                    // Set to inactive state immediately
                    setIndicatorInactive(indicator, inactiveWidth, inactiveColor)
                }
            }
        }
    }

    private fun animateIndicatorToActive(
        view: View,
        startWidth: Int,
        endWidth: Int,
        startColor: Int,
        endColor: Int
    ) {
        val widthAnimator = ValueAnimator.ofInt(startWidth, endWidth)
        val colorAnimator = ValueAnimator.ofArgb(startColor, endColor)

        widthAnimator.duration = 300
        colorAnimator.duration = 300

        widthAnimator.interpolator = AccelerateDecelerateInterpolator()
        colorAnimator.interpolator = AccelerateDecelerateInterpolator()

        widthAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.width = value
            view.layoutParams = layoutParams
        }

        colorAnimator.addUpdateListener { animation ->
            val color = animation.animatedValue as Int
            val drawable = android.graphics.drawable.GradientDrawable()
            drawable.shape = android.graphics.drawable.GradientDrawable.RECTANGLE
            drawable.setColor(color)
            drawable.cornerRadius = resources.getDimension(R.dimen.indicator_corner_radius)
            view.background = drawable
        }

        widthAnimator.start()
        colorAnimator.start()
    }

    private fun animateIndicatorToInactive(
        view: View,
        startWidth: Int,
        endWidth: Int,
        startColor: Int,
        endColor: Int
    ) {
        val widthAnimator = ValueAnimator.ofInt(startWidth, endWidth)
        val colorAnimator = ValueAnimator.ofArgb(startColor, endColor)

        widthAnimator.duration = 300
        colorAnimator.duration = 300

        widthAnimator.interpolator = AccelerateDecelerateInterpolator()
        colorAnimator.interpolator = AccelerateDecelerateInterpolator()

        widthAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.width = value
            view.layoutParams = layoutParams
        }

        colorAnimator.addUpdateListener { animation ->
            val color = animation.animatedValue as Int
            val drawable = android.graphics.drawable.GradientDrawable()
            drawable.shape = android.graphics.drawable.GradientDrawable.OVAL
            drawable.setColor(color)
            view.background = drawable
        }

        widthAnimator.start()
        colorAnimator.start()
    }

    private fun setIndicatorInactive(view: View, width: Int, color: Int) {
        val layoutParams = view.layoutParams
        layoutParams.width = width
        view.layoutParams = layoutParams

        val drawable = android.graphics.drawable.GradientDrawable()
        drawable.shape = android.graphics.drawable.GradientDrawable.OVAL
        drawable.setColor(color)
        view.background = drawable
    }

    private fun setupClickListeners() {
        binding.tvSkip.setOnClickListener {
            finishOnboarding()
        }

        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < 2) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                finishOnboarding()
            }
        }
    }

    private fun finishOnboarding() {
        // Save onboarding completed preference
        // Navigate to main activity
        finish()
        startActivity(Intent(this, AuthActivity::class.java))
    }
}