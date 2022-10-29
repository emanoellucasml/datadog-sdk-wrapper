package com.example.datadog_wrapper

import android.content.Context
import android.util.Log
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.core.configuration.Credentials
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.RumMonitor
import com.datadog.android.rum.tracking.MixedViewTrackingStrategy

object DatadogInitializer {
    fun initialize(
        context: Context,
        clientToken: String,
        applicationId: String,
        envName: String? = "test",
        appVName: String? = "test",
        hostsList: List<String>? = null
    ) {
        val configuration = Configuration.Builder(
            true,
            true,
            true,
            true
        ).trackInteractions()
            .trackLongTasks()
            .setWebViewTrackingHosts(hostsList ?: listOf())
            .useViewTrackingStrategy(MixedViewTrackingStrategy(true))
            .useSite(DatadogSite.US1)
            .build()

        val credentials = Credentials(clientToken, envName!!, appVName!!, applicationId)
        Datadog.initialize(context, credentials, configuration, TrackingConsent.GRANTED)
        Datadog.setVerbosity(Log.VERBOSE)
        GlobalRum.registerIfAbsent(RumMonitor.Builder().build())

        Log.v("datadog-wrapper", "initialization finished")
    }
}