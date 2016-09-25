package com.example.android.myapplication.Database;

public interface HotelDbConstants {

    // Databse Values //
    public String DATABASE_NAME = "SriSaravana";

    public String TABLE_HOTEL = "HotelTable";
    public String TABLE_USERS = "Users";

    public int DATABASE_VERSION = 1;

    // Main Table Fields //
    public String ID = "id";
    public static final String NAME = "Name";

    public static final String MOBILE = "Mobile";
    public static final String PASSWORD = "Passwd";

    public static final String PRINT_DATE = "PrintDate";
    public static final String COUNT_NUMBER = "CountNumber";

    public String CREATE_HOTEL_TABLE = "create table " + TABLE_HOTEL + "("
            + ID + " INTEGER primary key autoincrement,"
            + NAME + " TEXT not null,"
            + PRINT_DATE + " DATETIME not null,"
            + COUNT_NUMBER + " TEXT not null);";

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
    public String SELECT_ALL_COUNT_DATE_WISE = "SELECT  * FROM " + TABLE_HOTEL + " WHERE " + PRINT_DATE + " BETWEEN " + "'$from' AND " + "'$to'";

    public String SELECT_MAIL_COUNT = "SELECT count(*) as Count FROM " + TABLE_HOTEL;

}
