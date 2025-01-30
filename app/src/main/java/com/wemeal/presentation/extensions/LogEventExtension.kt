package com.wemeal.presentation.extensions

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mazenrashed.logdnaandroidclient.LogDna.log
import com.mazenrashed.logdnaandroidclient.models.Line
import com.wemeal.BuildConfig
import com.wemeal.data.model.FoodyModel
import com.wemeal.data.model.main.feed.Activity
import com.wemeal.data.model.main.feed.Result
import com.wemeal.presentation.util.SharedPreferencesManager
import com.wemeal.presentation.util.UserType
import com.wemeal.presentation.util.events.*

/**
 * Log Custom Event to Facebook and Firebase Analytics and LogDNA.
 *
 * In case the event has an error, make sure to add the exception object and ensure eventCase = EventCase.ERROR. Having an exception object will also log the error to LogDNA and Firebase Crashlytics
 */
fun Context.logEvent(
    event: CustomEvent,
    eventCase: EventCase,
    bundle: Bundle = Bundle(),
    message: String? = null,
    exception: Exception? = null
) {
    // Checks that the eventCase is correct based on whether we have an exception or not
    checkWrongEventCase(eventCase, exception)

    // Adds the eventCase from the parameter into the event object
    supplementEventWithEventCase(event, eventCase)

    // Supplements the bundle with app parameters and context info
    supplementBundleWithAppParams(bundle, this)
    supplementBundleWithContext(bundle, this)

    // Supplements the bundle with the exception if it exists.
    supplementBundleWithException(bundle, exception)

    // Log event to Facebook App Events and Firebase Analytics Events as a custom event (non-standard event)
    logEventToFacebookAppEvents(this, event, bundle)
    logEventToFirebaseAnalyticsEvents(this, event, bundle)

    // Supplement Bundle with event parameters (which are eventName and eventCase). We don't need to log them to Facebook App Events nor Firebase Analytics Events
    supplementBundleWithEventParams(bundle, event)

    // Log to LogDNA. It will include message and exception if they exist.
    logEventToLogDNA(event, eventCase, bundle, message, exception)

    // Log to LogCat. It will include message and exception if they exist.
    logEventToLogcatConsole(this, event, eventCase, bundle, message, exception)

    // Log to Firebase Crashlytics, if the exception exists.
    logToFirebaseCrashlytics(exception)
}

/**
 * Log Standard Facebook Events to Facebook only
 */
fun Context.logFacebookStandardEvent(
    event: FacebookStandardEvent,
    eventCase: EventCase,
    bundle: Bundle = Bundle(),
    valueToSum: Double? = null
) {
    // Checks that the eventCase is correct based on whether we have an exception or not
    checkWrongEventCase(eventCase)

    // Ensures Bundle is correct for the standard event parameters provided
    checkBundleForStandardEvent(event, bundle, valueToSum)

    // Adds the eventCase from the parameter into the event object
    supplementEventWithEventCase(event, eventCase)

    // Supplements the bundle with app parameters and context info
    supplementBundleWithAppParams(bundle, this)
    supplementBundleWithContext(bundle, this)

    // Log event to Facebook App Events as a standard event
    logEventToFacebookAppEvents(this, event, bundle, valueToSum)

    // Log to LogDNA. This will already handle if message and exception are present or null
    logEventToLogDNA(event, eventCase, bundle)

    // Log to LogCat. This will already handle if message and exception are present or null
    logEventToLogcatConsole(this, event, eventCase, bundle)
}

/**
 * Log Standard Firebase Events to Firebase only
 */
fun Context.logFirebaseStandardEvent(
    event: FirebaseStandardEvent,
    eventCase: EventCase,
    bundle: Bundle = Bundle()
) {
    // Checks that the eventCase is correct based on whether we have an exception or not
    checkWrongEventCase(eventCase)

    // Ensures Bundle is correct for the standard event parameters provided
    checkBundleForStandardEvent(event, bundle)

    // Adds the eventCase from the parameter into the event object
    supplementEventWithEventCase(event, eventCase)

    // Supplements the bundle with app parameters and context info
    supplementBundleWithAppParams(bundle, this)
    supplementBundleWithContext(bundle, this)

    // Log event to Firebase App Events as a standard event
    logEventToFirebaseAnalyticsEvents(this, event, bundle)

    // Log to LogDNA. This will already handle if message and exception are present or null
    logEventToLogDNA(event, eventCase, bundle)

    // Log to LogCat. This will already handle if message and exception are present or null
    logEventToLogcatConsole(this, event, eventCase, bundle)
}

/**
 * Log Message without an Event.
 *
 * In case the event has an error, make sure to add the exception object. Having an exception object will also log the error to LogDNA and Firebase Crashlytics
 */
fun Context.logMessage(bundle: Bundle = Bundle(), message: String, exception: Exception? = null) {
    // Supplements the bundle with app parameters and context info
    supplementBundleWithAppParams(bundle, this)
    supplementBundleWithContext(bundle, this)

    // Log to LogDNA. This will already handle if message and exception are present or null
    logToLogDNA(message, if (exception == null) Line.LEVEL_DEBUG else Line.LEVEL_ERROR, bundle)

    // Log to LogCat. This will already handle if message and exception are present or null
    logMessageToLogcatConsole(this, bundle, message, exception)

    // Log to Firebase Crashlytics, if the exception exists.
    logToFirebaseCrashlytics(exception)
}

private fun logEventToFacebookAppEvents(
    context: Context,
    event: BaseEvent,
    bundle: Bundle,
    valueToSum: Double? = null
) {
    val appEventsLogger = AppEventsLogger.newLogger(context)

    if (valueToSum != null)
        appEventsLogger.logEvent(event.getEventName(), valueToSum, bundle)
    else
        appEventsLogger.logEvent(event.getEventName(), bundle)
}

private fun logEventToFirebaseAnalyticsEvents(context: Context, event: BaseEvent, bundle: Bundle) {
    FirebaseAnalytics.getInstance(context).logEvent(event.getEventName(), bundle)
}

private fun logEventToLogDNA(
    event: BaseEvent,
    eventCase: EventCase,
    bundle: Bundle,
    message: String? = null,
    exception: Exception? = null
) {
    if (!shouldLogToLogDNA(event, eventCase, message, exception))
        return

    checkWrongEventCase(eventCase, exception)
    supplementBundleWithEventParams(bundle, event)
    supplementBundleWithException(bundle, exception)

    val level: String =
        if (exception != null)
            Line.LEVEL_ERROR
        else
            when (eventCase) {
                EventCase.ATTEMPT -> Line.LEVEL_INFO
                EventCase.SUCCESS -> Line.LEVEL_INFO
                EventCase.FAILURE -> Line.LEVEL_WARN
                EventCase.CANCEL -> Line.LEVEL_INFO
                EventCase.ERROR -> Line.LEVEL_ERROR
            }

    val logMessage = formulateLogMessage(
        event,
        eventCase,
        bundle,
        message,
        exception,
        isAddBundleToLogMessage = false
    )

    logToLogDNA(logMessage, level, bundle)
}

private fun logToFirebaseCrashlytics(exception: Exception? = null) {
    if (exception == null)
        return

    FirebaseCrashlytics.getInstance().recordException(exception)
}

// This is the lowest-level function to log to LogDNA, and shouldn't really be changed in anyway
private fun logToLogDNA(line: String, level: String, bundle: Bundle) {
    val logDnaLine =
        Line.Builder().setLine(line).setLevel(level).setTime(System.currentTimeMillis())

    for (key in bundle.keySet()) {
        if (bundle.getString(key) != null)
            logDnaLine.addCustomField(Line.CustomField(key, bundle.getString(key)!!))
    }

    log(logDnaLine.build())
}

// This is the lowest-level function to log to Logcat Console, and shouldn't really be changed in anyway
private fun logEventToLogcatConsole(
    context: Context,
    event: BaseEvent,
    eventCase: EventCase,
    bundle: Bundle,
    message: String? = null,
    exception: Exception? = null
) {
    val tag = getPageNameFromContext(context)

    val logMessage = formulateLogMessage(
        event,
        eventCase,
        bundle,
        message,
        exception,
        isAddBundleToLogMessage = true
    )

    when (eventCase) {
        EventCase.ATTEMPT -> Log.i(tag, logMessage, exception)
        EventCase.SUCCESS -> Log.i(tag, logMessage, exception)
        EventCase.FAILURE -> Log.w(tag, logMessage, exception)
        EventCase.CANCEL -> Log.i(tag, logMessage, exception)
        EventCase.ERROR -> Log.e(tag, logMessage, exception)
    }
}

private fun logMessageToLogcatConsole(
    context: Context,
    bundle: Bundle,
    message: String,
    exception: Exception? = null
) {
    val tag = getPageNameFromContext(context)
    val logMessage = formulateLogMessage(
        bundle = bundle,
        message = message,
        exception = exception,
        isAddBundleToLogMessage = true
    )

    if (exception == null) {
        Log.d(tag, logMessage)
    } else {
        Log.e(tag, logMessage, exception)
    }
}

/**
 * We supplement the event object with the eventCase
 */
private fun supplementEventWithEventCase(event: BaseEvent, eventCase: EventCase) {
    event.setEventCase(eventCase)
}

/**
 * We supplement the bundle with App Platform and App Version. In this function, we don't have an event in the parameters.
 */
private fun supplementBundleWithAppParams(bundle: Bundle, context: Context) {
    bundle.putString(BaseEventParams.APP_PLATFORM.toString(), APP_PLATFORM_ANDROID)
    bundle.putString(BaseEventParams.APP_VERSION.toString(), BuildConfig.VERSION_NAME)

    val user = SharedPreferencesManager.instance.user;

    if (user != null) {
        if (user.id != null) {
            bundle.putString(
                BaseEventParams.USER_ID.toString(),
                SharedPreferencesManager.instance.user?.id
            )
        }

        if (user.mobile != null) {
            bundle.putString(
                BaseEventParams.USER_MOBILE_NUMBER.toString(),
                SharedPreferencesManager.instance.user?.mobile
            )
        }

        if (user.email != null) {
            bundle.putString(
                BaseEventParams.USER_EMAIL.toString(),
                SharedPreferencesManager.instance.user?.email
            )
        }
    }

    bundle.putString(BaseEventParams.DEVICE_ID.toString(), context.getDeviceId())
}

/**
 * We supplement the bundle with activity name from context.
 */
private fun supplementBundleWithContext(bundle: Bundle, context: Context) {
    bundle.putString(BaseEventParams.SCREEN_NAME.toString(), getPageNameFromContext(context))
}

/**
 * We supplement the bundle with Event Name and Event Case, as well as eventName and eventCase.
 */
private fun supplementBundleWithEventParams(bundle: Bundle, event: BaseEvent) {
    bundle.putString(BaseEventParams.EVENT_NAME.toString(), event.getEventName())
    bundle.putString(BaseEventParams.EVENT_CASE.toString(), event.getEventCase().toString())
}

/**
 * We supplement the bundle with App Platform and App Version, as well as eventName and eventCase. In this function, we have an event in the parameters.
 */
private fun supplementBundleWithException(bundle: Bundle, exception: Exception? = null) {
    if (exception == null)
        return

    bundle.putString(BaseEventParams.ERROR.toString(), exception.stackTrace.toString())
}

private fun checkWrongEventCase(eventCase: EventCase, exception: Exception? = null) {
    if (exception == null && eventCase == EventCase.ERROR) {
        throw Exception("The eventCase should not be EventCase.Error in case we don't have an exception object. So change the eventCase to EventCase.FAILURE or EventCase.CANCEL")
    }

    if (exception != null && eventCase != EventCase.ERROR) {
        throw Exception("The eventCase should always be EventCase.ERROR in case we have an exception object")
    }
}

/**
 * We should always log to LogDNA in case of the following cases:
 * 1 - If there a message by the developer
 * 2 - If there is an exception
 * 3 - If we're in DEBUG mode
 * 4 - If it's a mutation event
 * 5 - If the event is a failure or error
 */
private fun shouldLogToLogDNA(
    event: BaseEvent,
    eventCase: EventCase,
    message: String? = null,
    exception: Exception? = null
): Boolean {
    if (message != null ||
        exception != null ||
        BuildConfig.DEBUG ||
        event.getEventType() == EventType.MUTATION ||
        eventCase == EventCase.FAILURE ||
        eventCase == EventCase.ERROR
    ) {
        return true
    }

    return false
}

private fun checkBundleForStandardEvent(
    event: BaseEvent,
    bundle: Bundle,
    valueToSum: Double? = null
) {
    if (event.hasStandardParams() && bundle.isEmpty) {
        when (event) {
            is FacebookStandardEvent -> {
                throw Exception("Bundle shouldn't be empty for this standard Facebook event: ${event.getEventName()}. We need to have a bundle with the standard Facebook params key and value for that event, since that Facebook event has standard params.")
            }
            is FirebaseStandardEvent -> {
                throw Exception("Bundle shouldn't be empty for this standard Firebase event: ${event.getEventName()}. We need to have a bundle with the standard Firebase params key and value for that event, since that Firebase event has standard params.")
            }
            else -> {
                throw Exception("Bundle shouldn't be empty for this standard event: ${event.getEventName()}. We need to have a bundle with the standard params key and value for that event, since that event has standard params.")
            }
        }
    }

    if (event.hasValueToSum() && valueToSum == null) {
        throw Exception("We should have a valueToSum value for this standard Facebook event which is ${event.getEventName()}. Please revise the documentation for this event")
    }
}

private fun formulateLogMessage(
    event: BaseEvent? = null,
    eventCase: EventCase? = null,
    bundle: Bundle? = null,
    message: String? = null,
    exception: Exception? = null,
    isAddBundleToLogMessage: Boolean? = null
): String {
    var logMessage = ""

    if (event != null) {
        if (eventCase == null) {
            throw Exception("When calling the formulateLogMessage function, if you enter an event parameter, you must also enter an eventCase parameter.")
        }

        logMessage = when (eventCase) {
            EventCase.ATTEMPT -> "User attempted to ${event.getEventName()}"
            EventCase.SUCCESS -> "User succeeded in ${event.getEventName()}"
            EventCase.FAILURE -> "User failed in ${event.getEventName()}"
            EventCase.CANCEL -> "User cancelled in ${event.getEventName()}"
            EventCase.ERROR -> "User faced an error in ${event.getEventName()}"
        }
    }

    if (event == null && eventCase != null) {
        throw Exception("When calling the formulateLogMessage function, if you enter an eventCase parameter, you must also enter an event parameter.")
    }

    if (message != null) {
        logMessage += if (logMessage == "") "$message" else ". $message"
    }

    if (exception != null) {
        val exceptionMessage = "Exception occurred: ${exception.message}."

        logMessage += if (logMessage == "") "${exceptionMessage}." else " ${exceptionMessage}."
    }

    if (bundle != null && message == null && event == null) {
        throw Exception("When calling the formulateLogMessage function, if you enter a bundle parameter, you must also enter a message or event parameter.")
    }

    if (isAddBundleToLogMessage == true && bundle != null) {
        val bundleMessage = bundleToString(bundle)

        logMessage += if (logMessage == "") "${bundleMessage}." else ". Bundle: ${bundleMessage}"
    }

    return logMessage
}

private fun bundleToString(bundle: Bundle): String {
    return bundle.keySet().joinToString(", ", "{", "}") { key -> "$key=${bundle[key]}" }
}

private fun getPageNameFromContext(context: Context): String {
    return context.javaClass.simpleName
}

// This function is used for logging multiple events for different feed types
fun Context.logMultipleEvents(
    event: CustomEvent,
    foodyModel: FoodyModel,
    eventCase: EventCase = EventCase.ATTEMPT
) {
    //TODO Re-write this whole function
    //========================== This changes later (TEMP)==========================================

    val bundle = when {
        foodyModel.isTypeTwoFeed -> {
            bundleOf(
                CustomEventParams.ACTIVITY_TYPE.toString() to ActivitiesTypes.SINGLE_LIKE_POST, //will be dynamic based on data from object
                CustomEventParams.ACTIVITY_PERSONA.toString() to foodyModel.userType,
                CustomEventParams.CONTENT_TYPE.toString() to foodyModel.contentType
            )
        }
        //        foodyModel.isTypeThreeFeed -> {
        //            TODO()//
        //        }
        //        foodyModel.isTypeFourFeed -> {
        //            TODO()//
        //        }
        else -> {
            bundleOf(

                //TYPE 1 PARAMS

                CustomEventParams.POST_TYPE.toString() to foodyModel.postType,
                CustomEventParams.POST_CREATOR_ID.toString() to foodyModel.id,
                CustomEventParams.POST_WITH_IMAGE.toString() to if (foodyModel.images.isNullOrEmpty()) 0 else 1,
                CustomEventParams.POST_WITH_TAGGED_OBJECT.toString() to if (foodyModel.category == null) 0 else 1,
                CustomEventParams.POST_USER_TYPE.toString() to foodyModel.userType,
                CustomEventParams.OBJECT_TYPE.toString() to foodyModel.objectType,
                CustomEventParams.CARD_TYPE.toString() to foodyModel.cardType,
                CustomEventParams.CONTENT_TYPE.toString() to foodyModel.contentType,
                CustomEventParams.POST_ID.toString() to foodyModel.id,
                CustomEventParams.POST_WITH_TEXT.toString() to if (foodyModel.text.isNullOrEmpty()) 0 else 1
            )
        }
    } //==========================this changes later(TEMP)==========================================

    if (eventCase == EventCase.CANCEL) {
        activity()?.logEvent(event = event, eventCase, bundle)
    } else {
        activity()?.logEvent(event = event, EventCase.ATTEMPT, bundle)
        activity()?.logEvent(event = event, EventCase.SUCCESS, bundle)
    }
}

fun loggingPostAction(
    context: Context,
    model: Result?,
    eventName: CustomEvent,
    eventCase: EventCase
) {
    //==================for logging

    context.activity()?.logEvent(
        event = eventName,
        eventCase,
        bundle = bundleOf(
            CustomEventParams.POST_TYPE.toString() to PostType.ORIGINAL.name, // add shared
            CustomEventParams.POST_CREATOR_ID.toString() to model?.activity?.user?.id,
            CustomEventParams.POST_WITH_IMAGE.toString() to if (model?.activity?.getImages().isNullOrEmpty()) 0 else 1,
            CustomEventParams.POST_WITH_TAGGED_OBJECT.toString() to if (getTaggedObject(model?.activity) == ObjectType.NA.name) 0 else 1,
            CustomEventParams.POST_USER_TYPE.toString() to model?.activity?.user?.role,
            CustomEventParams.OBJECT_TYPE.toString() to getTaggedObject(model?.activity),
//            CustomEventParams.CARD_TYPE.toString() to getTaggedObject(model?.activity),
            CustomEventParams.CONTENT_TYPE.toString() to model?.type,
            CustomEventParams.POST_ID.toString() to model?.activity?._id,
            CustomEventParams.POST_WITH_TEXT.toString() to if (model?.activity?.body?.isEmpty() == true) 0 else 1
        )
    )
}

fun getTaggedObject(feedModel: Activity?): String? {
    return when {
        feedModel?.taggedBrand != null -> return ObjectType.RESTAURANT.name
        feedModel?.taggedMeal != null -> ObjectType.MEAL.name
        feedModel?.taggedOffer != null -> ObjectType.OFFER.name
        else -> ObjectType.NA.name
    }
}