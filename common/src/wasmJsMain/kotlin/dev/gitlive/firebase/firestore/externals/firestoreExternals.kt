@file:JsModule("firebase/firestore")

package dev.gitlive.firebase.firestore.externals

import dev.gitlive.firebase.externals.FirebaseApp
import kotlin.js.Promise

external fun addDoc(reference: CollectionReference, data: JsAny): Promise<DocumentReference>

external fun collection(firestore: Firestore, collectionPath: String): CollectionReference

external fun collection(reference: DocumentReference, collectionPath: String): CollectionReference

external fun doc(firestore: Firestore, documentPath: String): DocumentReference

external fun doc(firestore: CollectionReference, documentPath: String? = definedExternally): DocumentReference

external fun getDoc(
    reference: DocumentReference,
    options: JsAny? = definedExternally
): Promise<DocumentSnapshot>

external fun getDocs(query: Query): Promise<QuerySnapshot>

external fun getFirestore(app: FirebaseApp? = definedExternally): Firestore

external fun initializeFirestore(app: FirebaseApp): Firestore

external fun query(query: Query, vararg queryConstraints: QueryConstraint): Query

external fun setDoc(
    documentReference: DocumentReference,
    data: JsAny
): Promise<JsAny>  // Should be Promise<Unit> but I don't really care about the return type

external fun updateDoc(reference: DocumentReference, data: JsAny): Promise<JsAny>

external fun updateDoc(
    reference: DocumentReference,
    field: String,
    value: String?
): Promise<JsAny>

external fun where(field: String, opStr: String, value: JsAny?): QueryConstraint

external fun where(field: FieldPath, opStr: String, value: JsAny?): QueryConstraint


external interface Firestore {
    val app: FirebaseApp
}

external class FieldPath(vararg fieldNames: String) {
    companion object {
        val documentId: FieldPath
    }

    fun isEqual(other: FieldPath): Boolean
}

external interface CollectionReference : Query, JsAny {
    val id: String
    val path: String
    val parent: DocumentReference?
}

external class DocumentReference : JsAny {
    val id: String
    val path: String
    val parent: CollectionReference
}

external interface DocumentSnapshot : JsAny {
    val id: String
    val ref: DocumentReference

    //    val metadata: SnapshotMetadata
    fun data(options: JsAny? = definedExternally): JsAny?
    fun exists(): Boolean
    fun get(fieldPath: String, options: JsAny? = definedExternally): JsAny?
    fun get(fieldPath: FieldPath, options: JsAny? = definedExternally): JsAny?
}

external interface QuerySnapshot : JsAny {
    val docs: JsArray<DocumentSnapshot>
    val empty: Boolean
//    val metadata: SnapshotMetadata
//    fun docChanges(): Array<DocumentChange>
}

external interface Query

external interface QueryConstraint
