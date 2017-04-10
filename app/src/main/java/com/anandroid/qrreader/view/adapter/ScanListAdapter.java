package com.anandroid.qrreader.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.app.Fragment;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anandroid.qrreader.R;
import com.anandroid.qrreader.view.fragment.HomeScreen;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Charles on 2/27/2017.
 */

public class ScanListAdapter extends RecyclerView.Adapter<ScanListAdapter.MyViewHolder> {
    private static final String CURRENT_TAG = ScanListAdapter.class.getSimpleName();

    private ArrayList<String> listData;
    private Fragment fragment;

    public interface DataPass {
        void dataArray(ArrayList<String> arrayList);
    }

    private DataPass dataPass;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtData;
        private ImageView delete;


        public MyViewHolder(View view) {
            super(view);
            txtData = (TextView) view.findViewById(R.id.txtData);
            delete = (ImageView) view.findViewById(R.id.delete);

        }
    }


    public ScanListAdapter(Fragment fragment, ArrayList<String> listData) {
        this.fragment = fragment;
        dataPass = (DataPass) fragment;
        this.listData = listData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scan_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtData.setText(listData.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData.remove(position);
                notifyDataSetChanged();
                ((HomeScreen) fragment).dataFrag(listData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void clear() {
        if (null != listData) {
            listData.clear();
            notifyDataSetChanged();
        }
    }

    public void updateList(ArrayList<String> list) {
        listData = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
