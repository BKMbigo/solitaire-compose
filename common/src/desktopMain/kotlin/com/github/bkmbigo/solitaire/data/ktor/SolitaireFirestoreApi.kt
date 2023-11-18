package com.github.bkmbigo.solitaire.data.ktor

import com.github.bkmbigo.solitaire.data.SolitaireScore
import com.github.bkmbigo.solitaire.data.ktor.dto.*
import com.github.bkmbigo.solitaire.data.ktor.mappers.toFirebaseFieldDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class SolitaireFirestoreApi(
    private val projectId: String = "solitaire-compose",
    private val client: HttpClient = ktorClient
) {


    /*
    POST request to https://firestore.googleapis.com/v1/projects/${projectId}/databases/(default)/documents/experimental/
    {
        "fields": {
            "score": {
                "integerValue": 1223
            },
            "leaderboard": {
                "nullValue": "NULL_VALUE" (or a stringValue)
            },
            "platform": {
                "stringValue": "Wasm"
            }
        }
    }
    */
    suspend fun addRecord(collectionPath: String, score: SolitaireScore) =
        client.post("$BASE_URL/$projectId/$DATABASE_URL$collectionPath") {
            contentType(ContentType.Application.Json)
            setBody(FirestoreFieldsDto(score.toFirebaseFieldDto()))
        }


    /*
    * POST: https://firestore.googleapis.com/v1/projects/${projectId}/databases/(default)/documents:runQuery

    REQUEST:
        params

        body:
            {
              "structuredQuery": {
                "orderBy": [
                  {
                    "field": {
                      "fieldPath": "score"
                    },
                    "direction": "DESCENDING"
                  }
                ],
                "where": {
                  "fieldFilter": {
                    "field": {
                      "fieldPath": "leaderboard"
                    },
                    "op": "EQUAL",
                    "value": {
                      "stringValue": "Trial"
                    }
                  }
                },
                "from": [
                  {
                    "collectionId": "klondikeScore"
                  }
                ]
              }
            }

    RESPONSE:
        [
            {
                "document": {
                    "name": "projects/${projectId}/databases/(default)/documents/klondikeScore/SfphM95HtvAg5oKpyR0Z",
                    "fields": {
                        "score": {
                            "integerValue": "920"
                        },
                        "leaderboard": {
                            "stringValue": "Trial"
                        },
                        "playerName": {
                            "stringValue": "Trial Player"
                        },
                        "platform": {
                            "stringValue": "JS"
                        }
                    },
                    "createTime": "2023-11-17T11:01:41.848370Z",
                    "updateTime": "2023-11-17T11:01:41.848370Z"
                },
                "readTime": "2023-11-18T17:50:22.886970Z"
            }, ...
        ]
    * */
    suspend fun getRecordByEqualFilter(collectionName: String, fieldName: String, value: String) =
        client.post("$BASE_URL/$projectId/$DATABASE_URL:runQuery") {
            setBody(
                FirestoreStructuredQueryDto(
                    where = FirestoreWhereDto(
                        fieldFilter = FirestoreFieldFilterDto(
                            field = mapOf(
                                "fieldPath" to fieldName
                            ),
                            op = "EQUAL",
                            value = mapOf(
                                "stringValue" to value
                            )
                        )
                    ),
                    orderBy = listOf(
                        FirestoreOrderByDto(
                            field = mapOf(
                                "fieldPath" to "score"
                            ),
                            direction = FirestoreFieldDirection.DESCENDING
                        )
                    ),
                    from = listOf(
                        mapOf(
                            "collectionId" to collectionName
                        )
                    )
                )
            )
        }

    /*
    * GET request to https://firestore.googleapis.com/v1/projects/solitaire-compose/databases/(default)/documents/experimental/

    REQUEST:
        params:
            pageSize = 20
            orderBy = "score desc"

    RESPONSE:
        {
            "documents": [
                {
                    "name": "projects/${projectId}/databases/(default)/documents/klondikeScore/SfphM95HtvAg5oKpyR0Z",
                    "fields": {
                        "playerName": {
                            "stringValue": "Trial Player"
                        },
                        "platform": {
                            "stringValue": "JS"
                        },
                        "score": {
                            "integerValue": "920"
                        },
                        "leaderboard": {
                            "stringValue": "Trial"
                        }
                    },
                    "createTime": "2023-11-17T11:01:41.848370Z",
                    "updateTime": "2023-11-17T11:01:41.848370Z"
                }, ...
            ],
            "nextPageToken": "AFTOeJx3aoLJxdp5140Ip_AEhlkvEyzQdBRskahT2mFWc9EaIlgoYVU366g2UQnbqFRnjFHh4l0skB3DfefMx5QzM0SN-YQMijmGd2BBgMloxe2ZXrqrr-bY7jN77qvYdcGO3cjV5FivJ9N_nyzv7g"
        }

    * */
    suspend fun getAllRecords(collectionName: String) =
        client.get("$BASE_URL/$projectId/$DATABASE_URL/$collectionName") {
            parameters {
                parameter("pageSize", 20)
                parameter("orderBy", "score desc")
            }
        }

    companion object {
        private const val BASE_URL = "https://firestore.googleapis.com/v1/projects"
        private const val DATABASE_URL = "databases/(default)/documents/"
    }

}
