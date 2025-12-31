package com.tech.empedancemachinetask

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.tech.empedancemachinetask.adapters.HomePagerAdapter
import com.tech.empedancemachinetask.common.setupMainToolbar
import com.tech.empedancemachinetask.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityHomeBinding
    @Inject
    lateinit var pagerAdapter: HomePagerAdapter

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
        
        initToolbar()
        binding.viewpager.adapter = pagerAdapter
        initTabLayout()
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