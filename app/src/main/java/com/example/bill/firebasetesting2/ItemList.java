package com.example.bill.firebasetesting2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemList extends ArrayAdapter<Item> {
    private Activity context;
    private List<Item> itemList;

    public ItemList(Activity context, List<Item> itemList){
        super(context, R.layout.list_layout, itemList); //ArrayAdapter constructor
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull //only this view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = itemList.get(position);
        // Inflate the view
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_layout, null, true);
        // Lookup view for data population
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.textViewRating);
        // Populate the data into the template view susing the data object
        textViewName.setText(item.getItemName());
        textViewRating.setText(String.valueOf(item.getItemRating()));
        //Return the completed view to render on screen
        return listViewItem;
    }
}
