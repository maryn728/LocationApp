package ro.dam.project.locationapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ro.dam.project.locationapp.model.User;
import ro.dam.project.locationapp.utility.DBHelper;

/**
 * Data access object for user related information
 */
public class UserDAO {
    public static final String TAG = "USER_DAO";

    private SQLiteDatabase mDB;
    private DBHelper mDBHelper;
    private Context mContext;
    private String[] mColumns = {DBHelper.USER_ID,
            DBHelper.USER_FIRSTNAME, DBHelper.USER_LASTNAME, DBHelper.EMAIL
            , DBHelper.PASSWORD, DBHelper.COUNTRY};

    public UserDAO(Context mContext) {
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

    //insert USER
    public User insertUser(String firstname, String lastname, String email, String password, String country) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_FIRSTNAME, firstname);
        values.put(DBHelper.USER_LASTNAME, lastname);
        values.put(DBHelper.EMAIL, email);
        values.put(DBHelper.PASSWORD, password);
        values.put(DBHelper.COUNTRY, country);


        long idInserted = mDB
                .insert(mDBHelper.USERS_TABLE, null, values);
        Cursor cursor = mDB.query(mDBHelper.USERS_TABLE, mColumns,
                DBHelper.USER_ID + " = " + idInserted, null, null,
                null, null);
        cursor.moveToFirst();
        User newUser = cursorUser(cursor);
        //  Log.i("!!!!User Inserat",newUser.toString());
        cursor.close();

        return newUser;
    }

    public List<User> selectAllUsers() {
        List<User> userList = new ArrayList<>();

        Cursor cursor = mDB.query(mDBHelper.USERS_TABLE,
                mColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorUser(cursor);
            userList.add(user);
            cursor.moveToNext();
        }
        // inchidere cursor
        cursor.close();
        return userList;
    }

    //verify if user & pass exist in db
    public User verifyUserAndPassword(String email, String pass) {
        User user = null;
        Cursor cursor = mDB.query(mDBHelper.USERS_TABLE, mColumns,
                mDBHelper.EMAIL + " = ? AND " + mDBHelper.PASSWORD + " = ?",
                new String[]{String.valueOf(email), String.valueOf(pass)}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = cursorUser(cursor);
        }
        return user;
    }

    //verify if user exists in db
    public User verifyIfUserExistsInBd(String email) {
        User user = null;
        Cursor cursor = mDB.query(mDBHelper.USERS_TABLE, mColumns,
                mDBHelper.EMAIL + " = ?",
                new String[]{String.valueOf(email)}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = cursorUser(cursor);
        }
        return user;
    }


    //select user by id- method used in ip_location_DAO's cursor
    public User selectUserById(long id) {
        User user = null;
        Cursor cursor = mDB.query(DBHelper.USERS_TABLE, mColumns,
                DBHelper.USER_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            user = cursorUser(cursor);
        }
        return user;
    }


    private User cursorUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setFirstName(cursor.getString(1));
        user.setLastName(cursor.getString(2));
        user.setEmail(cursor.getString(3));
        user.setPassword(cursor.getString(4));
        user.setCountry(cursor.getString(5));

        return user;
    }

    public void open() throws SQLException {
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }
}
