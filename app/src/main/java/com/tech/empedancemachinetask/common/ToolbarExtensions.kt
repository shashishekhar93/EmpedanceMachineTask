package com.tech.empedancemachinetask.common

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.MaterialToolbar
import com.tech.empedancemachinetask.R
import androidx.core.view.size
import androidx.core.view.get

/*this is the example of extension function which is offered by kotlin
* allow you to add new functions to an existing class without modifying its source code,
*  using inheritance, or applying complex design patterns*/
fun ComponentActivity.setupMainToolbar(
    toolMenu: Int? = null,
    toolbar: MaterialToolbar,
    title: String? = null,
    customView: View? = null,
    onMenuItemClick: ((MenuItem) -> Boolean)? = null
) {
    toolMenu?.let {
        toolbar.inflateMenu(it)
    }

    if (customView != null) {
        toolbar.addView(customView)
        // Ensure the custom view takes up available space
        val layoutParams = customView.layoutParams as? Toolbar.LayoutParams
        layoutParams?.let {
            it.gravity = Gravity.CENTER_VERTICAL
        }
    } else {
        toolbar.setTitle(title)
        toolbar.setTitleTextColor(getColor(R.color.white))
    }
    toolbar.setBackgroundColor(getColor(R.color.colorChatBackground))
    //toolbar.overflowIcon?.setTint(getColor(R.color.black))

    // Tint menu items to black
    val menu = toolbar.menu
    for (i in 0 until menu.size) {
        val item = menu[i]
        item.icon?.setTint(getColor(R.color.black))
    }

    toolbar.setOnMenuItemClickListener { item ->
        onMenuItemClick?.invoke(item) ?: false
    }
}
