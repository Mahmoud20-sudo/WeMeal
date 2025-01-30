package com.wemeal.presentation.util.events

enum class BaseEventParams
{
    SCREEN_NAME,
    EVENT_NAME,
    EVENT_CASE,
    APP_VERSION,
    APP_PLATFORM,
    USER_ID,
    USER_MOBILE_NUMBER,
    USER_EMAIL,
    DEVICE_ID,
    ERROR
}

enum class CustomEventParams
{
    //    USER_TYPE,//Restaurant, Foodie, Influencer Foodie, Verified Buyer Foodie
    POST_TYPE, //“Original” “Shared”
    POST_CREATOR_ID,
    POST_WITH_IMAGE, //0 or 1
    POST_WITH_TEXT, //0 or 1
    POST_WITH_TAGGED_OBJECT, //0 or 1
    POST_ID,
    POST_USER_TYPE, //Restaurant, Foodie, Influencer Foodie, Verified Buyer Foodie
    OBJECT_TYPE, //Meal, Restaurant, Offer, Order, NA
    CARD_TYPE, //“Meal”, “Restaurant”, “Offer”, “Order”, “Foodie”, “Restaurant”
    CONTENT_TYPE, //“Post”, “Activity”, “Promoted_Post”, “Recommendations”, “Invites”, “Card”
    ACTIVITY_TYPE, //Activity is either a single action done by a single foodie or a restaurant or a group action performed by many foodies
    ACTIVITY_PERSONA, //The persona who performed the activity. “Foodie”, “Influencer_Foodie” “Restaurant”
}

enum class ObjectType{
    RESTAURANT,
    MEAL,
    OFFER,
    ORDER,
    NA
}

enum class PostType{
    ORIGINAL,
    SHARED
}


enum class ActivitiesTypes
{
    SINGLE_LIKE_POST,
    SINGLE_LIKE_OBJECT_CARD,
    SINGLE_LIKE_COMMENT,
    SINGLE_LIKE_REPLY,
    SINGLE_COMMENT,
    SINGLE_REPLY,
    FOLLOWING_SINGLE_FOODIE,
    FOLLOWING_MANY_FOODIES,
    GROUP_FOLLOWING_FOODIE,
    FOLLOWING_SINGLE_RESTAURANT,
    FOLLOWING_MULTIPLE_RESTAURANT,
    GROUP_FOLLOWING_RESTAURANT,
    GROUP_SHARING,
    SINGLE_ORDERING,
    GROUP_ORDERING
}

enum class EventType
{
    VIEW,
    MUTATION
}

enum class EventCase
{
    ATTEMPT,
    SUCCESS,
    FAILURE,
    ERROR,
    CANCEL
}

interface BaseEvent
{
    fun getEventName(): String
    fun getEventType(): EventType?

    fun getEventCase(): EventCase?
    fun setEventCase(eventCase: EventCase)

    fun hasStandardParams(): Boolean
    fun hasValueToSum(): Boolean
}

const val APP_PLATFORM_ANDROID = "ANDROID"
const val CURRENCY_EGP = "EGP"
const val LOGIN_METHOD_FACEBOOK = "facebook"
const val LOGIN_METHOD_GOOGLE = "google"
