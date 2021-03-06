package app.backend.utils

fun isEmailValid(email: String): Boolean = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex().matches(email)
fun capitalize(name: String): String = name[0].uppercase() + name.substring(1).lowercase()

fun cleanName(name: String) = capitalize(name.trim())
fun cleanEmail(email: String) = email.trim().lowercase()
fun cleanPassword(password: String) = password.trim()
