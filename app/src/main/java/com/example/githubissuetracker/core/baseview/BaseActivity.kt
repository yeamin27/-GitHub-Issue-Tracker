package com.example.githubissuetracker.core.baseview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

typealias InflateActivity<T> = (LayoutInflater) -> T

abstract class BaseActivity<V : ViewBinding>(private val inflater: InflateActivity<V>) :
    AppCompatActivity() {
    private var _binding: V? = null
    val binding: V? get() = _binding

    protected abstract fun initView(savedInstanceState: Bundle?)
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