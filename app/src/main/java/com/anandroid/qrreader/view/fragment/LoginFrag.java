package com.anandroid.qrreader.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.anandroid.qrreader.R;
import com.anandroid.qrreader.view.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Admin on 3/13/2017.
 */

public class LoginFrag extends Fragment {

    @BindView(R.id.login_btn)
    Button login_btnJ;
    @BindView(R.id.cus_id_login)
    EditText cus_id_login;
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    int[] cusId = {111, 222, 333, 444, 555};

    public LoginFrag() {
        super();
        setRetainInstance(true);
    }

    private static final String CURRENT_TAG = LoginFrag.class.getSimpleName();
    private Unbinder unbinder;
    public static final int INDEX = 0;
    private Bundle data;
    private int hideIndex = -1;
    protected AppCompatActivity mBaseAct;
    protected Context mBaseCon;
    private LoginFrag fragment;
    private CustomerId customerId;

    public static LoginFrag newInstance(Bundle tag) {
        LoginFrag fragment = new LoginFrag();
        fragment = fragment;
        Bundle args = tag;
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context context) {
        mBaseCon = context;
        if ((mBaseCon instanceof AppCompatActivity)) {
            mBaseAct = (AppCompatActivity) context;
        }
        super.onAttach(context);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        } else {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.login_frag, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);
        cus_id_login.addTextChangedListener(new GenericTextWatcher(cus_id_login));

        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

      /*  try {
            data = getArguments();
            if (null != data) {
                hideIndex = data.getInt(FragmentKey.HIDE_INDEX, -1);
                LOGI(TAG, "Hide Index is : " + hideIndex);
            }

        } catch (Exception e) {
        }*/


    }

    @Override
    public void onResume() {
        super.onResume();

    }


   /* @OnClick(R.id.chat_back_img)
    public void onUiBackPressed(View view) {

       *//* getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount()==0){
        }*//*

        ((MainAC) getActivity()).uiBackPressed();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        unbinder = null;
        mBaseAct = null;
        mBaseCon = null;
        super.onDetach();
    }

    @OnClick(R.id.login_btn)
    public void loginClick(View view) {
        Log.i("The Login", " Clicked");

        if (cus_id_login.length() > 0) {
            int value = Integer.parseInt(cus_id_login.getText().toString());
            // Passing Value
            // customerId.onCustId(value);
            ((MainActivity) getActivity()).cusId(value);

            for (int i : cusId) {
                if (value == i) {

                    permission();
                }

            }

        } else {
            Toast.makeText(mBaseAct, "Please Enter Id", Toast.LENGTH_SHORT).show();
        }

    }

    private void permission() {

        if (ContextCompat.checkSelfPermission(mBaseAct, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mBaseAct,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            ((MainActivity) getActivity()).addHomeScreenFragment();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ((MainActivity) getActivity()).addHomeScreenFragment();

                } else {
                    Toast.makeText(mBaseAct, "Please grant camera permission", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    //Declaration
    private class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         /*   if (charSequence.length() > 2) {
                int value = Integer.parseInt(cus_id_login.getText().toString());

                for (int j : cusId) {
                    if (value == j) {

                        permission();
                    }

                }

            }*/
        }

        public void afterTextChanged(Editable editable) {

        }
    }

    public interface CustomerId {
        /*Callback for when an item has been selected. */
        public void onCustId(int id);
    }


}
