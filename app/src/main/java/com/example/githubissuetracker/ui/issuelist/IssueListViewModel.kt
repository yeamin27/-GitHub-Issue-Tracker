package com.example.githubissuetracker.ui.issuelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubissuetracker.core.baseview.BaseViewModel
import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.model.IssueListResponse
import com.example.githubissuetracker.data.repository.IssueRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class IssueListViewModel(
    private val user: String,
    private val repo: String,
    private val repository: IssueRepositoryImpl
) : BaseViewModel() {

    companion object {
        private const val TAG = "IssueListViewModel"
    }

    private val _resultState = MutableLiveData<NetworkResult<IssueListResponse>?>()
    val resultState: LiveData<NetworkResult<IssueListResponse>?>
        get() = _resultState
    val issueList: List<Issue>
        get() {
            if (resultState.value is NetworkResult.Success<IssueListResponse>)
                return (_resultState.value as NetworkResult.Success<IssueListResponse>).data.issues
            return emptyList()
        }

    var isSearchResult = false
        private set

    private val _selectedIssue = MutableLiveData<Issue?>(null)
    val selectedIssue: LiveData<Issue?>
        get() = _selectedIssue

    init {
        loadIssues()
    }

    private var job: Job? = null

    private fun loadIssues() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            setLoadingAsync(true)
            isSearchResult = false
            _resultState.postValue(null)
            _resultState.postValue(repository.getIssueList(user, repo))
            setLoadingAsync(false)
        }
    }

    fun search(searchTerm: String?) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            loadIssues()
            return
        }
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            setLoadingAsync(true)
            isSearchResult = true
            _resultState.postValue(null)
            _resultState.postValue(repository.searchIssues(searchTerm, user, repo))
            setLoadingAsync(false)
        }
    }

    fun navigateIssues(url: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            setLoadingAsync(true)
            _resultState.postValue(null)
            _resultState.postValue(repository.navigateIssues(url))
            setLoadingAsync(false)
        }
    }

    fun navigateSearch(url: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            setLoadingAsync(true)
            _resultState.postValue(null)
            _resultState.postValue(repository.navigateSearch(url))
            setLoadingAsync(false)
        }
    }

    fun setSelectedIssue(issue: Issue?) {
        _selectedIssue.value = issue
    }
}