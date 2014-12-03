package com.example.miguel.placestore;

/**
 * Created by Miguel on 03/12/2014.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Miguel on 20/11/2014.
 */
public class listAdapter extends BaseAdapter {
    //Variables
    private Context mContext = null;
    private ArrayList<listPlaces> mArrayList = null;
    private LayoutInflater mLayoutInflater = null;

    public listAdapter (Context context, ArrayList <listPlaces> arrayList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mArrayList = arrayList;
    }

    @Override
    public Object getItem(int position) {

        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getCount (){
        return mArrayList.size();
    }

    static class Holder {
        ImageView imageMarker;
        TextView txtName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        View view = convertView;
        //Check if view is null
        if (view == null){
            holder = new Holder ();
            view = mLayoutInflater.inflate(R.layout.activity_list_places,null);
            holder.imageMarker = (ImageView)view.findViewById(R.id.image_mark);
            holder.txtName = (TextView)view.findViewById(R.id.txt_name);
            view.setTag(holder);
        }
        else{
            holder=(Holder)view.getTag();

        }
        //holder.imageMarker.setImageDrawable(mArrayList.get(position).getMarkLogo());
        holder.txtName.setText(mArrayList.get(position).getPlace());

        return view;
    }



}




