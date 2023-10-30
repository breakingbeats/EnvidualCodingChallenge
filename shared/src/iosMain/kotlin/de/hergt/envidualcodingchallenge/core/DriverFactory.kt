package de.hergt.envidualcodingchallenge.core

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import de.hergt.envidualcodingchallenge.CommonDatabase
import kotlinx.coroutines.runBlocking

actual class DriverFactory {
    actual suspend fun createDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): SqlDriver {
        return NativeSqliteDriver(schema.synchronous(), "common.db")
    }

}