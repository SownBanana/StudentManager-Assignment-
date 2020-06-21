package com.example.studentmanager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentAdapter extends BaseAdapter {
    private List<Student> items;

    public StudentAdapter(List<Student> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHoler;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, null);
            viewHoler = new ViewHolder();
            viewHoler.txtSid = convertView.findViewById(R.id.txt_sid);
            viewHoler.txtSName = convertView.findViewById(R.id.txt_name);
            viewHoler.txtDob = convertView.findViewById(R.id.txt_dob);
            convertView.setTag(viewHoler);
        } else viewHoler = (ViewHolder) convertView.getTag();

        if (!items.isEmpty()) {
            viewHoler.txtSid.setText(items.get(position).getSid() + "");
            viewHoler.txtSName.setText(items.get(position).getName());
            viewHoler.txtDob.setText(items.get(position).getDob());
        }
        return convertView;
    }


    class ViewHolder {
        TextView txtSid;
        TextView txtSName;
        TextView txtDob;
    }
}