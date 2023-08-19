package com.example.githubissuetracker.core.baseview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding

typealias InflateActivity<T> = (LayoutInflater) -> T

abstract class BaseActivity<V : ViewBinding>(private val inflater: InflateActivity<V>) :
    AppCompatActivity() {
    private var _binding: V? = null
    val binding: V? get() = _binding

    protected abstract fun initView(savedInstanceState: Bundle?)

    protected fun <T> LiveData<T>.subscribe(function: (T) -> Unit) {
        this.observe(this@BaseActivity) {
            function(it)
        }
    }

    protected fun <T> MutableLiveData<T>.subscribe(function: (T) -> Unit) {
        this.observe(this@BaseActivity) {
            function(it)
        }
    }

    protected fun View.onClick(block: (View) -> Unit) {
        setOnClickListener(block)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflater.invoke(layoutInflater)
        setContentView(binding!!.root)
        initView(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}