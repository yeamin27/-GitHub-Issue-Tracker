package com.example.githubissuetracker.core.baseview

import androidx.viewbinding.ViewBinding

abstract class BaseObservableActivity<V : ViewBinding, ListenerType>(inflater: InflateActivity<V>) :
    BaseActivity<V>(inflater) {

    private val listeners = hashSetOf<ListenerType>()

    fun notify(data: (ListenerType) -> Unit) {
        listeners.forEach(data)
    }

    fun registerObserver(it: ListenerType) {
        if (listeners.contains(it).not()) listeners.add(it)
    }

    fun unRegisterObserver(it: ListenerType) {
        if (listeners.contains(it)) listeners.remove(it)
    }

    override fun onDestroy() {
        super.onDestroy()
        listeners.clear()
    }
}