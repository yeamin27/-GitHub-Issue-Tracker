package com.example.githubissuetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.githubissuetracker.core.baseview.BaseActivity
import com.example.githubissuetracker.databinding.ActivityMainBinding
import com.example.githubissuetracker.ui.issuelist.IssueListActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    companion object {
        private const val TAG = "MainActivity"
    }

    private fun getUserAndRepo(url: String?): Pair<String, String>? {
        if (url == null)
            return null

        val matchResult = """^(?:https:\/\/)?(?:www\.)?github\.com\/([^\/]+)\/([^\/]+)$""".toRegex().find(url)
            ?: return  null

        return Pair(matchResult.groupValues[1], matchResult.groupValues[2])
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.btnSearch?.onClick {
            val url = binding?.etUrl?.text?.toString()

            val userAndRepo = getUserAndRepo(url)
            if (userAndRepo == null) {
                binding?.tvErrorHint?.visibility = View.VISIBLE
                return@onClick
            }
            binding?.tvErrorHint?.visibility = View.GONE
            val intent = Intent(this, IssueListActivity::class.java)
            intent.putExtra(IssueListActivity.KEY_USER, userAndRepo.first)
            intent.putExtra(IssueListActivity.KEY_REPO, userAndRepo.second)
            startActivity(intent)
        }
    }
}