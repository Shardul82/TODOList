package edu.niu.cs.z1888485.controller;
/***************************************************************
 *                                                             *
 * CSCI 522      Assignment 8                      Fall 2020   *
 *                                                             *
 * Class Name:  MyAdapter                                 *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: MyAdapter class is a controller class to get view, *
 *          items and description.                             *
 *                                                             *
 ***************************************************************/
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.niu.cs.z1888485.R;
import edu.niu.cs.z1888485.model.Item;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Item> {

    private int resourceLayout;
    private Context mContext;

    public MyAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    // getView method checks if view is null or not. If not then it returns view.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
        // to get item position
        Item p = getItem(position);
        if (p != null) {

            TextView description = (TextView) v.findViewById(R.id.description);

            if (description != null) {
                description.setText(p.getDescription());
            }
        }
        return v;
    }
}

