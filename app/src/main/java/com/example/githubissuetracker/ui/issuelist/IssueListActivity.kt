package com.example.githubissuetracker.ui.issuelist

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.githubissuetracker.core.baseview.BaseActivity
import com.example.githubissuetracker.core.common.ViewModelFactory
import com.example.githubissuetracker.databinding.ActivityIssueListBinding

class IssueListActivity :
    BaseActivity<ActivityIssueListBinding>(ActivityIssueListBinding::inflate) {
    companion object {
        const val KEY_USER = "KEY_USER"
        const val KEY_REPO = "KEY_REPO"
    }

    private lateinit var user: String
    private lateinit var repo: String
    private lateinit var viewModel: IssueListViewModel
    private val adapter: IssueListAdapter by lazy { IssueListAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        user = intent.getStringExtra(KEY_USER) ?: ""
        repo = intent.getStringExtra(KEY_REPO) ?: ""
        viewModel =
            ViewModelProvider(this, (ViewModelFactory(user, repo)))[IssueListViewModel::class.java]

        binding?.rvIssues?.adapter = adapter

        viewModel.issueList.subscribe { issues ->
            adapter.items = issues
        }

        viewModel.isLoading.subscribe { isLoading ->
            if (isLoading) {
                binding?.content?.visibility = View.GONE
                binding?.loadingIndicator?.visibility = View.VISIBLE
            } else {
                binding?.content?.visibility = View.VISIBLE
                binding?.loadingIndicator?.visibility = View.GONE
            }
        }
    }
}