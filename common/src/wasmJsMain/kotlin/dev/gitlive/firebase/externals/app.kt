@file:JsModule("firebase/app")

package dev.gitlive.firebase.externals


external fun initializeApp(options: FirebaseOptions, name: String = definedExternally): FirebaseApp

external fun getApp(name: String = definedExternally): FirebaseApp


external interface FirebaseApp : JsAny {
    val automaticDataCollectionEnabled: Boolean
    val name: String
    val options: FirebaseOptions
}

external interface FirebaseOptions {
    var apiKey: String
    var appId: String
    var authDomain: String?
    var databaseURL: String?
    var measurementId: String?
    var messagingSenderId: String?
    var gaTrackingId: String?
    var projectId: String
    var storageBucket: String?
}
