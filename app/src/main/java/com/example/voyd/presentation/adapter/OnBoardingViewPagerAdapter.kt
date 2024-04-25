package com.example.voyd.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.voyd.R
import com.example.voyd.domain.models.OnBoardingData

class OnBoardingViewPagerAdapter(
    private var context: Context,
    private var onBoardingDataList: List<OnBoardingData>
) :
    PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return onBoardingDataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.onboarding_screen, null)

        val title: TextView = view.findViewById(R.id.onboarding_txt)
        val imageView: ImageView = view.findViewById(R.id.onboarding_img)

        title.text = onBoardingDataList[position].title
        imageView.setImageResource(onBoardingDataList[position].imageUrl)

        container.addView(view)
        return view
    }
}
