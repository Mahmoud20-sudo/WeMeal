package com.wemeal.presentation.util.events

import com.google.firebase.analytics.FirebaseAnalytics

enum class FirebaseStandardEvent(private var eventName: String, private val hasStandardParams: Boolean, private var eventType: EventType, private var eventCase: EventCase? = null) : BaseEvent
{

    /** FIREBASE STANDARD EVENTS **/
    // https://firebase.google.com/docs/reference/kotlin/com/google/firebase/analytics/FirebaseAnalytics.Event

    // LOGIN
    LOGIN(FirebaseAnalytics.Event.LOGIN, true, EventType.MUTATION);

    /*** OVERRIDE FUNCTIONS ***/

    // Override Functions to implement BaseEvent interface
    override fun getEventName(): String
    {
        return eventName
    }

    override fun getEventCase(): EventCase?
    {
        return eventCase
    }

    override fun setEventCase(eventCase: EventCase)
    {
        this.eventCase = eventCase
    }

    override fun getEventType(): EventType?
    {
        return eventType
    }

    override fun hasStandardParams(): Boolean
    {
        return hasStandardParams
    }

    override fun hasValueToSum(): Boolean
    {
        return false
    }
}