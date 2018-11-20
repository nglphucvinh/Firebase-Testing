package com.example.bill.firebasetesting2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    public static final String EXTRA_MESSAGE = "com.example.bill.firebasetesting2.MESSAGE";

    EditText editTextName;
    Button buttonAdd;
    Button buttonWrite;
    Spinner spinnerLocations;
    // Write a message to the database
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseParam;

//    ListView listViewParams;
//
//    List<Param> paramList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       myRef.setValue("Hello, World!");

        Button button = (Button) findViewById(R.id.buttonChange);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goToSecondActivity();
            }

        });

        databaseParam = FirebaseDatabase.getInstance().getReference("params");

        final String ID = databaseParam.push().getKey(); // This is for constant ID, put this in add Param, and remove final if you want to add many

//        ((GlobalKey) this.getApplication()).setKey(id);

        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonWrite = (Button) findViewById(R.id.buttonWrite);
        spinnerLocations = (Spinner) findViewById(R.id.spinnerLocations);

//        listViewParams = (ListView) findViewById(R.id.listViewParams);
//
//        paramList = new ArrayList<>();

        buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addParam(ID);
            }
        });

    }

    private void goToSecondActivity() {
        Intent intent = new Intent(this, ReadActivity.class);
        startActivity(intent);
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        databaseParam.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                paramList.clear();
//
//                for(DataSnapshot paramSnapshot : dataSnapshot.getChildren()){
//                    Param param = paramSnapshot.getValue(Param.class);
//
//                    paramList.add(param);
//                }
//
//                ParamList adapter = new ParamList(MainActivity.this, paramList);
//                listViewParams.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void addParam(String id){
        String name = editTextName.getText().toString().trim();
        String location = spinnerLocations.getSelectedItem().toString();

        if(!TextUtils.isEmpty(editTextName.getText())){

            Param param = new Param (id, location, name);

            databaseParam.child(id).setValue(param);

            Toast.makeText(this,"Value added", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "You should enter a name", Toast.LENGTH_LONG).show();
        }
    }



}
