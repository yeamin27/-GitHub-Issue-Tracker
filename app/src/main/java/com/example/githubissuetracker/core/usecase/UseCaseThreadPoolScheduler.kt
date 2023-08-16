package com.example.githubissuetracker.core.usecase

import android.os.Handler
import android.os.Looper
import com.example.githubissuetracker.core.usecase.UseCase
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class UseCaseThreadPoolScheduler : UseCaseScheduler {
    companion object {
        const val POOL_SIZE = 2

        const val MAX_POOL_SIZE = 4

        const val TIMEOUT = 30
    }

    private val mHandler = Handler(Looper.getMainLooper())

    private var mThreadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
        POOL_SIZE, MAX_POOL_SIZE, TIMEOUT.toLong(),
        TimeUnit.SECONDS, ArrayBlockingQueue(POOL_SIZE)
    )

    override fun execute(runnable: Runnable) {
        mThreadPoolExecutor.execute(runnable)
    }

    override fun <V : UseCase.ResponseValue> notifyResponse(
        response: V,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        mHandler.post { useCaseCallback.onSuccess(response) }
    }

    override fun <V : UseCase.ResponseValue> onError(
        useCaseCallback: UseCase.UseCaseCallback<V>, t: Throwable
    ) {
        mHandler.post { useCaseCallback.onError(t) }
    }

}