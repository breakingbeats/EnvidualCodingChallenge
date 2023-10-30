package de.hergt.envidualcodingchallenge.core

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import de.hergt.envidualcodingchallenge.CommonDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

expect class DriverFactory {
    suspend fun createDriver(
        schema: SqlSchema<QueryResult.AsyncValue<Unit>>
    ): SqlDriver
}

suspend fun createDatabase(driverFactory: DriverFactory): CommonDatabase {
    val driver = driverFactory.createDriver(CommonDatabase.Schema)
    return CommonDatabase.invoke(driver)
}