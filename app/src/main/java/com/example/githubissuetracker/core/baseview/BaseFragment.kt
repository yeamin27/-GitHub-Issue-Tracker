package com.example.githubissuetracker.core.baseview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding

typealias InflateFragment<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<V : ViewBinding>(private val inflater: InflateFragment<V>) :
    Fragment() {
    private var _binding: V? = null
    val binding: V? get() = _binding
    var idTag: String = ""

    open val screenName: String? = null

    open fun onBackPressed(params: OnBackPressedCallback) {
        params.isEnabled = false
        activity?.onBackPressedDispatcher?.onBackPressed()
        params.isEnabled = true
    }

    private val backPressCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed(this)
        }
    }

    protected abstract fun initView(savedInstanceState: Bundle?)
    protected inline fun <reified T> grabListener(noinline onFind: ((it: T) -> Unit)? = null): T? {
        var par = parentFragment
        while (par != null) {
            if (par is T) {
                onFind?.invoke(par)
                return par
            }
            par = par.parentFragment
        }
        return (activity as? T)?.let {
            onFind?.invoke(it)
            return it
        }
    }

    protected fun <T> LiveData<T>.subscribe(function: (T) -> Unit) {
        this.observe(viewLifecycleOwner) {
            function(it)
        }
    }

    protected fun <T> MutableLiveData<T>.subscribe(function: (T) -> Unit) {
        this.observe(viewLifecycleOwner) {
            function(it)
        }
    }

    protected fun registerBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, backPressCallback)
    }

    protected fun View.onClick(block: (View) -> Unit) {
        setOnClickListener(block)
    }

    protected val mFragmentManager: FragmentManager get() = childFragmentManager
    protected val mActivity: FragmentActivity get() = requireActivity()

    override fun onCreateView(
        inflaterp: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflater.invoke(inflaterp, container, false)
        binding!!.root.onClick { }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPress()
        initView(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}