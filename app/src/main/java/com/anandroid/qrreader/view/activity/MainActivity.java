package com.anandroid.qrreader.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anandroid.qrreader.R;
import com.anandroid.qrreader.utills.Constants;
import com.anandroid.qrreader.utills.FragmentKey;
import com.anandroid.qrreader.view.fragment.HomeScreen;
import com.anandroid.qrreader.view.fragment.LoginFrag;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_TAG = MainActivity.class.getSimpleName();

    private String FROM_TAG;

    private Bundle data = new Bundle();

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
        if (!popFragment()) {
            //finish();
            super.onBackPressed();
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

        data.putInt(FragmentKey.INDEX, 1);
        data.putString(FROM_TAG, Constants.FROM_TAG);
        getSupportFragmentManager()
                .beginTransaction()
                //   .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .add(R.id.main_login_container, HomeScreen.newInstance(data), "HomeScreen")
                .addToBackStack("HomeScreen")
                .commitAllowingStateLoss();
    }
}
