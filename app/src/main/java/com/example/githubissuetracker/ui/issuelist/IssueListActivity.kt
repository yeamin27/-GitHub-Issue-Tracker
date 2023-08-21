package com.example.githubissuetracker.ui.issuelist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.githubissuetracker.R
import com.example.githubissuetracker.core.baseview.BaseActivity
import com.example.githubissuetracker.core.common.ViewModelFactory
import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.model.IssueListResponse
import com.example.githubissuetracker.databinding.ActivityIssueListBinding
import com.example.githubissuetracker.ui.issuedetails.IssueDetailsFragment

class IssueListActivity :
    BaseActivity<ActivityIssueListBinding>(ActivityIssueListBinding::inflate),
    IssueListAdapter.Listener {
    companion object {
        private const val TAG = "IssueListActivity"
        const val KEY_USER = "KEY_USER"
        const val KEY_REPO = "KEY_REPO"
    }

    private lateinit var user: String
    private lateinit var repo: String
    private lateinit var viewModel: IssueListViewModel
    private val adapter: IssueListAdapter by lazy { IssueListAdapter() }

    private fun bindPageNavigation(networkResult: NetworkResult.Success<IssueListResponse>) {
        networkResult.data.firstUrl?.let { url ->
            binding?.btnFirst?.setCardBackgroundColor(resources.getColor(R.color.royal_blue))
            binding?.btnFirst?.onClick {
                if (viewModel.isSearchResult)
                    viewModel.navigateSearch(url)
                else
                    viewModel.navigateIssues(url)
            }
        } ?: run {
            binding?.btnFirst?.setCardBackgroundColor(resources.getColor(R.color.text_color_muted))
        }

        networkResult.data.previousUrl?.let { url ->
            binding?.btnPrev?.setCardBackgroundColor(resources.getColor(R.color.royal_blue))
            binding?.btnPrev?.onClick {
                if (viewModel.isSearchResult)
                    viewModel.navigateSearch(url)
                else
                    viewModel.navigateIssues(url)
            }
        } ?: run {
            binding?.btnPrev?.setCardBackgroundColor(resources.getColor(R.color.text_color_muted))
        }

        networkResult.data.nextUrl?.let { url ->
            binding?.btnNext?.setCardBackgroundColor(resources.getColor(R.color.royal_blue))
            binding?.btnNext?.onClick {
                if (viewModel.isSearchResult)
                    viewModel.navigateSearch(url)
                else
                    viewModel.navigateIssues(url)
            }
        } ?: run {
            binding?.btnNext?.setCardBackgroundColor(resources.getColor(R.color.text_color_muted))
        }

        networkResult.data.lastUrl?.let { url ->
            binding?.btnLast?.setCardBackgroundColor(resources.getColor(R.color.royal_blue))
            binding?.btnLast?.onClick {
                if (viewModel.isSearchResult)
                    viewModel.navigateSearch(url)
                else
                    viewModel.navigateIssues(url)
            }
        } ?: run {
            binding?.btnLast?.setCardBackgroundColor(resources.getColor(R.color.text_color_muted))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        user = intent.getStringExtra(KEY_USER) ?: ""
        repo = intent.getStringExtra(KEY_REPO) ?: ""
        viewModel =
            ViewModelProvider(this, (ViewModelFactory(user, repo)))[IssueListViewModel::class.java]

        adapter.listener = this
        binding?.rvIssues?.adapter = adapter

        viewModel.resultState.subscribe { result ->
            result?.let {
                when (it) {
                    is NetworkResult.Error -> {
                        Log.d(TAG, "initView: ${it.message}")
                        binding?.tvErrorHint?.text = it.message
                        binding?.tvErrorHint?.visibility = View.VISIBLE
                    }

                    is NetworkResult.Exception -> {
                        Log.e(TAG, "initView: ", it.e)
                        binding?.tvErrorHint?.text = getString(R.string.no_internet)
                        binding?.tvErrorHint?.visibility = View.VISIBLE
                    }

                    is NetworkResult.Success -> {
                        binding?.tvErrorHint?.visibility = View.GONE

                        if (viewModel.issueList.isEmpty()) {
                            binding?.rvIssues?.visibility = View.GONE
                            binding?.tvErrorHint?.text = getString(R.string.no_issue_found)
                        } else {
                            adapter.items = viewModel.issueList
                            binding?.rvIssues?.visibility = View.VISIBLE
                            binding?.tvErrorHint?.visibility = View.GONE
                        }
                        bindPageNavigation(it)
                    }
                }
            }
        }

        viewModel.isLoading.subscribe { isLoading ->
            if (isLoading) {
                binding?.tvErrorHint?.visibility = View.GONE
                binding?.content?.visibility = View.GONE
                binding?.loadingIndicator?.visibility = View.VISIBLE
            } else if (viewModel.resultState.value is NetworkResult.Success<IssueListResponse>) {
                binding?.loadingIndicator?.visibility = View.GONE
                binding?.content?.visibility = View.VISIBLE
            } else {
                binding?.loadingIndicator?.visibility = View.GONE
            }
        }

        binding?.btnSearch?.onClick {
            viewModel.search(binding?.etSearchField?.text.toString())
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