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

    private val _resultState = MutableLiveData<NetworkResult<List<Issue>>?>()
    val resultState: LiveData<NetworkResult<List<Issue>>?>
        get() = _resultState

    private val _issueList = MutableLiveData<List<Issue>>(emptyList())
    val issueList: LiveData<List<Issue>>
        get() = _issueList

    private val _selectedIssue = MutableLiveData<Issue?>(null)
    val selectedIssue: LiveData<Issue?>
        get() = _selectedIssue

    init {
        loadIssues()
    }

    private fun loadIssues() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingAsync(true)
            _issueList.postValue(emptyList())
            val result = repository.getIssueList(user, repo)
            _resultState.postValue(result)

            if (result is NetworkResult.Success) {
                _issueList.postValue(result.data)
            }
            setLoadingAsync(false)
        }
    }

    fun setSelectedIssue(issue: Issue?) {
        _selectedIssue.value = issue
    }
}