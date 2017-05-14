/*
package com.anandroid.qrreader.utills;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.app.api.ServerJsonResponseKey;
import com.app.api.ServerParams;
import com.app.views.activity.HomeAct;
import com.wekancode.vms.App;

import java.util.HashMap;

*/
/**
 * Created by Anand  on 1/30/2017.
 *//*


public class UserSessionManager {

    private static UserSessionManager mInstance;

    public static String DATA = "data";
    // Shared Preferences reference
    //private static
    SharedPreferences pref = getSharedPreferences(qr_reader"", PRIVATE_MODE);

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    //Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;


    // Constructor
    private UserSessionManager() {
        //this._context = context;
        editor = pref.edit();
    }

    */
/**
     * Singleton construct design pattern.
     *
     * @return single instance of SessionManagerSingleton
     *//*

    public static synchronized UserSessionManager getInstance() {
        if (mInstance == null) {
            mInstance = new UserSessionManager();
        }
        return mInstance;
    }

    */
/**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     *//*


  */
/*  public void saveTennisTimingIds(String morningId
            , String afternoonId, String eveningId, String nightId) {
        editor.putString(DataBaseKey.MORNING_TIME, morningId);
        editor.putString(DataBaseKey.AFTERNOON_TIME, afternoonId);
        editor.putString(DataBaseKey.EVENING_TIME, eveningId);
        editor.putString(DataBaseKey.NIGHT_TIME, nightId);
        editor.commit();
    }*//*


    // VMS

    // Check for login
    public void userLoggedIn() {
        editor.putBoolean(Constants.IS_USER_LOGIN, true);
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(Constants.IS_USER_LOGIN, false);
    }

    //Create login session
    public void createSession(String token) {
        // Storing login value as TRUE
        editor.putString(Constants.TOKEN, Constants.BEARER + token);
        // commit changes
        editor.commit();
    }

    public String getSession() {
        return pref.getString(ServerJsonResponseKey.TOKEN, "");
    }


    public void cleardatas() {
// Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
    }


    //User Details session
    public void userDetails(String firstName, String lastName, String email) {
        // Storing login value as TRUE
        editor.putString(ServerParams.FIRST_NAME, firstName);
        editor.putString(ServerParams.LAST_NAME, lastName);
        editor.putString(ServerParams.EMAIL, email);
        editor.commit();
    }


    //User TimeZone
    public void timeZone(String time_zone) {
        // Storing login value as TRUE
        editor.putString(ServerParams.TIME_ZONE, time_zone);
        editor.commit();
    }

    //User Details session
    public String getTimeZone() {
        return pref.getString(ServerParams.TIME_ZONE, null);
    }


    //User Details session
    public HashMap<String, String> getUserDatas()

    {
        // Storing login value as TRUE
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(ServerParams.FIRST_NAME, pref.getString(ServerParams.FIRST_NAME, null));
        user.put(ServerParams.LAST_NAME, pref.getString(ServerParams.LAST_NAME, null));
        user.put(ServerParams.EMAIL, pref.getString(ServerParams.EMAIL, null));
        return user;
    }


}*/
