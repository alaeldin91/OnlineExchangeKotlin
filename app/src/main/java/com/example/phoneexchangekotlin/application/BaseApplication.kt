package com.example.phoneexchangekotlin.application

import android.support.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication: MultiDexApplication() {
}