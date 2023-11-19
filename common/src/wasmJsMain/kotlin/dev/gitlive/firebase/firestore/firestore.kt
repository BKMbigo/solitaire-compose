/*
 * Copyright (c) 2020 GitLive Ltd.  Use of this source code is governed by the Apache 2.0 license.
 */

package dev.gitlive.firebase.firestore

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.FirebaseException
import dev.gitlive.firebase.firestore.externals.*
import externals.catchJsExceptions
import kotlinx.coroutines.await
import dev.gitlive.firebase.firestore.externals.CollectionReference as JsCollectionReference
import dev.gitlive.firebase.firestore.externals.DocumentReference as JsDocumentReference
import dev.gitlive.firebase.firestore.externals.DocumentSnapshot as JsDocumentSnapshot
import dev.gitlive.firebase.firestore.externals.FieldPath as JsFieldPath
import dev.gitlive.firebase.firestore.externals.Query as JsQuery
import dev.gitlive.firebase.firestore.externals.QuerySnapshot as JsQuerySnapshot
import dev.gitlive.firebase.firestore.externals.where as jsWhere


val Firebase.firestore
    get() = rethrow { FirebaseFirestore(getFirestore()) }

fun Firebase.firestore(app: FirebaseApp) =
    rethrow { FirebaseFirestore(getFirestore(app.js)) }

class FirebaseFirestore(jsFirestore: Firestore) {

    var js: Firestore = jsFirestore
        private set

    fun collection(collectionPath: String) = rethrow { CollectionReference(collection(js, collectionPath)) }

    fun document(documentPath: String) = rethrow { DocumentReference(doc(js, documentPath)) }
}

typealias NativeDocumentReference = JsDocumentReference

class DocumentReference(internal val nativeValue: NativeDocumentReference) {
    val js: NativeDocumentReference by ::nativeValue

    val id: String
        get() = rethrow { js.id }

    val path: String
        get() = rethrow { js.path }

    val parent: CollectionReference
        get() = rethrow { CollectionReference(js.parent) }

    fun collection(collectionPath: String) = rethrow { CollectionReference(collection(js, collectionPath)) }

    suspend inline fun <reified T> set(data: JsAny) =
        rethrow { setDoc(js, data).await<JsAny>() }


//    suspend inline fun <reified T> update(data: T, encodeDefaults: Boolean) =
//        rethrow { jsUpdate(js, encode(data, encodeDefaults)!!).await() }
//
//    suspend fun delete() = rethrow { deleteDoc(js).await() }

    suspend fun get() = rethrow { DocumentSnapshot(getDoc(js).await()) }

    //    override fun equals(other: Any?): Boolean =
//        this === other || other is DocumentReference && refEqual(nativeValue, other.nativeValue)
    override fun hashCode(): Int = nativeValue.hashCode()
    override fun toString(): String = "DocumentReference(path=$path)"
}

open class Query(open val js: JsQuery) {
    suspend fun get() = rethrow { QuerySnapshot(getDocs(js).await()) }

    internal fun _where(field: String, equalTo: JsAny?) = rethrow { Query(query(js, jsWhere(field, "==", equalTo))) }
    internal fun _where(path: FieldPath, equalTo: JsAny?) =
        rethrow { Query(query(js, jsWhere(path.js, "==", equalTo))) }

    internal fun _where(field: String, equalTo: DocumentReference) =
        rethrow { Query(query(js, jsWhere(field, "==", equalTo.js))) }

    internal fun _where(path: FieldPath, equalTo: DocumentReference) =
        rethrow { Query(query(js, jsWhere(path.js, "==", equalTo.js))) }
}

enum class Direction(internal val jsString: String) {
    ASCENDING("asc"),
    DESCENDING("desc");
}

private fun _orderBy(field: String, direction: JsAny) = orderBy(field, direction)

fun Query.orderBy(field: String, direction: Direction = Direction.ASCENDING) = rethrow {
    Query(query(js, _orderBy(field, direction.jsString.toJsString())))
}

fun Query.where(field: String, equalTo: JsAny?) = _where(field, equalTo)
fun Query.where(path: FieldPath, equalTo: JsAny?) = _where(path, equalTo)

class QuerySnapshot(val js: JsQuerySnapshot) {

    /* Lack of JsArray.map{  }
    * I don't like this workaround!!! */
    val documents
        get() = js.docs.let {
            val length = js.docs.length
            val items = mutableListOf<DocumentSnapshot>()

            for (i in 0..<length) {
                js.docs[i]?.let { DocumentSnapshot(it) }?.let { items.add(it) }
            }

            items
        }
}

class DocumentSnapshot(val js: JsDocumentSnapshot) {

    val id get() = rethrow { js.id }
    val reference get() = rethrow { DocumentReference(js.ref) }

    fun data(): JsAny? =
        rethrow { js.data() }

    inline fun <reified T> get(field: String) =
        rethrow { js.get(field) }

    //    fun contains(field: String) = rethrow { js.get(field) != Js }
    val exists get() = rethrow { js.exists() }
}

class CollectionReference(override val js: JsCollectionReference) : Query(js) {

    val path: String
        get() = rethrow { js.path }

    val document get() = rethrow { DocumentReference(doc(js)) }

    val parent get() = rethrow { js.parent?.let { DocumentReference(it) } }

    fun document(documentPath: String) = rethrow { DocumentReference(doc(js, documentPath)) }

    suspend inline fun add(data: JsAny) =
        rethrow {
//            printJsObject("Data passed to Add is", data)
            val ref = DocumentReference(
                catchJsExceptions {
                    addDoc(js, data)
                }.await()
            ).js
            ref
        }

}

class FieldPath private constructor(val js: JsFieldPath) {
    val documentId: FieldPath get() = FieldPath(JsFieldPath.documentId)

    override fun equals(other: Any?): Boolean = other is FieldPath && js.isEqual(other.js)
    override fun hashCode(): Int = js.hashCode()
    override fun toString(): String = js.toString()
}

class FirebaseFirestoreException(cause: Throwable, val code: FirestoreExceptionCode) :
    FirebaseException(code.toString(), cause)

enum class FirestoreExceptionCode {
    OK,
    CANCELLED,
    UNKNOWN,
    INVALID_ARGUMENT,
    DEADLINE_EXCEEDED,
    NOT_FOUND,
    ALREADY_EXISTS,
    PERMISSION_DENIED,
    RESOURCE_EXHAUSTED,
    FAILED_PRECONDITION,
    ABORTED,
    OUT_OF_RANGE,
    UNIMPLEMENTED,
    INTERNAL,
    UNAVAILABLE,
    DATA_LOSS,
    UNAUTHENTICATED
}

inline fun <T, R> T.rethrow(function: T.() -> R): R = dev.gitlive.firebase.firestore.rethrow { function() }

// Catches Kotlin exceptions TODO: Add support for catching Javascript exceptions
inline fun <R> rethrow(function: () -> R): R {
    try {
        return function()
    } catch (e: Exception) {
        throw e
        // Note that this does not capture Javascript exceptions
//    } catch(e: dynamic) {
//        throw errorToException(e)
    }
}

/* Lack of dynamic type in Wasm */
//fun errorToException(e: dynamic) = (e?.code ?: e?.message ?: "")
//    .toString()
//    .lowercase()
//    .let {
//        when {
//            "cancelled" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.CANCELLED)
//            "invalid-argument" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.INVALID_ARGUMENT)
//            "deadline-exceeded" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.DEADLINE_EXCEEDED)
//            "not-found" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.NOT_FOUND)
//            "already-exists" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.ALREADY_EXISTS)
//            "permission-denied" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.PERMISSION_DENIED)
//            "resource-exhausted" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.RESOURCE_EXHAUSTED)
//            "failed-precondition" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.FAILED_PRECONDITION)
//            "aborted" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.ABORTED)
//            "out-of-range" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.OUT_OF_RANGE)
//            "unimplemented" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.UNIMPLEMENTED)
//            "internal" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.INTERNAL)
//            "unavailable" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.UNAVAILABLE)
//            "data-loss" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.DATA_LOSS)
//            "unauthenticated" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.UNAUTHENTICATED)
//            "unknown" in it -> FirebaseFirestoreException(e, FirestoreExceptionCode.UNKNOWN)
//            else -> {
//                println("Unknown error code in ${JSON.stringify(e)}")
//                FirebaseFirestoreException(e, FirestoreExceptionCode.UNKNOWN)
//            }
//        }
//    }
