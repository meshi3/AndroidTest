package com.example.boriskalim.tipcalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vitali on 08/12/2015.
 */
public class MyAdapter extends BaseAdapter {

    private ArrayList<String[]> mData;

    public MyAdapter(ArrayList<String[]> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            ViewHolder h = new ViewHolder(convertView);
            convertView.setTag(h);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        String[] item = mData.get(position);
        holder.name.setText(item[1]);
        holder.amount.setText(item[0]);
        return convertView;
    }

    private static class ViewHolder {
        TextView name, amount;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.name);
            amount = (TextView) v.findViewById(R.id.ammount);
        }
    }
}
