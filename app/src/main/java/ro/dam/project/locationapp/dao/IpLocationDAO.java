package ro.dam.project.locationapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ro.dam.project.locationapp.model.CountryInfo;
import ro.dam.project.locationapp.model.LocationSearch;
import ro.dam.project.locationapp.model.User;
import ro.dam.project.locationapp.utility.DBHelper;

/**
 * Data access object for retrieving location information/search history
 */
public class IpLocationDAO {
    public static final String TAG = "LocationSearch_DAO";

    private SQLiteDatabase mDB;
    private DBHelper mDBHelper;
    private Context mContext;
    private String[] mColumns = {DBHelper.IP_PK,
            DBHelper.COUNTRY_NAME_FK, DBHelper.USER_ID_PK_FK};

    public IpLocationDAO(Context mContext) {
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

    //insert LocationSearch data
    public LocationSearch insertIpLocationData(String ip, String countryName, long userId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.IP_PK, ip);
        values.put(DBHelper.COUNTRY_NAME_FK, countryName);
        values.put(DBHelper.USER_ID_PK_FK, userId);

        //verify if fk exists in parent table
        CountryInfoDAO dao = new CountryInfoDAO(mContext);
        boolean countryExists = dao.verifyCountryInfo(countryName);
        CountryInfo c;
        if (!countryExists) {
            c = dao.insertCountryInfo(countryName, "", "", "", -1, "");
            Log.i("Tara noua inserata ", c.toString());
        }

        long idInserted = mDB
                .insert(DBHelper.IPLOC_TABLE, null, values);
        Cursor cursor = mDB.query(DBHelper.IPLOC_TABLE, mColumns,
                DBHelper.COUNTRY_NAME_FK + " = '" + countryName + "'", null, null,
                null, null);
        cursor.moveToFirst();
        LocationSearch newIpLocation = cursorIpLocation(cursor);
        Log.i("!!!!Locatie Inserata", newIpLocation.toString());
        cursor.close();

        return newIpLocation;
    }

    public boolean verifyIpInsertedBySameUser(String ip, long userId) {
        boolean ipInserdedBySameUser = false;
        Cursor cursor = mDB.query(DBHelper.IPLOC_TABLE, mColumns,
                DBHelper.IP_PK + " = ? AND " + DBHelper.USER_ID_PK_FK + " =?", new String[]{String.valueOf(ip), String.valueOf(userId)}, null,
                null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            ipInserdedBySameUser = true;
        cursor.close();
        return ipInserdedBySameUser;
    }

    // select all IpAddresses ByUserId

    public List<LocationSearch> selectAllIpAddressesByUserId(long userId) {
        List<LocationSearch> ipList = new ArrayList<>();
        Cursor cursor = mDB.query(DBHelper.IPLOC_TABLE,
                mColumns, DBHelper.USER_ID_PK_FK + " = " + userId, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocationSearch ipLocElem = cursorIpLocation(cursor);
            ipList.add(ipLocElem);
            cursor.moveToNext();
        }
        // inchidere cursor
        cursor.close();
        return ipList;
    }

    private LocationSearch cursorIpLocation(Cursor cursor) {
        LocationSearch ipLoc = new LocationSearch();
        ipLoc.setIp(cursor.getString(0));

        //     CountryInfo by id
        String idCountryInfo = cursor.getString(1);
        CountryInfoDAO dao = new CountryInfoDAO(mContext);
        CountryInfo countryI = dao.selectCountryInfoById(idCountryInfo);
        if (countryI != null)
            ipLoc.setCountryInfo(countryI);
        else //country doesn't exist in db
        {

            ipLoc.setCountryInfo(new CountryInfo(idCountryInfo));
        }
        // user by id
        long idUser = cursor.getLong(2);
        UserDAO dao1 = new UserDAO(mContext);
        User user = dao1.selectUserById(idUser);
        if (user != null)
            ipLoc.setUser(user);
        else
            ipLoc.setUser(null);
        return ipLoc;
    }


    public void open() throws SQLException {
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }
}
