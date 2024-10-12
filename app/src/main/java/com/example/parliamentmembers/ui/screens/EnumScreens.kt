/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * EnumScreens is an enum class that defines the navigation routes for
 * the ParliamentMembers application. Each enum constant represents a
 * different screen in the app, with associated route strings for navigation.
 * The class includes a method, withParams, that allows for dynamic route
 * parameterization by replacing placeholder segments in the route with
 * provided arguments, facilitating navigation with specific parameters.
 */

package com.example.parliamentmembers.ui.screens

enum class EnumScreens(val route: String) {
    HOME("home"),
    MEMBERLIST("memberlist/{type}/{selected}"),
    MEMBER("member/{param}"),
    NOTE("note/{param}");

    fun withParams(vararg args: String): String {
        var updatedRoute = route
        args.forEach { updatedRoute = updatedRoute.replaceFirst(Regex("\\{[^}]+\\}"), it) }
        return updatedRoute
    }
}