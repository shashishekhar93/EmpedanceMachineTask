package com.tech.empedancemachinetask.sealed_classes
sealed class Screens(val title: String) {

    object Home : Screens("Home")
    object Services : Screens("Services")
    object Booking : Screens("Booking")
    object Profile : Screens("Profile")


}