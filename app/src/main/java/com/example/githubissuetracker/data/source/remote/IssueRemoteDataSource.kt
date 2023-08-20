package com.example.githubissuetracker.data.source.remote

import android.util.Log
import com.example.githubissuetracker.core.networking.GithubService
import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.repository.IssueDataSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.lang.reflect.Type


class IssueRemoteDataSource(private val githubService: GithubService) : IssueDataSource {

    companion object {
        private const val TAG = "IssueRemoteDataSource"
        fun getInstance(githubService: GithubService) = IssueRemoteDataSource(githubService)
    }

    override suspend fun getIssueList(
        user: String,
        repo: String
    ): NetworkResult<List<Issue>> {
        return try {
            val response = githubService.getIssues(user, repo)
            Log.d(TAG, "getIssueList: ${response.code()} ${response.message()}")
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                var message = response.message()
                if (response.code() == 404)
                    message = "No repository found!"
                NetworkResult.Error(code = response.code(), message = message)
            }
        } catch (ex: Exception) {
            Log.e(TAG, "getIssueList: ", ex)
            NetworkResult.Exception(ex)
        }
    }
}