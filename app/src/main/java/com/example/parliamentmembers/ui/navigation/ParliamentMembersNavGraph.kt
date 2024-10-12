/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * ParliamentMembersNavHost sets up the navigation graph for the Parliament Members application.
 * It uses the NavHostController to manage navigation between different composable screens,
 * including HomeScreen, MemberListScreen, MemberScreen, and NoteScreen.
 * Each screen is mapped to a corresponding route defined in the EnumScreens class.
 */

package com.example.parliamentmembers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parliamentmembers.ui.screens.EnumScreens
import com.example.parliamentmembers.ui.screens.MemberListScreen
import com.example.parliamentmembers.ui.screens.MemberScreen
import com.example.parliamentmembers.ui.screens.HomeScreen
import com.example.parliamentmembers.ui.screens.NoteScreen

@Composable
fun ParliamentMembersNavHost(
    navCtrl: NavHostController
) {
    NavHost(navController = navCtrl, startDestination = EnumScreens.HOME.name) {
        composable(route = EnumScreens.HOME.route) {
            HomeScreen(navCtrl)
        }
        composable(route = EnumScreens.MEMBERLIST.route) { backStackEntry ->
            MemberListScreen(navCtrl, backStackEntry)
        }
        composable(route = EnumScreens.MEMBER.route) {
            MemberScreen(navCtrl)
        }
        composable(route = EnumScreens.NOTE.route) {
            NoteScreen(navCtrl)
        }
    }
}