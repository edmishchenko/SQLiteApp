package com.example.test

object Constants {

    // Data for database
    internal const val DATABASE_NAME = "student.db"
    internal const val DATABASE_VERSION = 1
    internal const val TBL_STUDENT = "tbl_student"
    internal const val ID = "id"
    internal const val NAME = "name"
    internal const val EMAIL = "email"

    //Query for database
    internal const val CREATE_TBL_STUDENT = (
                "CREATE TABLE $TBL_STUDENT " +
                "(" +
                "$ID INTEGER PRIMARY KEY," +
                "$NAME TEXT," +
                "$EMAIL TEXT" +
                ")")
    internal const val SELECT_ALL_TABLE = "SELECT * FROM $TBL_STUDENT"
    internal const val DROP_TABLE_IF_EXISTS =  "DROP TABLE IF EXISTS $TBL_STUDENT"
}