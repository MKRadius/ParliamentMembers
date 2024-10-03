package com.example.parliamentmembers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parliamentmembers.ui.screens.EnumScreens
import com.example.parliamentmembers.ui.screens.MemberListScreen
import com.example.parliamentmembers.ui.screens.MemberScreen
import com.example.parliamentmembers.ui.screens.HomeScreen

@Composable
fun ParliamentMembersNavHost(
    navCtrl: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navCtrl, startDestination = EnumScreens.HOME.name) {
        composable(route = EnumScreens.HOME.route) {
            HomeScreen(navCtrl, modifier)
        }
        composable(route = EnumScreens.MEMBERLIST.route) { backStackEntry ->
            MemberListScreen(navCtrl, modifier, backStackEntry)
        }
        composable(route = EnumScreens.MEMBER.route) { backStackEntry ->
            MemberScreen(navCtrl, modifier, backStackEntry)
        }
    }
}