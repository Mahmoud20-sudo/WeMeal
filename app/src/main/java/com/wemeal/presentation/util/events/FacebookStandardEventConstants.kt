package com.wemeal.presentation.util.events

import com.facebook.appevents.AppEventsConstants

enum class FacebookStandardEvent(private var eventName: String, private val hasStandardParams: Boolean, private var hasValueToSum: Boolean, private var eventType: EventType, private var eventCase: EventCase? = null) : BaseEvent
{

    /** FACEBOOK STANDARD EVENTS **/
    // https://developers.facebook.com/docs/app-events/reference

    // COMPLETE REGISTRATION
    EVENT_NAME_COMPLETED_REGISTRATION(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, true, true, EventType.MUTATION), // valueToSum here should be false, but for some reason facebook still requires it for this event to made it true

    EVENT_NAME_ACTIVATED_APP(AppEventsConstants.EVENT_NAME_ACTIVATED_APP, false, false, EventType.VIEW);

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
        return hasValueToSum
    }
}