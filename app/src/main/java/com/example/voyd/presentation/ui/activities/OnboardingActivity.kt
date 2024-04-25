package com.example.voyd.presentation.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.voyd.R
import com.example.voyd.databinding.ActivityOnboardingBinding
import com.example.voyd.domain.models.OnBoardingData
import com.example.voyd.presentation.adapter.OnBoardingViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class OnBoardingActivity : AppCompatActivity() {

    private var _binding: ActivityOnboardingBinding? = null
    private val binding get() = _binding!!

    private lateinit var skipClick: TextView
    private lateinit var sliderDot: TabLayout
    private var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    private var onBoardingViewPager: ViewPager? = null
    private lateinit var button: Button
    private lateinit var getStartedButton: Button
    private var position = 0
    private lateinit var onBoardingDataList: MutableList<OnBoardingData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.VoydSplashTheme)
        _binding = ActivityOnboardingBinding.inflate(layoutInflater)

        //The activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //when this activity is about to be launched we need to check if
        // the activity has been seen by the user
        if (restorePrefData()) {
            val intent = Intent(applicationContext, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(binding.root)

        // initializing the views
        skipClick = binding.onBoardingActivitySkipTextView
        onBoardingViewPager = binding.screenPager
        sliderDot = binding.onBoardingActivityTabLayout
        button = binding.activityNextButton
        getStartedButton = binding.activityGetStartedButton

        skipClick.setOnClickListener {
            startNewActivity()
        }
        getStartedButton.setOnClickListener {
            startNewActivity()
        }

        setOnBoardingViewPagerAdapter()

        // Next button click listener
        button.setOnClickListener {
            position = onBoardingViewPager!!.currentItem
            if (position < onBoardingDataList.size) {
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if (position == onBoardingDataList.size - 1) { //when we reach the last screen
                loadLastScreen()
            }
        }

        //Tab indicator listener
        sliderDot.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == onBoardingDataList.size - 1) {
                    loadLastScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

    private fun restorePrefData(): Boolean {
        val pref: SharedPreferences = application.getSharedPreferences("MyPref", MODE_PRIVATE)
        val isIntroActivitySeenBefore: Boolean = pref.getBoolean("IsIntroActivityOpened", false)
        return isIntroActivitySeenBefore

    }

    private fun startNewActivity() {
        //open new activity
        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)

        //we need to save a value to storage so next time the user run the app
        // we could know that he already checked the intro activity
        // I will will use shared preferences for this.
        sharedPrefsData()
        finish()

    }

    private fun sharedPrefsData() {
        val pref: SharedPreferences = application.getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean("IsIntroActivityOpened", true)
        editor.commit()
    }

    // create a function outside the onCreate to bind the variables to the actual views
    private fun setOnBoardingViewPagerAdapter() {
        onBoardingDataList = ArrayList()
        onBoardingDataList.add(
            OnBoardingData(
                getString(R.string.onboarding_txt1),
                R.drawable.onboarding_img1
            )
        )
        onBoardingDataList.add(
            OnBoardingData(
                getString(R.string.onboarding_txt2),
                R.drawable.onboarding_img2
            )
        )

        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingDataList)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        sliderDot.setupWithViewPager(onBoardingViewPager, true)
    }

    private fun loadLastScreen() {
        button.visibility = View.INVISIBLE
        getStartedButton.visibility = View.VISIBLE
        sliderDot.visibility = View.INVISIBLE
        skipClick.visibility = View.INVISIBLE

    }


}