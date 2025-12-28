package com.tech.empedancemachinetask

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.tech.empedancemachinetask.adapters.HomePagerAdapter
import com.tech.empedancemachinetask.common.NetworkHelper
import com.tech.empedancemachinetask.common.setupMainToolbar
import com.tech.empedancemachinetask.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityHomeBinding
    @Inject
    lateinit var pagerAdapter: HomePagerAdapter
    @Inject
    lateinit var networkHelper: NetworkHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        checkPermissionsAndNetwork()
        initToolbar()
        binding.viewpager.adapter = pagerAdapter
        initTabLayout()
    }

    private fun checkPermissionsAndNetwork() {
        if (!networkHelper.isInternetPermissionGranted()) {
            Toast.makeText(this, "Internet Permission is missing in Manifest!", Toast.LENGTH_LONG).show()
        } else {
            // Permission exists, observe network status
            lifecycleScope.launch {
                networkHelper.observeNetworkStatus().collectLatest { isConnected ->
                    if (!isConnected) {
                        Toast.makeText(this@MainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.bottomTabView, binding.viewpager, { tab, position ->
            tab.text = pagerAdapter.getPageTitle(position)
            tab.icon = pagerAdapter.getPageIcon(position)
        }).attach()
    }

    private fun initToolbar() {
        val customToolbarView = LayoutInflater.from(this)
            .inflate(R.layout.custom_toolbar_layout,
                binding.toolbar, false)
        setupMainToolbar(
            toolMenu = R.menu.home_menu,
            toolbar = binding.toolbar,
            customView = customToolbarView
        ) {
            when (it.itemId) {
                R.id.cart -> {
                    cartAction()
                    true
                }

                else -> false
            }
        }
    }

    private fun cartAction() {
        Toast.makeText(this, "Cart Action", Toast.LENGTH_SHORT).show()
    }

}