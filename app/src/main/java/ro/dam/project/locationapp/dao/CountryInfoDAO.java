package ro.dam.project.locationapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ro.dam.project.locationapp.model.CountryInfo;
import ro.dam.project.locationapp.utility.DBHelper;

/**
 * To be used for obtaining country information from DB
 */
public class CountryInfoDAO {
    public static final String TAG = "COUNTRY_INFO_DAO";

    private SQLiteDatabase mDB;
    private DBHelper mDBHelper;
    private Context mContext;
    private String[] mColumns = {DBHelper.COUNTRY_NAME_PK,
            DBHelper.COUNTRY_FLAG, DBHelper.CAPITAL, DBHelper.OFFICIAL_LANGUAGES
            , DBHelper.POPULATION, DBHelper.DESCRIPTION};

    public CountryInfoDAO(Context mContext) {
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
        // deschide bd
        try {
            open();
            Log.i("DB OPENED", mDBHelper.getDatabaseName());
        } catch (SQLException e) {
            Log.e(TAG, "SQLException la deschiderea bazei de date! " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    //insert COUNTRY_INFO
    public CountryInfo insertCountryInfo(String countryName, String countryFlag, String capital, String offLanguages, long population, String description) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COUNTRY_NAME_PK, countryName);
        values.put(DBHelper.COUNTRY_FLAG, countryFlag);
        values.put(DBHelper.CAPITAL, capital);
        values.put(DBHelper.OFFICIAL_LANGUAGES, offLanguages);
        values.put(DBHelper.POPULATION, population);
        values.put(DBHelper.DESCRIPTION, description);


        long idInserted = mDB
                .insert(mDBHelper.COUNTRY_INFO_TABLE, null, values);
        Cursor cursor = mDB.query(mDBHelper.COUNTRY_INFO_TABLE, mColumns,
                DBHelper.COUNTRY_NAME_PK + " = '" + countryName + "'", null, null,
                null, null);
        cursor.moveToFirst();
        CountryInfo newCountryInfo = cursorCountryInfo(cursor);
        Log.i("!!!!CountryInfo Inserat", newCountryInfo.toString());
        cursor.close();

        return newCountryInfo;
    }

    //select countryInfo by id- method used in ip_location_DAO's cursor
    public CountryInfo selectCountryInfoById(String id) {
        CountryInfo countryInfo = null;
        Cursor cursor = mDB.query(DBHelper.COUNTRY_INFO_TABLE, mColumns,
                DBHelper.COUNTRY_NAME_PK + " = '" + id + "'",
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            countryInfo = cursorCountryInfo(cursor);
        }
        return countryInfo;
    }

    //verify if country exists in db
    public boolean verifyCountryInfo(String id) {
        boolean countryExists = false;
        CountryInfo countryInfo = null;
        Cursor cursor = mDB.query(DBHelper.COUNTRY_INFO_TABLE, mColumns,
                DBHelper.COUNTRY_NAME_PK + " = '" + id + "'",
                null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            countryExists = true;
        }
        return countryExists;
    }

    private CountryInfo cursorCountryInfo(Cursor cursor) {
        CountryInfo ci = new CountryInfo();
        ci.setCountryName(cursor.getString(0));
        ci.setCountryFlag(cursor.getString(1));
        ci.setCapital(cursor.getString(2));
        ci.setOffLanguages(cursor.getString(3));
        ci.setPopulation(cursor.getLong(4));
        ci.setDescription(cursor.getString(5));

        return ci;
    }
}
