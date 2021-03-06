package com.anandroid.qrreader.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.app.Fragment;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
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
 * Created by Anand on 2/27/2017.
 */

public class PastOrdersAdapter extends RecyclerView.Adapter<PastOrdersAdapter.MyViewHolder> {
    private static final String CURRENT_TAG = PastOrdersAdapter.class.getSimpleName();

    private ArrayList<String> listData;
    private Fragment fragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtData;


        public MyViewHolder(View view) {
            super(view);
            txtData = (TextView) view.findViewById(R.id.past_txtData);

        }
    }

    public PastOrdersAdapter(Fragment fragment, ArrayList<String> listData) {
        this.fragment = fragment;
        this.listData = listData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.past_orders_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtData.setText(listData.get(position));
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
