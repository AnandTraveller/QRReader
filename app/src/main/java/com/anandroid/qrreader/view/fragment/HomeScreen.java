package com.anandroid.qrreader.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.anandroid.qrreader.R;
import com.anandroid.qrreader.utills.PointsOverlayView;
import com.anandroid.qrreader.view.activity.MainActivity;
import com.anandroid.qrreader.view.adapter.PastOrdersAdapter;
import com.anandroid.qrreader.view.adapter.ScanListAdapter;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

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

public class HomeScreen extends Fragment implements QRCodeReaderView.OnQRCodeReadListener, ScanListAdapter.DataPass {

    private TextView resultTextView, past_order_txt, current_order_txt;
    private QRCodeReaderView qrCodeReaderView;
    private CheckBox flashlightCheckBox;
    private CheckBox enableDecodingCheckBox;
    private PointsOverlayView pointsOverlayView;
    private Set<String> setDatas;
    private ArrayList<String> listDatas;
    private int[] arrayNum;
    private InputMethodManager imm;
    private ViewGroup viewContainer;
    private boolean isAlreadyRunning = false;
    private ArrayList<String> sharedArrayList;

    private ScanListAdapter scanListAdapter;
    private PastOrdersAdapter pastOrderAdapter;
    @BindView(R.id.scan_list_recyc)
    RecyclerView scan_list_recyc;
    @BindView(R.id.past_list_recyc)
    RecyclerView past_list_recyc;
    @BindView(R.id.total_txt)
    TextView total_txtJ;
    @BindView(R.id.confirm_img)
    ImageView confirm_imgJ;
    @BindView(R.id.progres_bar_homepage)
    ProgressBar progres_bar_homepage;
    private QrGene task;

    public final static int QRcodeWidth = 500;
    Bitmap bitmap;

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

    @Override
    public void onStart() {
        super.onStart();
        //Hide the soft keyboard
        imm.hideSoftInputFromWindow(viewContainer.getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.home_screen_frag, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);

        viewContainer = container;
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        qrCodeReaderView = (QRCodeReaderView) viewRoot.findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) viewRoot.findViewById(R.id.result_text_view);
        flashlightCheckBox = (CheckBox) viewRoot.findViewById(R.id.flashlight_checkbox);
        enableDecodingCheckBox = (CheckBox) viewRoot.findViewById(R.id.enable_decoding_checkbox);
        pointsOverlayView = (PointsOverlayView) viewRoot.findViewById(R.id.points_overlay_view);
        past_order_txt = (TextView) viewRoot.findViewById(R.id.past_order_txt);
        current_order_txt = (TextView) viewRoot.findViewById(R.id.current_order_txt);

        arrayNum = new int[100];
        initQRCodeReaderView();
        return viewRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedArrayList = ((MainActivity) getActivity()).getStoredArrayList();
        if (sharedArrayList != null && !(sharedArrayList.size() > 0)) {
            Log.i("STRING DATAS", "" + sharedArrayList.toString());
            past_order_txt.setVisibility(View.GONE);
        } else {
            Toast.makeText(mBaseAct, "Past Orders Available", Toast.LENGTH_SHORT).show();
            Log.i("STRING DATAS", "" + sharedArrayList.toString());
            pastOrderAdapter = new PastOrdersAdapter(HomeScreen.this, sharedArrayList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            past_list_recyc.setLayoutManager(layoutManager);
            past_list_recyc.setItemAnimator(new DefaultItemAnimator());
            past_list_recyc.smoothScrollToPosition(0);
            past_list_recyc.setHasFixedSize(true);
            past_list_recyc.setAdapter(pastOrderAdapter);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listDatas = new ArrayList<>();
        setDatas = new HashSet<String>();
        task = new QrGene();

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
            // qrCodeReaderView.startCamera();
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

    @OnClick(R.id.current_order_txt)
    public void onCurrent(View view) {
        // Using AsyncTask to Generate Or Image
        scan_list_recyc.setVisibility(View.VISIBLE);
        past_list_recyc.setVisibility(View.GONE);

        past_order_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_dim));
        current_order_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.QRCodeWhiteColor));

    }

    @OnClick(R.id.past_order_txt)
    public void onPast(View view) {
        scan_list_recyc.setVisibility(View.GONE);

        past_order_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.QRCodeWhiteColor));
        current_order_txt.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_dim));
        past_list_recyc.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.confirm_img)
    public void onConfirm(View view) {
        // Using AsyncTask to Generate Or Image
        if (listDatas.size() > 0) {
            if (!isAlreadyRunning) {
                isAlreadyRunning = true;
                task.execute();
            } else {
                Toast.makeText(mBaseAct, "Processing", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(mBaseAct, "No Products in List", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.progres_bar_homepage)
    public void progressCancel(View view) {
        //
        progres_bar_homepage.setVisibility(View.GONE);
        task.cancel(true);
        isAlreadyRunning = false;


    }


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

        // Filtering Integer
        String amount = text.replaceAll("\\D+", "");
        Log.i("Filtered", "" + amount);
        pointsOverlayView.setPoints(points);
        setDatas.add(text);
        listDatas = new ArrayList<>(setDatas);

        int totalValue = 0;

        for (String str : listDatas) {

            String amt = str.replaceAll("\\D+", "").trim();

            totalValue = totalValue + Integer.valueOf(amt);

            Log.i("Amount", "" + totalValue);
        }
  /*      for (int i = 0; i <= listDatas.size(); i++) {
            // Replacing Integer with ""
            //  totalValue = totalValue + Integer.parseInt("1");
            // totalValue = totalValue + Integer.valueOf("1");

            String datatemp = listDatas.get(i).toString().replace("\\D+", "").trim();
            //  String tempData = Integer.parseInt(listDatas.get(i).replace(listDatas.get(i), "").trim());
            //  totalValue = totalValue + Integer.parseInt(text.replace(listDatas.get(i), "").trim());
            //  Log.i("String Filtered", "" + totalValue);
            Log.i("Temp", "" + datatemp);
        }*/


        total_txtJ.setText(totalValue + " Rs");
        Log.i("Display Set ", "" + setDatas.toString());
        Log.i("Display ", "" + listDatas.toString());
        scanListAdapter.updateList(listDatas);
    }

    @Override
    public void dataArray(ArrayList<String> arrayList) {

        listDatas.clear();
        listDatas = arrayList;
    }

    public class QrGene extends AsyncTask<Object, Object, Bitmap>

    {
        BitMatrix bitMatrix;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progres_bar_homepage.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Object... params) {

            try {

                bitMatrix = new MultiFormatWriter().encode(listDatas.toString(),
                        BarcodeFormat.DATA_MATRIX.QR_CODE,
                        QRcodeWidth, QRcodeWidth, null
                );

            } catch (IllegalArgumentException Illegalargumentexception) {

                return null;
            } catch (WriterException e) {
                e.printStackTrace();
            }
            int bitMatrixWidth = bitMatrix.getWidth();

            int bitMatrixHeight = bitMatrix.getHeight();

            int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

            for (int y = 0; y < bitMatrixHeight; y++) {
                int offset = y * bitMatrixWidth;

                for (int x = 0; x < bitMatrixWidth; x++) {

                    pixels[offset + x] = bitMatrix.get(x, y) ?
                            getResources().getColor(R.color.QRCodeBlackColor) : getResources().getColor(R.color.QRCodeWhiteColor);
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

            bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            isAlreadyRunning = false;
            super.onPostExecute(bitmap);
            //  imageView.setImageBitmap(bitmap);
            progres_bar_homepage.setVisibility(View.GONE);
            //  onDetach();

            // Sending to Shared Preference
            ((MainActivity) getActivity()).setDatas(listDatas);
            ((MainActivity) getActivity()).addSummaryFragment(bitmap);

        }
    }

    public void dataFrag(ArrayList<String> data) {
        //  listDatas.clear();
        listDatas = data;
        Log.i("ttttttttttttttttttttt", "" + listDatas.toString());

        int totalValue = 0;

        for (String str : listDatas) {

            String amt = str.replaceAll("\\D+", "").trim();

            totalValue = totalValue + Integer.valueOf(amt);

            Log.i("Amount", "" + totalValue);
        }
  /*      for (int i = 0; i <= listDatas.size(); i++) {
            // Replacing Integer with ""
            //  totalValue = totalValue + Integer.parseInt("1");
            // totalValue = totalValue + Integer.valueOf("1");

            String datatemp = listDatas.get(i).toString().replace("\\D+", "").trim();
            //  String tempData = Integer.parseInt(listDatas.get(i).replace(listDatas.get(i), "").trim());
            //  totalValue = totalValue + Integer.parseInt(text.replace(listDatas.get(i), "").trim());
            //  Log.i("String Filtered", "" + totalValue);
            Log.i("Temp", "" + datatemp);
        }*/


        total_txtJ.setText(totalValue + " Rs");

    }

}
