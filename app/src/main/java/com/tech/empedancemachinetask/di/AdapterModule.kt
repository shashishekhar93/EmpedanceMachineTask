package com.tech.empedancemachinetask.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.tech.empedancemachinetask.adapters.AcServicesAdapter
import com.tech.empedancemachinetask.adapters.CategoriesAdapter
import com.tech.empedancemachinetask.adapters.HomePagerAdapter
import com.tech.empedancemachinetask.adapters.OffersAdapter
import com.tech.empedancemachinetask.adapters.ServicesAdapter
import com.tech.empedancemachinetask.common.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class AdapterModule {
    @ActivityScoped
    @Provides
    fun provideHomeAdapter(@ActivityContext context: Context): HomePagerAdapter {
        val activity = context as FragmentActivity
        return HomePagerAdapter(activity.supportFragmentManager, activity.lifecycle, context)
    }

    @ActivityScoped
    @Provides
    fun provideServicesAdapter(appUtils: AppUtils): ServicesAdapter {
        return ServicesAdapter(appUtils)
    }

    @ActivityScoped
    @Provides
    fun provideCategoriesAdapter(appUtils: AppUtils): CategoriesAdapter {
        return CategoriesAdapter(appUtils)
    }
    
    @ActivityScoped
    @Provides
    fun provideAcServicesAdapter(): AcServicesAdapter {
        return AcServicesAdapter()
    }

    @ActivityScoped
    @Provides
    fun provideOffersAdapter(): OffersAdapter {
        return OffersAdapter()
    }
}
