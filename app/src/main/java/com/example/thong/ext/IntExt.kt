package com.example.thong.ext

fun Int?.displayData(): String {
    if (this == null) return "0"
    if (this > 100) return "100+"
    if (this > 10) return "10+"
    return this.toString()
}