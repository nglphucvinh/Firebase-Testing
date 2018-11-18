package com.example.bill.firebasetesting2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ParamList extends ArrayAdapter<Param> {

    private Activity context;
    private List<Param> paramList;

    public ParamList(Activity context, List<Param> paramList){
        super(context, R.layout.list_layout, paramList); //ArrayAdapter constructor
        this.context = context;
        this.paramList = paramList;
    }

    @NonNull //only this view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Param param = paramList.get(position);
        // Inflate the view
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        // Lookup view for data population
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewLocation = (TextView) listViewItem.findViewById(R.id.textViewLocation);
        // Populate the data into the template view susing the data object
        textViewName.setText(param.getParamName());
        textViewLocation.setText(param.getParamLocation());
        //Return the completed view to render on screen
        return listViewItem;
    }


}
