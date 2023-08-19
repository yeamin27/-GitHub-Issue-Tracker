package com.example.githubissuetracker.ui.issuelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubissuetracker.core.baseview.BaseViewModel
import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.repository.IssueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IssueListViewModel(
    private val user: String,
    private val repo: String,
    private val repository: IssueRepository
) : BaseViewModel() {

    companion object {
        private const val TAG = "IssueListViewModel"
    }

    private val _issueList = MutableLiveData<List<Issue>>(emptyList())
    val issueList: LiveData<List<Issue>>
        get() = _issueList

    init {
        Log.d(TAG, "Init: fetching issues")
        loadIssues()
    }

    private fun loadIssues() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingAsync(true)
            _issueList.postValue(emptyList())
            Log.d(TAG, "loadIssues: fetching")
            when (val result = repository.getIssueList(user, repo)) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "loadIssues: ${result.data.size}")
                    _issueList.postValue(result.data)
                }

                is NetworkResult.Error -> {
                    Log.d(TAG, "loadIssues: ${result.code} ${result.message}")
                }

                is NetworkResult.Exception -> {
                    Log.e(TAG, "loadIssues: ", result.e)
                }
            }
            setLoadingAsync(false)
        }
    }
}