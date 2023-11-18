package externals

import dev.gitlive.firebase.externals.FirebaseOptions

/* This is just a workaround for lacking kotlin.js.json */
@JsFun("(applicationId, myApiKey, myProjectId) => ({ apiKey: myApiKey, appId: applicationId, projectId: myProjectId })")
external fun createJsFirebaseOptions(
    applicationId: JsString,
    myApiKey: JsString,
    myProjectId: JsString
): FirebaseOptions
