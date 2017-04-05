package com.anandroid.qrreader.utills;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 */

public final class Constants {

    // Sharedpref file name
    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String IS_DATA_FILLED = "IS_DATA_FILLED";
    public static final int PERMISSION_REQUEST_CODE = 1;
    public static final String FROM_TAG = "FROM_TAG";
    public static final String FROM_TAG_NEW_ACT_FRG = "FROM_TAG_NEW_ACT_FRG";
    public static final String FROM_TAG_BASE_ROOT = "FROM_TAG_BASE_ROOT";
    public static final String FROM_PREVIOUS_TAG = "FROM_PREVIOUS_TAG";
    public static final String FROM_WHICH_FRAG_TAG = "FROM_WHICH_FRAG_TAG";
    public static final String BACK_PRESSED = "BACK_PRESSED";
    public static final String PUT_BUNDLE_HASH_MAP = "PUT_BUNDLE_HASH_MAP";
    public static final String PUT_BUNDLE = "PUT_BUNDLE";
    public static final String PUT_MODEL_DATA = "PUT_MODEL_DATA";
    public static final String PUT_INTENT_ARR_LIST_STR = "PUT_INTENT_ARR_LIST_STR";
    public static final String JSON_IS_SINGLES_DATA = "JSON_IS_SINGLES_DATA";
    public static final String SELECTED_IDS = "SELECTED_IDS";
    public static final String SELECTED_MODEL_LIST = "SELECTED_MODEL_LIST";
    public static final String SELECTED_FILTER_MODEL_LIST = "SELECTED_FILTER_MODEL_LIST";
    public static final String SPORT_PROFILE_ID = "SPORT_PROFILE_ID";
    public static final String SPORT_PROFILE_ID_POS = "SPORT_PROFILE_ID_POS";
    public static final String FROM_SINGLE_SPORT_ID = "FROM_SINGLE_SPORT_ID";
    public static final String SPORT_ID_NAME = "SPORT_ID_NAME";
    public static final String FROM_SPORT_PROFILE_ID = "FROM_SPORT_PROFILE_ID";
    public static final String GAME_TYPE = "GAME_TYPE";
    public static final String TEAM_ID = "teamId";
    public static final String POSITION = "position";
    public static final String CHALLENGE_CONFIRMED = "CHALLENGE_CONFIRMED";

    public static final int PAGINATION_COUNT = 25;
    public static final String SERVICE_TYPE = "SERVICE_TYPE";

    private static String blockCharacterSet = "~#^|$%&*!";

    public static void showToast(Context context, String messageText) {

        Toast.makeText(context, messageText, Toast.LENGTH_LONG).show();

    }


    public static boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

   /* public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }*/

    public static void hideKeyboard(Activity activity, View viewToHide) {
        try {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewToHide.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("wkcleg_Constants", "hideKeyboard Exception", e);
        }
    }

    public static String upperCaseFirst(String value) {
        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }

    public static String upperCaseAllFirst(String value) {
        char[] array = value.toCharArray();
        // Uppercase first letter.
        array[0] = Character.toUpperCase(array[0]);
        // Uppercase all letters that follow a whitespace character.
        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }
        // Result.
        return new String(array);
    }

    public static InputFilter filterSpecialChar = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    public static InputFilter filterLetter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.isLetter(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

    public final String APP_TAG = "OneLeagueAppImg";

    // Returns the Uri for a photo stored on disk given the fileName

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isGPSEnabled(Context mContext) {
        LocationManager lm = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;

        }
    }

    public static void requestPermission(Activity context) {

   /* if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

        Toast.makeText(getApplicationContext(),"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

    } else {*/

        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    }

    public static String customDateFormat(String date) {


        String[] splitDateString = date.split(" ");

        String subStringDate = splitDateString[0];

        String[] splitForMonth = subStringDate.split("-");

        String stringDateFormat = splitForMonth[2] + " " + Constants.nameOfTheMonth(splitForMonth[1]) + " " + splitForMonth[0];


        return stringDateFormat;
    }

    public static String nameOfTheMonth(String monthNumber) {

        switch (monthNumber) {
            case "01":
                monthNumber = "Jan";
                break;
            case "02":
                monthNumber = "Feb";
                break;
            case "03":
                monthNumber = "Mar";
                break;
            case "04":
                monthNumber = "Apr";
                break;
            case "05":
                monthNumber = "May";
                break;
            case "06":
                monthNumber = "Jun";
                break;
            case "07":
                monthNumber = "Jul";
                break;
            case "08":
                monthNumber = "Aug";
                break;
            case "09":
                monthNumber = "Sep";
                break;
            case "10":
                monthNumber = "Oct";
                break;
            case "11":
                monthNumber = "Nov";
                break;
            case "12":
                monthNumber = "Dec";
                break;
        }

        return monthNumber;
    }

    public static String getDateUTC_IST(String OurDate) {

        // 2017-03-15T05:57:39.684Z
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("IST"));
            Date value = formatter.parse(OurDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            OurDate = dateFormatter.format(value);

            //Log.d("OurDate", OurDate);
        } catch (Exception e) {
            OurDate = "00-00-0000 00:00";
        }
        return OurDate;
    }

    public static String getCurrentDateAndTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public final static String twoDatesBetweenTime(String oldtime) {
        // TODO Auto-generated method stub
        int day = 0;
        int hh = 0;
        int mm = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date oldDate = dateFormat.parse(oldtime);
            Date cDate = new Date();
            Log.i("Constants", "Date differ :" + oldDate + " =============== " + cDate);
            Long timeDiff = cDate.getTime() - oldDate.getTime();
            // Long timeDiff = cDate.getTime() - cDate.getTime();

            day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
            hh = (int) (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day));
            mm = (int) (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
            Log.i("MINUSSSSSSSSSSS", " =============== " + mm);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    /*if(day==0)
    {
        return hh + " hours " + mm + " mins ago";
    }
    else if(hh==0)
    {
        return mm + " mins ago";
    }
    else
    {
        return day + " days " + hh + " hours " + mm + " mins ago";
    }*/


        if (day == 0) {
            if (hh == 0) {
                if (mm >= 0) {
                    return "Just now";
                } else if (mm == 1) {
                    return String.valueOf(mm) + " min ago";
                } else {
                    return String.valueOf(mm) + " mins ago";
                }
            } else if (hh == 1) {
                return String.valueOf(hh) + " hour ago";
            } else {
                return String.valueOf(hh) + " hours ago";
            }

        } else {
            if (day == 1) {
                return String.valueOf(day) + " day ago";
            }
            if (day >= 2 && day <= 29) {
                return String.valueOf(day) + " days ago";
            }
            if (day > 29 && day <= 58) {
                return "1 month ago";
            }
            if (day > 58 && day <= 87) {
                return "2 months ago";
            }
            if (day > 87 && day <= 116) {
                return "3 months ago";
            }
            if (day > 116 && day <= 145) {
                return "4 months ago";
            }
            if (day > 145 && day <= 174) {
                return "5 months ago";
            }
            if (day > 174 && day <= 203) {
                return "6 months ago";
            }
            if (day > 203 && day <= 232) {
                return "7 months ago";
            }
            if (day > 232 && day <= 261) {
                return "8 months ago";
            }
            if (day > 261 && day <= 290) {
                return "9 months ago";
            }
            if (day > 290 && day <= 319) {
                return "10 months ago";
            }
            if (day > 319 && day <= 348) {
                return "11  months ago";
            }
            if (day > 348 && day <= 360) {
                return "12 months ago";
            }

            if (day > 360 && day <= 720) {
                return "1 year ago";
            }

            if (day > 720) {
                return "2 years ago";
            }

            return "";
        }

    }

    /**
     * Get the animator to unreveal the circle
     *
     * @param cx center x of the circle (or where the view was touched)
     * @param cy center y of the circle (or where the view was touched)
     * @return Animator object that will be used for the animation
     */

    /**
     * To be really accurate we have to start the circle on the furthest corner of the view
     *
     * @param v  the view to unreveal
     * @param cx center x of the circle
     * @param cy center y of the circle
     * @return the maximum radius
     */
    public static int getEnclosingCircleRadius(View v, int cx, int cy) {
        int realCenterX = cx + v.getLeft();
        int realCenterY = cy + v.getTop();
        int distanceTopLeft = (int) Math.hypot(realCenterX - v.getLeft(), realCenterY - v.getTop());
        int distanceTopRight = (int) Math.hypot(v.getRight() - realCenterX, realCenterY - v.getTop());
        int distanceBottomLeft = (int) Math.hypot(realCenterX - v.getLeft(), v.getBottom() - realCenterY);
        int distanceBottomRight = (int) Math.hypot(v.getRight() - realCenterX, v.getBottom() - realCenterY);

        Integer[] distances = new Integer[]{distanceTopLeft, distanceTopRight, distanceBottomLeft,
                distanceBottomRight};

        return Collections.max(Arrays.asList(distances));
    }

}