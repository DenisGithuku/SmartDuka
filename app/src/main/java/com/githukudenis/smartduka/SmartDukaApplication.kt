package com.githukudenis.smartduka

import android.app.Application
import android.util.Log
import androidx.compose.ui.text.font.Font
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class SmartDukaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        when {
            BuildConfig.DEBUG -> {
                Timber.plant(object: Timber.DebugTree() {
                    override fun createStackElementTag(element: StackTraceElement): String {
                        return super.createStackElementTag(element) + ":" + element.lineNumber
                    }
                })
            }
            else -> Timber.plant(CrashlyticsTree())
        }
    }
}

private class CrashlyticsTree: Timber.Tree() {
    private val crashlytics: FirebaseCrashlytics by lazy { FirebaseCrashlytics.getInstance() }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority === Log.VERBOSE || priority === Log.DEBUG || priority === Log.INFO) {
            return
        }

        if (BuildConfig.DEBUG) {
            crashlytics.isCrashlyticsCollectionEnabled = false
            return
        }

        crashlytics.setCustomKey(CrashlyticsKeys.CRASHLYTICS_KEY_PRIORITY, priority)
        if (tag != null) {
            crashlytics.setCustomKey(CrashlyticsKeys.CRASHLYTICS_KEY_TAG, tag)
        }
        crashlytics.setCustomKey(CrashlyticsKeys.CRASHLYTICS_KEY_MESSAGE, message)

        if (t === null) {
            crashlytics.recordException(Throwable(message))
        } else {
            crashlytics.recordException(t)
        }
    }
}

object CrashlyticsKeys {
    const val CRASHLYTICS_KEY_PRIORITY = "priority"
    const val CRASHLYTICS_KEY_TAG = "tag"
    const val CRASHLYTICS_KEY_MESSAGE = "message"
}