/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * PMApp is the main composable function of the Parliament Members application.
 * It initializes and manages the navigation controller (NavHostController) and
 * sets up the navigation graph using ParliamentMembersNavHost, allowing screen navigation.
 */

package com.example.parliamentmembers

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.parliamentmembers.ui.navigation.ParliamentMembersNavHost

@Composable
fun PMApp(navCtrl: NavHostController = rememberNavController()) {
    ParliamentMembersNavHost(navCtrl)
}