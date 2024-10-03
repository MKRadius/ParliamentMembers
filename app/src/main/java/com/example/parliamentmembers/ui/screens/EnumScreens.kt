package com.example.parliamentmembers.ui.screens

enum class EnumScreens(val route: String) {
    HOME("home"),
    MEMBERLIST("memberlist/{param}"),
    MEMBER("member/{param}");

    fun withParam(vararg args: String): String {
        return buildString {
            append(route.substringBefore("/{"))
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}