package com.tech.empedancemachinetask.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tech.empedancemachinetask.R
import com.tech.empedancemachinetask.fragments.BookingFragment
import com.tech.empedancemachinetask.fragments.HomeFragment
import com.tech.empedancemachinetask.fragments.ProfileFragment
import com.tech.empedancemachinetask.fragments.ServicesFragment
import com.tech.empedancemachinetask.sealed_classes.Screens

class HomePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val context: Context
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val screens = listOf(
        Screens.Home,
        Screens.Services,
        Screens.Booking,
        Screens.Profile
    )

    override fun createFragment(position: Int): Fragment {
        return when (screens[position]) {
            Screens.Home -> HomeFragment.newInstance()
            Screens.Services -> ServicesFragment.newInstance()
            Screens.Booking -> BookingFragment.newInstance()
            Screens.Profile -> ProfileFragment.newInstance()
        }
    }

    override fun getItemCount(): Int = screens.size

    fun getPageTitle(position: Int): CharSequence = screens[position].title

    fun getPageIcon(position: Int): Drawable? = when (position) {
        0 -> ContextCompat.getDrawable(context, R.drawable.ic_home)
        1 -> ContextCompat.getDrawable(context, R.drawable.ic_services)
        2 -> ContextCompat.getDrawable(context, R.drawable.ic_booking)
        3 -> ContextCompat.getDrawable(context, R.drawable.ic_profile)
        else -> null
    }
}