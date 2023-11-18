package externals


@JsFun("(map) => Object.fromEntries(map)")
external fun <K : JsAny> toJsObject(map: K): JsAny

/* Workaround for reporting JS Exceptions (Not tested yet!) */
@JsFun("function catchJsExceptions(fn) { try { let t = fn(); return t; } catch(err) { console.log('Error Encountered' + err) } }")
external fun <T : JsAny> catchJsExceptions(function: () -> T): T

/* Workaround for printing json objects (Again a workaround for lacking kotlin.js.json in Kotlin/Wasm) */
@JsFun("function printJsObject(prefix, jsObject) { console.log(prefix + '' + JSON.stringify(jsObject)); }")
external fun printJsObject(prefix: String, jsObject: JsAny)
