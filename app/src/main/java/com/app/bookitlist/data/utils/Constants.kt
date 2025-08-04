package com.app.bookitlist.data.utils

object Constants {

    // API Configuration
    object Api {
        const val BASE_URL = "https://bookitlist.com/api.php"
        const val BASE_URL_STAGING = "https://staging-api.yourapp.com/v1/"
        const val BASE_URL_DEV = "https://dev-api.yourapp.com/v1/"

        // Timeouts
        const val CONNECT_TIMEOUT = 30L
        const val READ_TIMEOUT = 30L
        const val WRITE_TIMEOUT = 30L

        // Cache
        const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB
        const val CACHE_MAX_AGE = 5 // 5 seconds
        const val CACHE_MAX_STALE = 7 // 7 days

        // Headers
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_CONTENT_TYPE = "Content-Type"
        const val HEADER_ACCEPT = "Accept"
        const val HEADER_USER_AGENT = "User-Agent"
        const val HEADER_CACHE_CONTROL = "Cache-Control"

        // Content Types
        const val CONTENT_TYPE_JSON = "application/json"
        const val CONTENT_TYPE_FORM = "application/x-www-form-urlencoded"
        const val CONTENT_TYPE_MULTIPART = "multipart/form-data"
    }

    // Database Configuration
    object Database {
        const val DATABASE_NAME = "app_database"
        const val DATABASE_VERSION = 1

        // Table Names
        const val TABLE_USERS = "users"
        const val TABLE_POSTS = "posts"
        const val TABLE_CACHE = "cache"
    }

    // SharedPreferences Keys
    object Prefs {
        const val PREF_NAME = "app_preferences"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val USER_ID = "user_id"
        const val USER_EMAIL = "user_email"
        const val USER_NAME = "user_name"
        const val IS_FIRST_LAUNCH = "is_first_launch"
        const val THEME_MODE = "theme_mode"
        const val LANGUAGE = "language"
        const val NOTIFICATION_ENABLED = "notification_enabled"
    }

    // Request Codes
    object RequestCodes {
        const val CAMERA_PERMISSION = 1001
        const val STORAGE_PERMISSION = 1002
        const val LOCATION_PERMISSION = 1003
        const val NOTIFICATION_PERMISSION = 1004
        const val CAMERA_CAPTURE = 2001
        const val GALLERY_PICK = 2002
        const val FILE_PICK = 2003
    }

    // Intent Extras
    object Extras {
        const val USER_ID = "user_id"
        const val POST_ID = "post_id"
        const val TITLE = "title"
        const val MESSAGE = "message"
        const val URL = "url"
        const val DATA = "data"
    }

    // Error Codes
    object ErrorCodes {
        const val NETWORK_ERROR = 1000
        const val SERVER_ERROR = 1001
        const val UNAUTHORIZED = 1002
        const val FORBIDDEN = 1003
        const val NOT_FOUND = 1004
        const val VALIDATION_ERROR = 1005
        const val UNKNOWN_ERROR = 1006
    }

    // HTTP Status Codes
    object HttpStatus {
        const val OK = 200
        const val CREATED = 201
        const val NO_CONTENT = 204
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val METHOD_NOT_ALLOWED = 405
        const val CONFLICT = 409
        const val UNPROCESSABLE_ENTITY = 422
        const val INTERNAL_SERVER_ERROR = 500
        const val BAD_GATEWAY = 502
        const val SERVICE_UNAVAILABLE = 503
    }

    // Pagination
    object Pagination {
        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 100
        const val FIRST_PAGE = 1
    }

    // File Upload
    object FileUpload {
        const val MAX_FILE_SIZE = 10 * 1024 * 1024L // 10MB
        const val MAX_IMAGE_SIZE = 5 * 1024 * 1024L // 5MB
        const val ALLOWED_IMAGE_TYPES = "image/jpeg,image/png,image/gif"
        const val ALLOWED_FILE_TYPES = "application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    }

    // Date Formats
    object DateFormats {
        const val API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        const val DISPLAY_DATE_FORMAT = "MMM dd, yyyy"
        const val DISPLAY_TIME_FORMAT = "hh:mm a"
        const val DISPLAY_DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm a"
        const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }

    // Validation
    object Validation {
        const val MIN_PASSWORD_LENGTH = 8
        const val MAX_PASSWORD_LENGTH = 50
        const val MIN_NAME_LENGTH = 2
        const val MAX_NAME_LENGTH = 50
        const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        const val PHONE_PATTERN = "^[+]?[0-9]{10,15}$"
    }

    // App Configuration
    object App {
        const val APP_NAME = "YourApp"
        const val VERSION_NAME = "1.0.0"
        const val PRIVACY_POLICY_URL = "https://yourapp.com/privacy"
        const val TERMS_OF_SERVICE_URL = "https://yourapp.com/terms"
        const val SUPPORT_EMAIL = "support@yourapp.com"
        const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id="
    }

    // Notification Channels
    object NotificationChannels {
        const val GENERAL = "general"
        const val MESSAGES = "messages"
        const val UPDATES = "updates"
        const val PROMOTIONS = "promotions"
    }

    // Animation Durations
    object Animation {
        const val SHORT_DURATION = 200L
        const val MEDIUM_DURATION = 300L
        const val LONG_DURATION = 500L
    }
}