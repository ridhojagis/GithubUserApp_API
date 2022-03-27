package com.ridhojagis.githubuserapi.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ridhojagis.githubuserapi.fragment.FollowsFragment

class SectionPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowsFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowsFragment.ARG_SECTION_NUMBER, position + 1)
            putString(FollowsFragment.USERNAME, username)
        }
        return fragment
    }
}