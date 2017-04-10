package com.anandroid.qrreader.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.anandroid.qrreader.R;
import com.anandroid.qrreader.utills.PointsOverlayView;
import com.anandroid.qrreader.view.adapter.ScanListAdapter;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Admin on 3/13/2017.
 */

public class SummaryFrag extends Fragment {

    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;
    private CheckBox flashlightCheckBox;
    private CheckBox enableDecodingCheckBox;
    private PointsOverlayView pointsOverlayView;
    private Set<String> setDatas;
    private ArrayList<String> listDatas;
    private int[] arrayNum;

    private ScanListAdapter scanListAdapter;
    @BindView(R.id.summary_qr_img)
    ImageView summary_qr_img;

    public SummaryFrag() {
        super();
        setRetainInstance(true);
    }

    private static final String CURRENT_TAG = SummaryFrag.class.getSimpleName();
    private Unbinder unbinder;
    public static final int INDEX = 0;
    private Bundle data;
    private int hideIndex = -1;
    private Bitmap bitmap;
    protected AppCompatActivity mBaseAct;
    protected Context mBaseCon;


    public static SummaryFrag newInstance(Bundle tag) {
        SummaryFrag fragment = new SummaryFrag();
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
        View viewRoot = inflater.inflate(R.layout.summary_frag, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            data = getArguments();
            if (null != data) {
                bitmap = data.getParcelable("image");
                summary_qr_img.setImageBitmap(bitmap);
                Log.i("Coinggggg", "Hide Index is : " + hideIndex);
            }

        } catch (Exception e) {
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }


    /* @OnClick(R.id.chat_back_img)
    public void onUiBackPressed(View view) {

       *//* getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount()==0){
        }*//*

        ((MainAC) getActivity()).uiBackPressed();
    }*/
/*
    @OnClick(R.id.confirm_img)
    public void onConfirm(View view) {

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

}
