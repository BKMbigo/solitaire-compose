package dev.gitlive.firebase

// Placed in a separate file due to an IDE cache error
open class FirebaseException(code: String?, cause: Throwable) : Exception("$code: ${cause.message}", cause)
