package com.wgv.ampliacionbuttonnavigation

import kotlinx.serialization.Serializable

sealed class Screen() {
    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data object SettingScreen : Screen(){

    }

    @Serializable
    data class ProfileScreen(val userId: String) : Screen()

}

