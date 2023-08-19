package com.example.githubissuetracker.core.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubissuetracker.core.networking.RetrofitInstance
import com.example.githubissuetracker.data.repository.IssueRepository
import com.example.githubissuetracker.data.source.remote.IssueRemoteDataSource
import com.example.githubissuetracker.ui.issuelist.IssueListViewModel

class ViewModelFactory(private val user: String, private val repo: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == IssueListViewModel::class.java) {
            return IssueListViewModel(
                user,
                repo,
                IssueRepository.getInstance(IssueRemoteDataSource(RetrofitInstance.githubService))
            ) as T
        }
        throw IllegalArgumentException("unknown model class $modelClass")
    }
}