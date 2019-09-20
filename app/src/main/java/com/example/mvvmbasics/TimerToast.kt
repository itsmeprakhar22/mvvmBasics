package com.example.mvvmbasics

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class TimerToast(application: Application, lifecycleOwner: LifecycleOwner): LifecycleObserver {

    private val lifecycle= lifecycleOwner.lifecycle
    private var started= false
    init{
        lifecycle.addObserver(this)
    }

    private val timer= object: CountDownTimer(60000, 3000){
        override fun onFinish() {
            if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
           Toast.makeText(application, "Finished", Toast.LENGTH_SHORT).show()
        }

        override fun onTick(millisUntilFinished: Long) {
            if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
            Toast.makeText(application,  "$millisUntilFinished", Toast.LENGTH_SHORT).show()
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start(){
        if(!started){
            started= true
            timer.start()
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stop(){
        timer.cancel()
    }
}