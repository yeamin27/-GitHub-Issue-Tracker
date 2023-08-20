package com.example.githubissuetracker.ui.issuelist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubissuetracker.R
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.databinding.IssueItemBinding

class IssueListAdapter : RecyclerView.Adapter<IssueListAdapter.ViewHolder>() {
    companion object {
        private const val TAG = "IssueListAdapter"
    }

    interface Listener {
        fun onSelected(issue: Issue)
    }

    var items: List<Issue>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = IssueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items!![position])
    }

    override fun getItemCount() = items?.size ?: 0

    inner class ViewHolder(private val binding: IssueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(issue: Issue) {
            Log.d(TAG, "bind: ${issue.title}")
            binding.tvTitle.text = issue.title
            binding.tvIssueNumber.text = binding.tvIssueNumber.context.getString(R.string.issue_number, issue.number)
            binding.tvIssueCreator.text = binding.tvIssueNumber.context.getString(R.string.created_by, issue.user?.login)

            if (issue.state?.equals("open") == true) {
                Glide
                    .with(binding.ivStateIcon)
                    .load(R.drawable.ic_dot_in_circle_16)
                    .into(binding.ivStateIcon);
            } else if (issue.state?.equals("closed") == true) {
                Glide
                    .with(binding.ivStateIcon)
                    .load(R.drawable.ic_tick_in_circle_16)
                    .into(binding.ivStateIcon);
            }

            binding.root.setOnClickListener {
                listener?.onSelected(issue)
            }
        }
    }

}