package dev.gitlive.firebase

//import dev.gitlive.firebase.externals.deleteApp
import dev.gitlive.firebase.externals.getApp
//import dev.gitlive.firebase.externals.getApps
import dev.gitlive.firebase.externals.initializeApp
import externals.createJsFirebaseOptions
//import kotlin.js.json
import dev.gitlive.firebase.externals.FirebaseApp as JsFirebaseApp

object Firebase

val Firebase.app: FirebaseApp
    get() = FirebaseApp(getApp())

fun Firebase.initialize(options: FirebaseOptions) =
    FirebaseApp(
        initializeApp(
            createJsFirebaseOptions(
                applicationId = options.applicationId.toJsString(),
                myApiKey = options.apiKey.toJsString(),
                myProjectId = options.projectId.toJsString()
            )
        )
    )

val Firebase.options: FirebaseOptions
    get() = Firebase.app.options


class FirebaseApp internal constructor(val js: JsFirebaseApp) {
    val name: String
        get() = js.name
    val options: FirebaseOptions
        get() = js.options.run {
            FirebaseOptions(
                appId,
                apiKey,
                projectId,
                databaseURL,
                gaTrackingId,
                storageBucket,
                messagingSenderId,
                authDomain
            )
        }
}

data class FirebaseOptions(
    val applicationId: String,
    val apiKey: String,
    val projectId: String,
    val databaseUrl: String? = null,
    val gaTrackingId: String? = null,
    val storageBucket: String? = null,
    val gcmSenderId: String? = null,
    val authDomain: String? = null
)

/* Lack of access to kotlin.js.json  */

//private fun FirebaseOptions.toJson() = json(
//    "apiKey" to apiKey,
//    "appId" to applicationId,
//    "databaseURL" to (databaseUrl ?: undefined),
//    "storageBucket" to (storageBucket ?: undefined),
//    "projectId" to (projectId ?: undefined),
//    "gaTrackingId" to (gaTrackingId ?: undefined),
//    "messagingSenderId" to (gcmSenderId ?: undefined),
//    "authDomain" to (authDomain ?: undefined)
//)


open class FirebaseNetworkException(code: String?, cause: Throwable) : FirebaseException(code, cause)
