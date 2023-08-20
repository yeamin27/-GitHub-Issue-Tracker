package com.example.githubissuetracker.core.networking

object EndPoints {
    const val BASE_URL = "https://api.github.com"

    const val ISSUES = "/repos/{user}/{repo}/issues"

    const val ISSUE_COUNT = "/search/issues?q=repo:{user}/{repo}%20is:issue"

}