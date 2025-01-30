package com.wemeal.data.model.user


data class PlaceDataModel(var id: String, var name: String, var description: String) {
    override fun toString(): String {
        return "PlaceDataModel(id='$id', name='$name')"
    }
}