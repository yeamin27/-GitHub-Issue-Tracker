package com.example.githubissuetracker.data.source.remote

import android.util.Log
import com.example.githubissuetracker.core.networking.GithubService
import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.model.IssueListResponse
import com.example.githubissuetracker.data.model.SearchResult
import com.example.githubissuetracker.data.repository.IssueDataSource
import okhttp3.Headers
import retrofit2.Response


class IssueRemoteDataSource(private val githubService: GithubService) : IssueDataSource {

    companion object {
        private const val TAG = "IssueRemoteDataSource"
        fun getInstance(githubService: GithubService) = IssueRemoteDataSource(githubService)
    }

    private fun getNavigationLinks(headers: Headers): List<Pair<String, String>> {
        val regex = """<([^>]+)>;\s*rel="([^"]+)"""".toRegex()
        val matches = regex.findAll(headers["link"] ?: "")

        val urlsAndRelations = matches.map { matchResult ->
            val url = matchResult.groupValues[1]
            val rel = matchResult.groupValues[2]
            rel to url
        }.toList()
        return urlsAndRelations
    }

    private fun handleIssueResponse(response: Response<List<Issue>>) : NetworkResult<IssueListResponse> {
        return if (response.isSuccessful && response.body() != null) {
            val navigationUrls = getNavigationLinks(response.headers())
            val issueListResponse = IssueListResponse(issues = response.body()!!)
            for ((rel, url) in navigationUrls) {
                when (rel) {
                    "prev" -> issueListResponse.previousUrl = url
                    "first" -> issueListResponse.firstUrl = url
                    "next" -> issueListResponse.nextUrl = url
                    "last" -> issueListResponse.lastUrl = url
                }
            }
            NetworkResult.Success(issueListResponse)
        } else {
            var message = response.message()
            if (response.code() == 404)
                message = "No repository found!"
            NetworkResult.Error(code = response.code(), message = message)
        }
    }

    private fun handleSearchResponse(response: Response<SearchResult>) : NetworkResult<IssueListResponse> {
        return if (response.isSuccessful && response.body() != null) {
            val navigationUrls = getNavigationLinks(response.headers())
            val issueListResponse = IssueListResponse(
                issues = response.body()!!.items!!,
                totalCount = response.body()?.totalCount,
                incompleteResults = response.body()?.incompleteResults,
            )
            for ((rel, url) in navigationUrls) {
                when (rel) {
                    "prev" -> issueListResponse.previousUrl = url
                    "first" -> issueListResponse.firstUrl = url
                    "next" -> issueListResponse.nextUrl = url
                    "last" -> issueListResponse.lastUrl = url
                }
            }
            NetworkResult.Success(issueListResponse)
        } else {
            var message = response.message()
            if (response.code() == 404 || response.code() == 422)
                message = "No repository found!"
            NetworkResult.Error(code = response.code(), message = message)
        }
    }

    override suspend fun getIssueList(
        user: String,
        repo: String
    ): NetworkResult<IssueListResponse> {
        return try {
            handleIssueResponse(githubService.getIssues(user, repo))
        } catch (ex: Exception) {
            NetworkResult.Exception(ex)
        }
    }

    override suspend fun navigateIssues(url: String): NetworkResult<IssueListResponse> {
        return try {
            handleIssueResponse(githubService.navigateIssues(url))
        } catch (ex: Exception) {
            NetworkResult.Exception(ex)
        }
    }

    override suspend fun searchIssues(
        searchTerm: String,
        user: String,
        repo: String
    ): NetworkResult<IssueListResponse> {
        return try {
            return handleSearchResponse(githubService.searchIssues("$searchTerm+repo:$user/$repo+in:title+is:issue"))
        } catch (ex: Exception) {
            NetworkResult.Exception(ex)
        }
    }

    override suspend fun navigateSearch(url: String): NetworkResult<IssueListResponse> {
        return try {
            return handleSearchResponse(githubService.navigateSearch(url))
        } catch (ex: Exception) {
            NetworkResult.Exception(ex)
        }
    }
}