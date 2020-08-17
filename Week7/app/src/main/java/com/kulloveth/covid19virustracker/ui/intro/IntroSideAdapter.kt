package com.kulloveth.covid19virustracker.ui.intro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.R
import kotlinx.android.synthetic.main.item_onboarding.view.*

class IntroSideAdapter(private val introSliders: List<IntroSlide>) :
    RecyclerView.Adapter<IntroSideAdapter.IntroSliderViewHolder>() {

    inner class IntroSliderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textTitle = view.app_nam
        private val imageIcon = view.lotty_icon

        fun bind(introSlide: IntroSlide) {
            textTitle.text = introSlide.titleResource
            imageIcon.setAnimation(introSlide.logoResource)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSliderViewHolder {
        return IntroSliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_onboarding,
                parent, false
            )
        )
    }

    override fun getItemCount() = introSliders.size

    override fun onBindViewHolder(holder: IntroSliderViewHolder, position: Int) {
        holder.bind(introSliders[position])
    }


}