package com.example.githubissuetracker.ui.issuelist

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.githubissuetracker.R
import com.example.githubissuetracker.core.baseview.BaseActivity
import com.example.githubissuetracker.core.common.ViewModelFactory
import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.databinding.ActivityIssueListBinding
import com.example.githubissuetracker.ui.issuedetails.IssueDetailsFragment

class IssueListActivity :
    BaseActivity<ActivityIssueListBinding>(ActivityIssueListBinding::inflate),
    IssueListAdapter.Listener {
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

        adapter.listener = this
        binding?.rvIssues?.adapter = adapter

        viewModel.resultState.subscribe {result->
            result?.let {
                when(it) {
                    is NetworkResult.Error -> {
                        binding?.tvErrorHint?.text = it.message
                        binding?.tvErrorHint?.visibility = View.VISIBLE
                    }
                    is NetworkResult.Exception -> {
                        binding?.tvErrorHint?.text = getString(R.string.no_internet)
                        binding?.tvErrorHint?.visibility = View.VISIBLE
                    }
                    is NetworkResult.Success -> {
                        binding?.tvErrorHint?.visibility = View.GONE
                    }
                }
            }
        }

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

    override fun onSelected(issue: Issue) {
        viewModel.setSelectedIssue(issue)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.details_container, IssueDetailsFragment())
            .addToBackStack(IssueDetailsFragment.TAG)
            .commitAllowingStateLoss()
    }
}