package de.hergt.envidualcodingchallenge.extensions

fun Int.toTwoDigits(): String {
    return if (IntRange(0, 9).contains(this)) {
        "0$this"
    } else this.toString()
}