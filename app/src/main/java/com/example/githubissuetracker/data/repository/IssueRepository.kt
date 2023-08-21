package com.example.githubissuetracker.data.repository

import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.model.SearchResult


class IssueRepository private constructor(
    private val remoteDataSource: IssueDataSource
) : IssueDataSource {

    companion object {
        private var INSTANCE: IssueRepository? = null
        fun getInstance(
            remoteDataSource: IssueDataSource
        ): IssueRepository {
            if (INSTANCE == null) {
                INSTANCE = IssueRepository(remoteDataSource)
            }
            return INSTANCE!!
        }
    }

    override suspend fun getIssueList(user: String, repo: String): NetworkResult<List<Issue>> {
        return remoteDataSource.getIssueList(user, repo)
    }

    override suspend fun searchIssues(
        searchTerm: String,
        user: String,
        repo: String
    ): NetworkResult<SearchResult> {
        return remoteDataSource.searchIssues(searchTerm, user, repo)
    }
}