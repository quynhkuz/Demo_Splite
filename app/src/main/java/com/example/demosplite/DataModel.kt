package com.example.demosplite

import java.io.Serializable

data class DataModel(
    var id :Int,
    var sbd : String,
    var hoten :String,
    var toan :Float,
    var ly :Float,
    var hoa: Float
):Serializable {
}