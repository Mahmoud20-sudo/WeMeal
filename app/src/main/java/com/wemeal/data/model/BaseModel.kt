package com.wemeal.data.model

import androidx.room.Entity

@Entity
open class BaseModel {
     var message: String? = null
     var type: String? = null
     var errorModel: ErrorModel? = null
     var errorType: String? = null
     var error: NameModel? = null
     var numPages: Int? = null
     var currentPage: Int? = null
}
