package com.example.githubissuetracker.ui.issuedetails

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.githubissuetracker.R
import com.example.githubissuetracker.core.baseview.BaseFragment
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.databinding.FragmentIssueDetailsBinding
import com.example.githubissuetracker.ui.issuelist.IssueListViewModel
import io.noties.markwon.Markwon

class IssueDetailsFragment :
    BaseFragment<FragmentIssueDetailsBinding>(FragmentIssueDetailsBinding::inflate) {
    companion object {
        const val TAG = "IssueDetailsFragment"
    }

    private val viewModel: IssueListViewModel by activityViewModels()

    private val issue: Issue? get() = viewModel.selectedIssue.value
    private lateinit var markwon: Markwon

    override fun initView(savedInstanceState: Bundle?) {
        markwon = Markwon.create(requireContext())
        binding?.tvTitle?.text = issue?.title
        binding?.tvIssueNumber?.text = getString(R.string.issue_number, issue?.number)

        issue?.state?.let {
            if (it == "open" && binding != null) {
                Glide
                    .with(binding!!.ivStateIcon)
                    .load(R.drawable.ic_dot_in_circle_16)
                    .into(binding!!.ivStateIcon)
                binding?.tvState?.text = getString(R.string.open)
                binding?.cvStateChip?.setCardBackgroundColor(resources.getColor(R.color.state_open))
            }
            if (it == "closed" && binding != null) {
                Glide
                    .with(binding!!.ivStateIcon)
                    .load(R.drawable.ic_tick_in_circle_16)
                    .into(binding!!.ivStateIcon)
                binding?.tvState?.text = getString(R.string.closed)
                binding?.cvStateChip?.setCardBackgroundColor(resources.getColor(R.color.state_closed))
            }

            binding?.tvIssueInfo?.text = getString(R.string.issue_info_author_and_date, issue?.user?.login, issue?.createdAt)
            markwon.setMarkdown(binding!!.tvIssueDescription, issue?.body ?: "");
        }
    }
}