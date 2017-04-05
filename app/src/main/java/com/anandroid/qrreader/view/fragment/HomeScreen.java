package com.anandroid.qrreader.view.fragment;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.anandroid.qrreader.R;
import com.anandroid.qrreader.utills.PointsOverlayView;
import com.anandroid.qrreader.view.activity.MainActivity;
import com.anandroid.qrreader.view.adapter.ScanListAdapter;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Admin on 3/13/2017.
 */

public class HomeScreen extends Fragment implements QRCodeReaderView.OnQRCodeReadListener {

    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;
    private CheckBox flashlightCheckBox;
    private CheckBox enableDecodingCheckBox;
    private PointsOverlayView pointsOverlayView;
    private Set<String> setDatas;
    private ArrayList<String> listDatas;

    private ScanListAdapter scanListAdapter;
    @BindView(R.id.scan_list_recyc)
    RecyclerView scan_list_recyc;


    public HomeScreen() {
        super();
        setRetainInstance(true);
    }

    private static final String CURRENT_TAG = HomeScreen.class.getSimpleName();
    private Unbinder unbinder;
    public static final int INDEX = 0;
    private Bundle data;
    private int hideIndex = -1;
    protected AppCompatActivity mBaseAct;
    protected Context mBaseCon;


    public static HomeScreen newInstance(Bundle tag) {
        HomeScreen fragment = new HomeScreen();
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
        View viewRoot = inflater.inflate(R.layout.home_screen_frag, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);

        qrCodeReaderView = (QRCodeReaderView) viewRoot.findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) viewRoot.findViewById(R.id.result_text_view);
        flashlightCheckBox = (CheckBox) viewRoot.findViewById(R.id.flashlight_checkbox);
        enableDecodingCheckBox = (CheckBox) viewRoot.findViewById(R.id.enable_decoding_checkbox);
        pointsOverlayView = (PointsOverlayView) viewRoot.findViewById(R.id.points_overlay_view);


        initQRCodeReaderView();

        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listDatas = new ArrayList<>();
        setDatas = new HashSet<String>();

        scanListAdapter = new ScanListAdapter(HomeScreen.this, listDatas);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        scan_list_recyc.setLayoutManager(layoutManager);
        scan_list_recyc.setItemAnimator(new DefaultItemAnimator());
        scan_list_recyc.smoothScrollToPosition(0);
        scan_list_recyc.setHasFixedSize(true);
        scan_list_recyc.setAdapter(scanListAdapter);
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

        if (qrCodeReaderView != null) {
            qrCodeReaderView.startCamera();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
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

    // Qr Code Methods

    private void initQRCodeReaderView() {
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        flashlightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrCodeReaderView.setTorchEnabled(isChecked);
            }
        });

        enableDecodingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrCodeReaderView.setQRDecodingEnabled(isChecked);
            }
        });
        qrCodeReaderView.startCamera();
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        resultTextView.setText(text);
        Log.i("Done", "" + text);
        // Log.i("Filtered", "" + String.format("%d", text));
        Log.i("Filtered", "" + text.replaceAll("\\D+", ""));

        pointsOverlayView.setPoints(points);
        setDatas.add(text);
        listDatas = new ArrayList<>(setDatas);
        Log.i("Display ", "" + listDatas.toString());
        Log.i("Display 1 ", "" + setDatas.toString());
        scanListAdapter.updateList(listDatas);

    }
}
