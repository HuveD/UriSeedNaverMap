package com.example.uriseednavermap.ui.map

enum class PlaceTab {
    pesticide {
        override fun keyword(): String = "농약사"
    },
    flower {
        override fun keyword(): String = "꽃집"
    };

    abstract fun keyword(): String;
}

