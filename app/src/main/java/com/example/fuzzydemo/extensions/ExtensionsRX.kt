package com.example.fuzzydemo.extensions

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.scheduleIOUI(): Single<T>? = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())



