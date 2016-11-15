package com.example.android.myapplication.Database;

public interface HotelDbConstants {

    // Databse Values //
    public String DATABASE_NAME = "SriSaravana";

    public String TABLE_HOTEL = "HotelTable";
    public String TABLE_USERS = "Users";
    public String TABLE_RETURN_COUNT = "RetCount";
    public int DATABASE_VERSION = 1;

    // Main Table Fields //
    public String ID = "id";
    public static final String NAME = "Name";

    public static final String MOBILE = "Mobile";
    public static final String PASSWORD = "Passwd";

    public static final String PRINT_DATE = "PrintDate";
    public static final String COUNT_NUMBER = "CountNumber";

    public static final String FOOD_TYPE = "FoodType";

    public static final String RET_TOKEN_ID = "TokId";

    public static final String RET_COUNT = "RetCount";

    public String CREATE_HOTEL_TABLE = "create table " + TABLE_HOTEL + "("
            + ID + " INTEGER primary key autoincrement,"
            + NAME + " TEXT not null,"
            + PRINT_DATE + " DATETIME not null,"
            + COUNT_NUMBER + " DATETIME not null,"
            + FOOD_TYPE + " DATETIME not null,"
            + RET_TOKEN_ID + " DATETIME not null,"
            + RET_COUNT + " TEXT not null);";

    public String CREATE_USERS_TABLE = "create table " + TABLE_USERS + "("
            + ID + " INTEGER primary key autoincrement,"
            + NAME + " TEXT not null,"
            + PASSWORD + " TEXT not null,"
            + MOBILE + " TEXT not null,"
            + PRINT_DATE + " DATETIME not null);";

    // Select All The Data From The Local Database
    public String SELECT_ALL_COUNT = "SELECT  * FROM " + TABLE_HOTEL;

    // Select All The Data From The Local Database
    public String SELECT_SINGLE_USER_DETAILS = "SELECT  * FROM " + TABLE_USERS + " WHERE " + NAME + " = " + "'$name' AND " + PASSWORD + " = " + "'$pass'";

    // Select All The Data From The Local Database
    public String SELECT_ALL_COUNT_DATE_WISE = "SELECT " + ID + "," + PRINT_DATE + ", sum(CountNumber) as TotalCount, sum(RetCount) as RetCount FROM " + TABLE_HOTEL + " WHERE ( " + PRINT_DATE + " BETWEEN " + "'$from' AND " + "'$to') AND FoodType IN ('$type') GROUP BY " + PRINT_DATE;

    // Select Last Inser Data
    public String SELECT_LAST_INSERT_DATA = "SELECT * FROM " + TABLE_HOTEL + " ORDER BY " +ID +" DESC LIMIT 1";

    // Select All The Data From The Local Database
    public String SELECT_RET_COUNT_DATE_WISE = "SELECT " + COUNT_NUMBER + " , " + FOOD_TYPE + " FROM " + TABLE_HOTEL + " WHERE " + RET_TOKEN_ID + " = " + "'$tokid'";

}