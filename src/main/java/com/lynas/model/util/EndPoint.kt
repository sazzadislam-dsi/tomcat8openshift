package com.lynas.model.util

import java.io.Serializable

data class EndPoint(
        val root:String,
        val method:String,
        val path:String,
        val reqBody: Serializable
)