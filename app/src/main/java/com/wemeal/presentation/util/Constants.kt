package com.wemeal.presentation.util

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.wemeal.R
import com.wemeal.data.model.*

const val COLLAPSED_MAX_LINES = Int.MAX_VALUE
const val DEFAULT_ANIM_DURATION = 450
const val DEFAULT_ELLIPSIZED_TEXT = " "
val facebook_permissions = listOf("email", "public_profile", "user_friends", "user_gender")

const val RC_GOOGLE_SIGN_IN_CODE = 2555
const val splashFadeDurationMillis = 600
const val introTransitionDurationMillis = 5000L
const val placesSearchTimeoutDurationMillis = 30000L
const val repeatTimes = 1000
const val RC_LOCATION_NO_RESULT_FOUND = 112
const val PAGE_SIZE = 10
const val POST_TEXT_EMS = 2200
const val PICKER_REQUEST_CODE = 18981

const val UNAVAILABLE = "UNAVAILABLE"
const val ACCESS_TOKEN = "ACCESS_TOKEN"
const val USER = "USER"
const val LOADING = "LOADING"
const val SUCCESS = "SUCCESS"
const val FIRST_TIME = "FIRST_TIME"
const val RULES_AGREED = "RULES_AGREED"
const val VIOLATION = "violated"

val montserratBlack = Font(R.font.montserrat_black)
val montserratBlackItalic = Font(R.font.montserrat_blackitalic)
val montserratBold = Font(R.font.montserrat_bold)
val montserratExtraBold = Font(R.font.montserrat_extrabold)
val montserratExtraBoldItalic = Font(R.font.montserrat_extrabolditalic)
val montserratRegular = Font(R.font.montserrat_regular)
val montserratLight = Font(R.font.montserrat_light)
val montserratMedium = Font(R.font.montserrat_medium)
val montserratSemiBold = Font(R.font.montserrat_semibold)
val appleColorEmjoi = Font(R.font.apple_color_emjoi)


val placeFields: List<Place.Field> = listOf(
    Place.Field.ID,
    Place.Field.NAME,
    Place.Field.LAT_LNG,
    Place.Field.ADDRESS
)

enum class ImageScale {
    RECTANGULAR,
    SQUARE,
    PORTRAIT
}

enum class FeedType {
    POST,
    ACTIVITY,
    PROMOTED
}

enum class UserType {
    USER,
    FOODIE,
    BRAND
}

const val googlePath = "people/me"
const val googleContactsRequiredFields = "person.names,person.emailAddresses,person.phoneNumbers"

const val MAP_SCREEN = "MAP_SCREEN"
const val RESTAURANTS_SCREEN = "RESTAURANTS_SCREEN"
const val FOODIES_SCREEN = "FOODIES_SCREEN"
const val FINISHING_SCREEN = "FINISHING_SCREEN"

const val POSTED_ABOUT_TYPE = 1
const val SHARED_TYPE = 2
const val ORDERED_FROM_TYPE = 3
const val COMMENTED_ON_TYPE = 4
const val RECOMMENDED_TYPE = 5 //TYPE 4

const val NONE = -1
const val ORDERING_ACTIVITY = 10
const val POSTING_ACTIVITY = 1
const val COMMENTING_ACTIVITY = 3

const val COUNTRIES = "countries"
const val CITIES = "cities"
const val AREAS = "areas"
const val SUB = "sub"
const val SUB_AREAS = "sub-areas"

//================IMAGES CLICKS CAPTIONS======================
enum class ImageActions(var contentDescription: String, var clickLabel: String = "") {
    CLOSE("CLOSE", "CLOSE ACTION"),
    SEARCH("SEARCH", "SEARCH ACTION"),
    BACK("BACK", "BACK ACTION"),
    MORE("MORE", "MORE ACTION"),
    DETECT("DETECT", "DETECT LOCATION"),
    VIEW("VIEW", "VIEWING IMAGE"),
    FOLLOW("FOLLOW", "FOLLOW USER")
}
//================IMAGES CLICKS CAPTIONS======================

//================SCREENS ROUTINGS======================
const val HOME = "HOME"
const val RESTAURANTS = "RESTAURANTS"
const val OFFERS = "OFFERS"
const val FOODIES = "FOODIES"
const val PROFILE = "PROFILE"
const val MY_ACCOUNT = "MY_ACCOUNT"
const val DETAILS = "DETAILS"
const val IMAGES_PAGER = "images-pager/{index}/"
const val REPORT = "REPORT/{index}"
const val FOLLOWED_FOODIES = "FOLLOWED_FOODIES"
const val ALL_FOODIES = "ALL_FOODIES"
const val ALL_RESTAURANT = "ALL_RESTAURANT"
const val CREATE_POST = "CREATE_POST"
const val IMAGES_EDIT = "IMAGES_EDIT"
const val TAG_OBJECT = "TAG_OBJECT"//
const val GALLERY = "GALLERY"//
//================SCREENS ROUTINGS======================

val ShimmerColorShades = listOf(
    Color.LightGray.copy(0.9f),
    Color.LightGray.copy(0.2f),
    Color.LightGray.copy(0.9f)
)

val CountriesList = listOf(
    "Egypt"
)
val CitiesList = listOf(
    "Cairo",
    "Alexandria",
    "Giza",
    "Ismaliya",
    "Assuit",
    "Luxor",
    "Aswan",
    "Elbhera",
    "Sinai"
)
val CairoAreasList = listOf(
    "Nasr City",
    "Mohandseen",
    "Down Town",
    "Maadi",
    "Elmatarya",
    "Bolak",
    "Tagmoo"
)
val AlexandriaAreasList = listOf(
    "Iqbal",
    "Lauran",
    "Agami",
    "AbouEir",
    "Mandra",
    "Manshia",
    "Raml Station"
)
val SubAreasList = hashMapOf(
    "Abbas Akkad" to LatLng(30.0324686, 31.3032941),
    "Hay 7" to LatLng(30.0493814, 31.1865497),
    "Enbbi" to LatLng(30.0324315, 31.3029505),
    "Hay 10" to LatLng(29.9603244, 31.2706033),
    "Makram Ebaid" to LatLng(29.9603244, 31.2706033),
    "Waffa & Aml" to LatLng(29.9603244, 31.2706033),
    "Zahra Nasr City" to LatLng(29.9603244, 31.2706033)
)

//=================================temp lists====================================
val resturantsList = listOf(
    RestaurantModel(
        id = 1,
        name = "KTX 1", img = R.mipmap.ic_kix_img,
        cover = R.mipmap.ic_kix_cover,
        followersCount = 19.0f,
        isFollowed = mutableStateOf(false)
    ),
    RestaurantModel(
        id = 2,
        name = "Pastawesy", img = R.mipmap.ic_pasta_img,
        cover = R.mipmap.ic_pasta_cover,
        followersCount = 8.2f,
        isFollowed = mutableStateOf(false)
    ),
    RestaurantModel(
        id = 3,
        name = "McDonald's", img = R.mipmap.ic_mac_img,
        cover = R.mipmap.ic_mac_cover,
        followersCount = 1.0f,
        isFollowed = mutableStateOf(false)
    ),
    RestaurantModel(
        id = 4,
        name = "Dahan", img = R.mipmap.ic_dahan_img,
        cover = R.mipmap.ic_dahan_cover,
        followersCount = 1.2f,
        isFollowed = mutableStateOf(false)
    ),
    RestaurantModel(
        id = 5,
        name = "Cook Door", img = R.mipmap.ic_cookdoor_img,
        cover = R.mipmap.ic_cockdoor_cover,
        followersCount = 8.0f,
        isFollowed = mutableStateOf(false)
    ),
    RestaurantModel(
        id = 6,
        name = "Chicken Chester", img = R.mipmap.ic_chicken_img,
        cover = R.mipmap.ic_chicken_cover,
        followersCount = 8.2f,
        isFollowed = mutableStateOf(false)
    )

)
val foodiesList = listOf<FoodyModel>(
//    FoodyModel(
//        id = -9, firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        isTypeFourFeed = true,
//        isFoodieActivity = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE)
//        ),
//        category = Category(
//            99,
//            30,
//            R.drawable.ic_dinner,
//            "Coleslaw",
//            "Chicken Chester",
//            false,
//            messageText = "Chicken Chester is closed now"
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nagy",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = -8, firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        isTypeFourFeed = true,
//        isRestaurantActivity = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.atoms_image_image_enabled, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            99,
//            30,
//            R.drawable.ic_dinner,
//            "Coleslaw",
//            "Chicken Chester",
//            false,
//            messageText = "Chicken Chester is closed now"
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nagy",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = -7, firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        isTypeThreeFeed = true,
//        promotionReason = "",
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.atoms_image_image_enabled, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            99,
//            30,
//            R.drawable.ic_dinner,
//            "Coleslaw",
//            "Chicken Chester",
//            false,
//            messageText = "Chicken Chester is closed now"
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nagy",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = -6, firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        isTypeThreeFeed = true,
//        promotionReason = "This meal has been ordered many times",
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.atoms_image_image_enabled, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            99,
//            30,
//            R.drawable.ic_dinner,
//            "Fries",
//            "Chicken Chester",
//            false,
//            messageText = "Chicken Chester is closed now"
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nagy",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = -5, firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.image_rectangle,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        isTypeThreeFeed = true,
//        promotionReason = "This meal was recently added",
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.atoms_image_image_enabled, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            99,
//            30,
//            R.drawable.ic_dinner,
//            "Fries",
//            "Chicken Chester",
//            true
//        )
//    ),
//    FoodyModel(
//        id = -4, firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        isTypeThreeFeed = true,
//        promotionReason = "This offer was recently added",
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.atoms_image_image_enabled, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            97,
//            30,
//            R.drawable.ic_offers,
//            "Chicken Fillet Combo with Pepsi, large Coleslaw and French Fries",
//            "Chicken Chester",
//            true
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nagy",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = -3, firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        isTypeThreeFeed = true,
//        promotionReason = "This offer has been ordered many times",
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.atoms_image_image_enabled, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            97,
//            30,
//            R.drawable.ic_offers,
//            "Chicken Fillet Combo with Pepsi, large Coleslaw and French Fries",
//            "Chicken Chester",
//            false,
//            messageText = "Chicken Chester is closed now"
//        )
//    ),
//    FoodyModel(
//        id = -2, firstName = "Dahan",
//        lastName = "",
//        img = R.mipmap.ic_dahan_img,
//        cover = R.mipmap.ic_dahan_cover,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        isTypeThreeFeed = true,
//        promotionReason = "This restaurant is trending recently",
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_rectangle, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            98,
//            30,
//            R.drawable.ic_fork_no_bg,
//            "Dahan",
//            "Cafe & Restaurant",
//            true
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nagy",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = -1, firstName = "Dahan",
//        lastName = "",
//        img = R.mipmap.ic_dahan_img,
//        cover = R.mipmap.ic_dahan_cover,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        isTypeThreeFeed = true,
//        promotionReason = "Dahan has Just Joined Qurba",
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_rectangle, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            99,
//            30,
//            R.drawable.ic_fork_no_bg,
//            "Dahan",
//            "Cafe & Restaurant",
//            messageText = "The restaurant is currently busy",
//            orderable = false
//        )
//    ),
//    FoodyModel(
//        id = 0,
//        firstName = "Mahmoud",
//        lastName = "Mohamed",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_chicken_cover,
//        followersCount = 1.0f,
//        postsCount = 200,
//        isVerified = false,
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        text = "TEEEEEEEEE",
//        isManyUser = false,
//        isOneFollowedUser = true,
//        otherFoodiesCount = 27,
//        isFoodieActivity = true,
//        foodyActivityType = 8,
//        followedUserImg = R.mipmap.avatar_copy_3,
//        followedUserName = "Mariam Emad",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE)
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nagy",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = 1,
//        firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isRestaurantActivity = true,
//        restaurantActivityType = 1,
//        followedUserImg = R.mipmap.avatar_copy_3,
//        otherFoodiesCount = 6,
//        followedUserName = "Haidy Nagy",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            97,
//            30,
//            R.drawable.ic_offers,
//            "Chicken Fillet Combo with Pepsi, large Coleslaw and French Fries",
//            "Chicken Chester",
//            true
//        )
//    ),
//    FoodyModel(
//        id = 2,
//        firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isRestaurantActivity = true,
//        restaurantActivityType = 1,
//        followedUserImg = R.mipmap.avatar_copy_3,
//        otherFoodiesCount = 6,
//        followedUserName = "Ali Osama",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            98,
//            30,
//            R.drawable.ic_fork_no_bg,
//            "Chicken Chester",
//            "Restaurant & Cafe",
//            true
//        )
//    ),
//    FoodyModel(
//        id = 3,
//        firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isRestaurantActivity = true,
//        restaurantActivityType = 1,
//        followedUserImg = R.mipmap.avatar_copy_3,
//        otherFoodiesCount = 6,
//        followedUserName = "Doaa Salaheldin",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            99,
//            30,
//            R.drawable.ic_dinner,
//            "Chicken Fillet Combo with Pepsi, large Coleslaw and French Fries",
//            "Chicken Chester",
//            true
//        )
//    ),
//    FoodyModel(
//        id = 4,
//        firstName = "Mahmoud",
//        lastName = "Mohamed",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_chicken_cover,
//        followersCount = 1.0f,
//        postsCount = 200,
//        isVerified = false,
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isManyUser = false,
//        isOneFollowedUser = true,
//        foodyActivityType = 7,
//        followedUserImg = R.mipmap.ic_chicken_img,
//        followedUserName = "Chicken Chester",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE)
//        ),
//        category = Category(
//            1,
//            33,
//            R.mipmap.ic_chicken_img,
//            "Chicken Chester",
//            "Cafe & Restaurant",
//            false
//        )
//    ),
//    FoodyModel(
//        id = 5,
//        firstName = "Haidy",
//        lastName = "Nagy",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.image_rectangle,
//        followersCount = 1.0f,
//        postsCount = 200,
//        isVerified = false,
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isManyUser = false,
//        isOneFollowedUser = true,
//        foodyActivityType = 5,
//        followedUserImg = R.mipmap.ic_chicken_img,
//        followedUserName = "Chicken Chester",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE)
//        ),
//        category = Category(
//            1,
//            33,
//            R.mipmap.ic_chicken_img,
//            "Buy 2 Chicken Burger Combo and 2 Large Coleslaw with only 65 EGP",
//            "Chicken Chester",
//            false
//        )
//    ),
//    FoodyModel(
//        id = 6,
//        firstName = "Haidy",
//        lastName = "Nagy",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.image_tv_16_9,
//        followersCount = 1.0f,
//        postsCount = 200,
//        isVerified = false,
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isManyUser = false,
//        isOneFollowedUser = true,
//        foodyActivityType = 6,
//        followedUserImg = R.mipmap.ic_chicken_img,
//        followedUserName = "Chicken Chester",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE)
//        ),
//        category = Category(
//            99,
//            33,
//            R.mipmap.ic_chicken_img,
//            "Fries",
//            "Chicken Chester",
//            true
//        )
//    ),
//    FoodyModel(
//        id = 7, firstName = "Haidy",
//        lastName = "Mohamed",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 180.0f,
//        postsCount = 100,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        date = "2021-11-14 13:33:00",
//        isFollowed = mutableStateOf(false),
//        sharedPostIndex = 32,
//        isTypeTwoFeed = false,
//        isFoodieActivity = true,
//        foodyActivityType = 1,
//        followedUserImg = R.mipmap.avatar_copy_3,
//        otherFoodiesCount = 6,
//        followedUserName = "Yassmin Gamal",//Mcdonald’s liked Doaa Salaheldin’s Post
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//        )
//    ),
//    FoodyModel(
//        id = 8,
//        firstName = "Doaa",
//        lastName = "Salaheldin",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFoodieActivity = true,
//        foodyActivityType = 2,
//        followedUserImg = R.mipmap.avatar_copy_3,
//        otherFoodiesCount = 6,
//        followedUserName = "Mariam Emad",//Mcdonald’s liked Doaa Salaheldin’s Post
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT)
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nagy",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = 9,
//        firstName = "Bissan",
//        lastName = "Gad",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "TEST",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFoodieActivity = true,
//        isOneFollowedUser = true,
//        foodyActivityType = 4,
//        followedUserImg = R.drawable.ic_temp,
//        otherFoodiesCount = 27,
//        followedUserName = "Mahmoud Ali",
//        images = listOf(
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT)
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nagy",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum ",
//                replies = listOf(
//                    Comment(
//                        55,
//                        "Mahmoud Ali", R.drawable.ic_temp, "HELLO"
//                    )
//                )
//            )
//        )
//    ),
//    FoodyModel(
//        id = 10,
//        firstName = "Mahmoud",
//        lastName = "Mohamed",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFoodieActivity = true,
//        isOneFollowedUser = false,
//        foodyActivityType = 3,
//        followedUserImg = R.mipmap.avatar_copy_3,
//        otherFoodiesCount = 27,
//        followedUserName = "Doaa Salaheldin",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Doaa Salaheldin",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = 11,
//        firstName = "Mahmoud",
//        lastName = "Mohamed",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFoodieActivity = true,
//        foodyActivityType = 3,
//        followedUserImg = R.mipmap.avatar_copy_3,
//        otherFoodiesCount = 27,
//        followedUserName = "Doaa Salaheldin",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Doaa Salaheldin",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = 12,
//        firstName = "Haidy",
//        lastName = "Nagy",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 1.0f,
//        postsCount = 200,
//        isVerified = false,
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isManyUser = false,
//        isOneFollowedUser = true,
//        otherFoodiesCount = 5,
//        foodyActivityType = ORDERING_ACTIVITY,
//        followedUserImg = R.mipmap.ic_chicken_img,
//        followedUserName = "Chicken Chester",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE)
//        ),
//        category = Category(
//            1,
//            33,
//            R.mipmap.ic_chicken_img,
//            "Fries",
//            "Chicken Chester",
//            true
//        )
//    ),
//    FoodyModel(
//        id = 13,
//        firstName = "Ali",
//        lastName = "Sirag",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_mac_cover,
//        followersCount = 1.0f,
//        postsCount = 200,
//        isVerified = false,
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isManyUser = false,
//        isOneFollowedUser = false,
//        otherFoodiesCount = 3,
//        foodyActivityType = ORDERING_ACTIVITY,
//        followedUserImg = R.mipmap.ic_mac_img,
//        followedUserName = "Macdonald's",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT)
//        ),
//        category = Category(
//            1,
//            33,
//            R.mipmap.ic_mac_img,
//            "Chicken MACDO, Carmel Sandwich, Fries, large Coleslaw and Pepsei can",
//            "MacDonald's",
//            false
//        )
//    ),
//    FoodyModel(
//        id = 14,
//        firstName = "Yassmin",
//        lastName = "Gamal",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isRestaurantActivity = true,
//        restaurantActivityType = 5,
//        isOneFollowedUser = false,
//        followedUserImg = R.mipmap.avatar_copy_3,
//        otherFoodiesCount = 27,
//        followedUserName = "Doaa Salaheldin",
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Haidy Nage",
//                R.mipmap.avatar_copy_3,
//                "Lorem ipsum "
//            )
//        ),
//        category = Category(
//            98,
//            42,
//            R.mipmap.ic_chicken_img,
//            "Chicken Chester",
//            "Cafe & Restaurant",
//            true
//        )
//    ),
//    FoodyModel(
//        id = 15,
//        firstName = "Doaa",
//        lastName = "Salaheldin",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFollowingActivity = true,
//        isRestaurantActivity = true,
//        restaurantActivityType = 3,
//        followedUserImg = R.mipmap.ic_mac_img,
//        otherFoodiesCount = 6,
//        followedUserName = "Mcdonald’s",//Mcdonald’s liked Doaa Salaheldin’s Post
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT)
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(
//                1,
//                "Doaa Salaheldin", R.mipmap.avatar_copy_3, "Lorem ipsum "
//            )
//        )
//    ),
//    FoodyModel(
//        id = 16,
//        firstName = "Doaa",
//        lastName = "Salaheldin",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFollowingActivity = false,
//        isRestaurantActivity = true,
//        restaurantActivityType = 2,
//        followedUserImg = R.mipmap.ic_mac_img,
//        otherFoodiesCount = 6,
//        followedUserName = "Mcdonald’s",//Mcdonald’s liked Doaa Salaheldin’s Post
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        ),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(Comment(1, "Macdonald's", R.mipmap.ic_mac_img, "Lorem ipsum "))
//    ),
//    FoodyModel(
//        id = 17,
//        firstName = "Doaa",
//        lastName = "Salaheldin",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 556f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFollowingActivity = false,
//        isRestaurantActivity = true,
//        restaurantActivityType = 1,
//        followedUserImg = R.mipmap.ic_mac_img,
//        otherFoodiesCount = 6,
//        followedUserName = "Mcdonald’s",//Mcdonald’s liked Doaa Salaheldin’s Post
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE)
//        ),
//        category = Category(
//            71,
//            42,
//            R.mipmap.ic_mac_img,
//            "Buy 2 Chicken Burger Combo and get one free for the sake of offer",
//            "Mcdonald’s",
//            true
//        )
//    ),
//    FoodyModel(
//        id = 18, firstName = "Amr", // -3
//        lastName = "Osama",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.atoms_image_image_enabled,
//        followersCount = 1.2f,
//        postsCount = 200,
//        isVerified = false,
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFollowingActivity = true,
//        isManyUser = true,
//        isRestaurantActivity = true,
//        isOneFollowedUser = false,
//        followedUserImg = R.mipmap.ic_chicken_img,
//        otherFoodiesCount = 6,
//        followedUserName = "Chicken Chester",
////        category = Category(
////            2,
////            33,
////            R.mipmap.ic_chicken_img,
////            "Chicken Chester",
////            "Fried Chicken",
////            true
////        )
//    ),
//    FoodyModel(
//        id = 19,
//        firstName = "Mahmoud",
//        lastName = "Sharki",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.ic_dahan_cover,
//        followersCount = 1.0f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit and laborum.",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFollowingActivity = true,
//        isFoodieActivity = true,
//        isManyUser = false,
//        otherFoodiesCount = 5,
//        foodyActivityType = POSTING_ACTIVITY,
//        followedUserImg = R.mipmap.ic_dahan_img,
//        followedUserName = "Dahan"
//    ),
//    FoodyModel(
//        id = 20,
//        firstName = "Doaa",
//        lastName = "Saleh",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.ic_pasta_cover,
//        followersCount = 1.0f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit and laborum.",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFoodieActivity = true,
//        isFollowingActivity = true,
//        isManyUser = false,
//        isOneFollowedUser = false,
//        otherFoodiesCount = 3,
//        foodyActivityType = POSTING_ACTIVITY,
//        followedUserImg = R.mipmap.ic_pasta_img,
//        followedUserName = "Pastawesy"
//    ),
//    FoodyModel(
//        id = 21, firstName = "Nagah",
//        lastName = "Sami",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.ic_mac_cover,
//        followersCount = 1.0f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit and laborum.",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFollowingActivity = true,
//        isRestaurantActivity = true,
//        isManyUser = true,
//        otherFoodiesCount = 4,
//        followedUserName = "McDonald's"
//    ),
//    FoodyModel(
//        id = 22, firstName = "Haidy",
//        lastName = "Gamal",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_kix_cover,
//        followersCount = 1.2f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit and laborum.",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        isTypeTwoFeed = true,
//        isFollowingActivity = true,
//        isFoodieActivity = true,
//        isManyUser = true,
//        isRestaurantActivity = false,
//        otherFoodiesCount = 7,
//        followedUserName = "Mahmoud Mohamed"
//    ),
//    FoodyModel(
//        id = 27, firstName = "Yassmine",
//        lastName = "جمال",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_dahan_cover,
//        followersCount = 1.2f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit and laborum.",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false)
//    ),
//    FoodyModel(
//        id = 28, firstName = "Haidy",
//        lastName = "Mohamed",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 180.0f,
//        postsCount = 100,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        date = "2021-11-14 13:33:00",
//        isFollowed = mutableStateOf(false),
//        sharedPostIndex = 34
//    ),
//    FoodyModel(
//        id = 29, firstName = "Haidy",
//        lastName = "Mohamed",
//        taggedObjectName = "Chicken Chester Miami ",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 180.0f,
//        postsCount = 100,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        date = "2021-11-14 13:33:00",
//        isFollowed = mutableStateOf(false),
//        sharedPostIndex = 37
//    ),
//    FoodyModel(
//        id = 30, firstName = "Haidy",
//        lastName = "Mohamed",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 180.0f,
//        postsCount = 100,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        date = "2021-11-14 13:33:00",
//        isFollowed = mutableStateOf(false),
//        sharedPostIndex = 26
//    ),
//    FoodyModel(
//        id = 31, firstName = "Amr",
//        lastName = "Moemn",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 1.0f,
//        postsCount = 303,
//        isVerified = true,
//        text = "Final post",
//        date = "2021-10-26 13:33:00",
//        isFollowed = mutableStateOf(false),
//        category = Category(
//            4,
//            24,
//            R.mipmap.ic_dahan_cover,
//            "Buy 2 Grilled Chicken Meal and get one Grilled Shawerma Sandwich",
//            "Dahan",
//            true
//        )
//    ),
//    FoodyModel(
//        id = 32, firstName = "Amr",
//        lastName = "Moemn",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_mac_cover,
//        followersCount = 1.0f,
//        postsCount = 303,
//        isVerified = true,
//        text = "AaAaaaaaaaaahdasjkdgakjdgaskjdgaksdgakjdsasgdkjsagdakjsgdaskjdgaskjdgaskjdgaskjdgakjdgaskjdagsdkjagdjkadsgakjsdgajksdgasjkdgajksdgajksdgadsjkagsdjagsdkjasdgakjsdgaskdgaskdgaksjdgasdkjagsdkjagdakjsdakjsdakjsdgakjsd",
//        date = "2021-10-19 13:33:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE),
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE),
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        )
//    ),
//    FoodyModel(
//        id = 33, firstName = "أكرم",
//        lastName = "توفيق",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_pasta_cover,
//        followersCount = 1.4f,
//        postsCount = 50,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident.لوريم ايبسوم دولار سيت أميت ,كونسيكتيتور أدايبا يسكينج أليايت,سيت دو أيوسمود تيمبور أنكايديديونتيوت لابوري ات دولار ماجنا أليكيوا . يوت انيم أد مينيم فينايم,كيواس نوستريد أكسير سيتاشن يللأمكو لابورأس نيسي يت أليكيوب أكس أيا كوممودو كونسيكيوات .",
//        date = "2021-10-19 13:52:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE),
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        )
//    ),
//    FoodyModel(
//        id = 34, firstName = "Yassmine",
//        lastName = "جمال",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_dahan_cover,
//        followersCount = 1.2f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Test",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE),
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        )
//    ),
//    FoodyModel(
//        id = 35, firstName = "Y@\$$!ne",
//        lastName = "G@m@[",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_kix_cover,
//        followersCount = 3.0f,
//        postsCount = 803,
//        isVerified = false,
//        text = "HELLO",
//        date = "2021-11-03 16:30:00",
//        isFollowed = mutableStateOf(false),
//        isLiked = mutableStateOf(true),
//        images = listOf(Img(R.mipmap.image_portrait, ImageScale.PORTRAIT)),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(1, "Samy Omsan", R.drawable.ic_temp, "Lorem ipsum "),
//            Comment(2, "Ali Sameh", R.drawable.ic_temp, "AAAAAAAAA")
//        ),
//        category = Category(
//            3,
//            24,
//            R.drawable.ic_offers,
//            "Buy 2 Chicken Burger Combo and get one free for the sake of offer",
//            "Chicken Chester",
//            orderable = false,
//            deleted = true,
//            messageText = "This Restaurant is no longer available"
//        )
//    ),
//    FoodyModel(
//        id = 36, firstName = "Hossam",
//        lastName = "Eldeen Abdulrahmaa Ali Hossam Zanaty",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_mac_cover,
//        followersCount = 1.2f,
//        postsCount = 105,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
//        date = "2021-11-02 12:00:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT)
//        ),
//        category = Category(
//            2,
//            24,
//            R.mipmap.ic_cookdoor_img,
//            "Cookdoor",
//            "Cafe & Restaurants",
//            false,
//            messageText = "Cookdoor is closed now"
//        )
//    ),
//    FoodyModel(
//        id = 37, firstName = "Mahmoud",
//        lastName = "Mohamed \uD83C\uDF69",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 8.2f,
//        postsCount = 235,
//        isVerified = true,
//        text = "أيوتي أريري دولار إن ريبريهينديرأيت فوليوبتاتي فيلايت أيسسي كايلليوم دولار أيو فيجايت نيولا باراياتيور. أيكسسيبتيور ساينت أوككايكات كيوبايداتات نون بروايدينت ,سيونت ان كيولبا كيو أوفيسي ديسيريو نتموليت انيم أيدي ايست لابوريوم لابورأس نيسي يت أليكيوب أكس أيا كوممودو كونسيكيوات . ديواس أيوتي أريري دولار إن ريبريهينديرأيت فوليوبتاتي فيلايت أيسسي لوريم ايبسوم دولار سيت أميت ,كونسيكتيتور أدايبا يسكينج أليايت,سيت دو أيوسمود تيمبور أنكايديديونتيوت لابوري ات دولار ماجنا أليكيوا . يوت انيم أد مينيم فينايم,كيواس نوستريد أكسير سيتاشن يللأمكو لابورأس نيسي يت أليكيوب أكس أيا كوممودو كونسيكيوات . ديواس",
//        date = "2021-11-03 17:15:00",
//        isFollowed = mutableStateOf(false),
//        isLiked = mutableStateOf(true),
//        commentsCount = mutableStateOf(112),
//        comments = listOf(
//            Comment(1, "Nagah Hussein", R.drawable.ic_temp, "Lorem ipsum "),
//            Comment(2, "Akram Eltayeb", R.drawable.ic_temp, "AAAAAAAAA")
//        ),
//        images = listOf(Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)),
//        category = Category(
//            1,
//            24, R.mipmap.ic_chicken_img,
//            "Chicken Burger Combo with Pepsi , coleslaw and a large fries", "Chicken Chester", true
//        )
//    ),
//    FoodyModel(
//        id = 38, firstName = "Chicken",
//        lastName = "Chester",
//        img = R.mipmap.ic_chicken_img,
//        cover = R.mipmap.ic_chicken_cover,
//        followersCount = 1.14f,
//        postsCount = 100,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.atoms_image_image_enabled, ImageScale.RECTANGULAR)
//        ),
//        category = Category(
//            99,
//            30,
//            R.drawable.ic_dinner,
//            "Chicken Burger Combo with Pepsi, large Coleslaw and French Fries",
//            "Chicken Chester",
//            true
//        )
    //)
)
val followedFoodiesList = listOf<FoodyModel>(
//    FoodyModel(
//        id = 20, firstName = "Yassmine",
//        lastName = "جمال",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_dahan_cover,
//        followersCount = 1.2f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit and laborum.",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false)
//    ),
//    FoodyModel(
//        id = 21, firstName = "Haidy",
//        lastName = "Mohamed",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 180.0f,
//        postsCount = 100,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        date = "2021-11-14 13:33:00",
//        isFollowed = mutableStateOf(false),
//        sharedPostIndex = 10
//    ),
//    FoodyModel(
//        id = 22, firstName = "Haidy",
//        lastName = "Mohamed",
//        taggedObjectName = "Chicken Chester Miami ",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 180.0f,
//        postsCount = 100,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        date = "2021-11-14 13:33:00",
//        isFollowed = mutableStateOf(false),
//        sharedPostIndex = 37
//    ),
//    FoodyModel(
//        id = 23, firstName = "Haidy",
//        lastName = "Mohamed",
//        img = R.mipmap.avatar_copy_3,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 180.0f,
//        postsCount = 100,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eros est, blandit eu nunc sit amet",
//        date = "2021-11-14 13:33:00",
//        isFollowed = mutableStateOf(false),
//        sharedPostIndex = 1
//    ),
//    FoodyModel(
//        id = 24, firstName = "Amr",
//        lastName = "Moemn",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_cockdoor_cover,
//        followersCount = 1.0f,
//        postsCount = 303,
//        isVerified = true,
//        text = "Final post",
//        date = "2021-10-26 13:33:00",
//        isFollowed = mutableStateOf(false),
//        category = Category(
//            4,
//            24,
//            R.mipmap.ic_dahan_cover,
//            "Buy 2 Grilled Chicken Meal and get one Grilled Shawerma Sandwich",
//            "Dahan",
//            true
//        )
//    ),
//    FoodyModel(
//        id = 25, firstName = "Amr",
//        lastName = "Moemn",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_mac_cover,
//        followersCount = 1.0f,
//        postsCount = 303,
//        isVerified = true,
//        text = "AaAaaaaaaaaahdasjkdgakjdgaskjdgaksdgakjdsasgdkjsagdakjsgdaskjdgaskjdgaskjdgaskjdgakjdgaskjdagsdkjagdjkadsgakjsdgajksdgasjkdgajksdgajksdgadsjkagsdjagsdkjasdgakjsdgaskdgaskdgaksjdgasdkjagsdkjagdakjsdakjsdakjsdgakjsd",
//        date = "2021-10-19 13:33:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE),
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE),
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        )
//    ),
//    FoodyModel(
//        id = 26, firstName = "أكرم",
//        lastName = "توفيق",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_pasta_cover,
//        followersCount = 1.4f,
//        postsCount = 50,
//        isVerified = true,
//        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident.لوريم ايبسوم دولار سيت أميت ,كونسيكتيتور أدايبا يسكينج أليايت,سيت دو أيوسمود تيمبور أنكايديديونتيوت لابوري ات دولار ماجنا أليكيوا . يوت انيم أد مينيم فينايم,كيواس نوستريد أكسير سيتاشن يللأمكو لابورأس نيسي يت أليكيوب أكس أيا كوممودو كونسيكيوات .",
//        date = "2021-10-19 13:52:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR),
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE),
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        )
//    ),
//    FoodyModel(
//        id = 27, firstName = "Yassmine",
//        lastName = "جمال",
//        img = R.drawable.ic_temp,
//        cover = R.mipmap.ic_dahan_cover,
//        followersCount = 1.2f,
//        postsCount = 200,
//        isVerified = false,
//        text = "Test",
//        date = "2021-10-26 12:30:00",
//        isFollowed = mutableStateOf(false),
//        images = listOf(
//            Img(R.mipmap.image_portrait, ImageScale.PORTRAIT),
//            Img(R.mipmap.image_rectangle, ImageScale.SQUARE),
//            Img(R.mipmap.image_tv_16_9, ImageScale.RECTANGULAR)
//        )
//    )
)