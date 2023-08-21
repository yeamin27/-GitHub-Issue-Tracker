package com.example.githubissuetracker.core.networking

object EndPoints {
    const val BASE_URL = "https://api.github.com"

    const val ISSUES = "/repos/{user}/{repo}/issues"

    const val SEARCH = "/search/issues"

}