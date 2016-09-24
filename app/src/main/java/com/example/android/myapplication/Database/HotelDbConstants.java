package com.example.android.myapplication.Database;

public interface HotelDbConstants {

    // Databse Values //
    public String DATABASE_NAME = "VoiceMailDb";
    public String HOTEL_TABLE = "HotelTable";

    public int DATABASE_VERSION = 1;

    // Main Table Fields //
    public String ID = "id";
    public static final String NAME = "Name";
    public static final String PRINT_DATE = "PrintDate";
    public static final String COUNT_NUMBER = "CountNumber";

    public String CREATE_TABLE_USERS = "create table " + HOTEL_TABLE + "("
            + ID + " INTEGER primary key autoincrement,"
            + NAME + " TEXT not null,"
            + PRINT_DATE + " DATETIME not null,"
            + COUNT_NUMBER + " TEXT not null);";

    // Select All The Data From The Local Database
    public String SELECT_ALL_COUNT = "SELECT  * FROM " + HOTEL_TABLE;

    // Select All The Data From The Local Database
    public String SELECT_ALL_COUNT_DATE_WISE = "SELECT  * FROM " + HOTEL_TABLE + " WHERE " + PRINT_DATE + " BETWEEN "+"'$from' AND " +"'$to'";

    public String SELECT_MAIL_COUNT = "SELECT count(*) as Count FROM " + HOTEL_TABLE;

}
