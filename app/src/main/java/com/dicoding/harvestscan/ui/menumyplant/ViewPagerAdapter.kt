package com.dicoding.harvestscan.ui.menumyplant

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.harvestscan.ui.menumyplant.myplant.MyPlantFragment
import com.dicoding.harvestscan.ui.menumyplant.reminder.ListReminderFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyPlantFragment()
            1 -> ListReminderFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}