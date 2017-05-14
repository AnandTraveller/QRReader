package com.anandroid.qrreader.view.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anandroid.qrreader.R;
import com.anandroid.qrreader.utills.Constants;
import com.anandroid.qrreader.utills.FragmentKey;
import com.anandroid.qrreader.view.fragment.HomeScreen;
import com.anandroid.qrreader.view.fragment.LoginFrag;
import com.anandroid.qrreader.view.fragment.SummaryFrag;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_TAG = MainActivity.class.getSimpleName();
    private String FROM_TAG;
    public static String DATAS = "datas";
    public String cusIdd = "";
    private Bundle data = new Bundle();
    private SharedPreferences myPrefs;

    //public final Resources res = App.getInstance().getResources();

    // User Session Manager Class
    //public UserSessionManager session = UserSessionManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            initFragment();
        }


    }

    private void initFragment() {
        if (null == data)
            data = new Bundle();

        data.putInt(FragmentKey.INDEX, 0);
        data.putString(FROM_TAG, Constants.FROM_TAG);
        getSupportFragmentManager()
                .beginTransaction()
                // .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .add(R.id.main_login_container, LoginFrag.newInstance(data), "LoginFrag")
                .addToBackStack("LoginFrag")
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        Fragment f = getSupportFragmentManager().findFragmentByTag("HomeScreen");
        Fragment summaryfrag = getSupportFragmentManager().findFragmentByTag("SummaryFrag");
        if (f instanceof HomeScreen) {//the fragment on which you want to handle your back press
            Log.i("BACK PRESSED", "BACK PRESSED");
        } else if (summaryfrag instanceof SummaryFrag) {//the fragment on which you want to handle your back press
            Log.i("BACK PRESSED", "BACK PRESSED");
            Fragment homescreenFrag = getSupportFragmentManager().findFragmentByTag("SummaryFrag");

            homescreenFrag.setEnterTransition(new Slide(Gravity.RIGHT));
            homescreenFrag.setExitTransition(new Slide(Gravity.LEFT));
            data.putInt(FragmentKey.INDEX, 1);
            data.putString(FROM_TAG, Constants.FROM_TAG);
            getSupportFragmentManager()
                    .beginTransaction()
                    //   .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(R.id.main_login_container, homescreenFrag, "HomeScreen")
                    .addToBackStack("HomeScreen")
                    .detach(homescreenFrag)
                    .attach(homescreenFrag)
                    .commit();

        } else {
            //super.onBackPressed();
        }
    }

    public void uiBackPressed() {
        if (!popFragment()) {
            //finish();
            finish();
        }
    }

    public void onUpdateHomeActivity(String newActFrg, boolean backPress) {
        try {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
        }

        Intent intent = new Intent();
        intent.putExtra(Constants.FROM_TAG, CURRENT_TAG);
        intent.putExtra(Constants.FROM_TAG_NEW_ACT_FRG, newActFrg);
        intent.putExtra(Constants.BACK_PRESSED, backPress);
        //intent.putExtra(TEAM_CREATED, teamCreated);
        setResult(RESULT_OK, intent);

        finish();
    }

    public boolean popFragment() {
        boolean isPop = false;
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.main_login_container);
        //  LOGE(TAG, "POP Back Current Fragment " + currentFragment);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                //onUpdateHomeActivity("CreateLeagueDialogFrag", true);
                // LOGI(TAG, "All Fragment Finished!");
                return false;
            }
            isPop = true;
        }
        return isPop;
    }

    public void addHomeScreenFragment() {

        HomeScreen homeSc = HomeScreen.newInstance(data);
        homeSc.setEnterTransition(new Slide(Gravity.RIGHT));
        homeSc.setExitTransition(new Slide(Gravity.LEFT));
        data.putInt(FragmentKey.INDEX, 1);
        data.putString(FROM_TAG, Constants.FROM_TAG);
        getSupportFragmentManager()
                .beginTransaction()
                //   .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.main_login_container, homeSc, "HomeScreen")
                .addToBackStack("HomeScreen")
                .commitAllowingStateLoss();
    }

    public void addSummaryFragment(Bitmap bitmap) {

        data.putInt(FragmentKey.INDEX, 2);
        data.putString(FROM_TAG, Constants.FROM_TAG);
        data.putParcelable("image", bitmap);
        getSupportFragmentManager()
                .beginTransaction()
                //   .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.main_login_container, SummaryFrag.newInstance(data), "SummaryFrag")
                .addToBackStack("SummaryFrag")
                .commitAllowingStateLoss();
    }

    public int cusId(int value) {
        cusIdd = "" + value;
        return value;
    }

    public void addRefreshHomeScreenFragment() {

        HomeScreen homeSc = HomeScreen.newInstance(data);

        homeSc.setEnterTransition(new Slide(Gravity.RIGHT));
        homeSc.setExitTransition(new Slide(Gravity.LEFT));
        data.putInt(FragmentKey.INDEX, 1);
        data.putString(FROM_TAG, Constants.FROM_TAG);
        getSupportFragmentManager()
                .beginTransaction()
                //   .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .replace(R.id.main_login_container, homeSc, "HomeScreen")
                .addToBackStack("HomeScreen")
                .commit();
    }

    public void setDatas(ArrayList<String> arrayList) {

        //Set Preference
        myPrefs = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        SharedPreferences.Editor prefsEditor;
        prefsEditor = myPrefs.edit();
//strVersionName->Any value to be stored
        Set<String> set = new HashSet<String>();
        set.addAll(arrayList);
        prefsEditor.putStringSet(DATAS, set);
        prefsEditor.commit();
        // prefsEditor.putString(DATAS, values);

    }

    public ArrayList<String> getStoredArrayList() {
        //Get Preferenece
        SharedPreferences myPrefs;
        ArrayList<String> arrayList=new ArrayList<>();
        myPrefs = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        // String StoredValue = myPrefs.getString(DATAS, "null");

        Set<String> set = myPrefs.getStringSet(DATAS, null);
        if(set!=null){
            arrayList = new ArrayList<String>(set);
        }
        return  arrayList;
    }
}
