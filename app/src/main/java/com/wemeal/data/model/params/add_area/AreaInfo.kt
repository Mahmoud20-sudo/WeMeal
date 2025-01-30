package com.wemeal.data.model.params.add_area

import com.wemeal.data.model.NameModel

data class AreaInfo(
    var lat: Double?,
    var lng: Double?,
    var city: NameModel?,
    var country: NameModel?,
    var name: NameModel?,
)