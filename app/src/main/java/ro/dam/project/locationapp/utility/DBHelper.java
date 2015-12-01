package ro.dam.project.locationapp.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "locationApp.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    // -----------------------------------------------------USERS columns
    public static final String USERS_TABLE = "USERS";
    public static final String USER_ID = "user_id";
    public static final String USER_FIRSTNAME = "firstName";
    public static final String USER_LASTNAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String COUNTRY = "country";

    // -----------------------------------------------------IP_LOCATIONS columns
    public static final String IPLOC_TABLE = "IP_LOCATIONS";
    public static final String IP_PK = "_id_ip";
    public static final String COUNTRY_NAME_FK = "countryName";
    public static final String USER_ID_PK_FK = "userId";

    // -----------------------------------------------------COUNTRY_INFO columns
    public static final String COUNTRY_INFO_TABLE = "COUNTRY_INFO";
    public static final String COUNTRY_NAME_PK = "_id";
    public static final String COUNTRY_FLAG = "countryFlag";
    public static final String CAPITAL = "capital";
    public static final String OFFICIAL_LANGUAGES = "official_languages";
    public static final String POPULATION = "population";
    public static final String DESCRIPTION = "description";

    //-------------------------------------------------------------------------------------------------
    // CREATE TABLE IP_Location
    private static final String SQL_CREATE_TABLE_IP_LOCATIONS = "CREATE TABLE " + IPLOC_TABLE + "("
            + IP_PK + " TEXT NOT NULL, "
            + COUNTRY_NAME_FK + " TEXT NOT NULL, "
            + USER_ID_PK_FK + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + USER_ID_PK_FK + "," + IP_PK + "), "
            + " FOREIGN KEY (" + COUNTRY_NAME_FK + ") REFERENCES " + COUNTRY_INFO_TABLE + " (" + COUNTRY_NAME_PK + ") ON DELETE CASCADE, "
            + " FOREIGN KEY (" + USER_ID_PK_FK + ") REFERENCES " + USERS_TABLE + " (" + USER_ID + ") ON DELETE CASCADE "
            + ");";

    // CREATE TABLE USERS
    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + USERS_TABLE + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_FIRSTNAME + " TEXT, "
            + USER_LASTNAME + " TEXT , "
            + EMAIL + " TEXT NOT NULL, "
            + PASSWORD + " TEXT , "
            + COUNTRY + " TEXT "
            + ");";

    // CREATE TABLE COUNTRY_INFO
    private static final String SQL_CREATE_TABLE_COUNTRY_INFO = "CREATE TABLE " + COUNTRY_INFO_TABLE + "("
            + COUNTRY_NAME_PK + " TEXT PRIMARY KEY, "
            + COUNTRY_FLAG + " TEXT, "
            + CAPITAL + " TEXT, "
            + OFFICIAL_LANGUAGES + " TEXT , "
            + POPULATION + " INTEGER, "
            + DESCRIPTION + " TEXT "
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_COUNTRY_INFO);
        db.execSQL(SQL_CREATE_TABLE_IP_LOCATIONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COUNTRY_INFO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + IPLOC_TABLE);

        // recrearea tabelelor
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }
}
