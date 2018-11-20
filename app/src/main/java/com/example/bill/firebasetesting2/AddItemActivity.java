package com.example.bill.firebasetesting2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    TextView textViewParamName;
    EditText editTextItemName;
    SeekBar seekBarRating;
    Button buttonAddItem;

    ListView listViewItems;
    List<Item> itemList;
    DatabaseReference databaseItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        textViewParamName = (TextView) findViewById(R.id.textViewParamName);
        editTextItemName = (EditText) findViewById(R.id.editTextItemName);
        seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);

        buttonAddItem = (Button) findViewById(R.id.buttonAddItems);

        listViewItems = (ListView) findViewById(R.id.listViewItems);

        Intent intent = getIntent();

        String id = intent.getStringExtra(ReadActivity.PARAM_ID);
        String name = intent.getStringExtra(ReadActivity.PARAM_NAME);

        itemList = new ArrayList<>();

        textViewParamName.setText(name);

        databaseItems = FirebaseDatabase.getInstance().getReference("items").child(id);

        buttonAddItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                saveItem();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                    Item item = itemSnapshot.getValue(Item.class);

                    itemList.add(item);
                }

                ItemList itemListAdapter = new ItemList(AddItemActivity.this, itemList);
                listViewItems.setAdapter(itemListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveItem(){
        String itemName = editTextItemName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if(!TextUtils.isEmpty(itemName)){

            String id = databaseItems.push().getKey();
            Item item = new Item (id, itemName, rating);
            databaseItems.child(id).setValue(item);

            Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Item name should not be empty", Toast.LENGTH_LONG).show();
        }
    }
}
