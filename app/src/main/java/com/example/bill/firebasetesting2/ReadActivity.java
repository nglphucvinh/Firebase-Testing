package com.example.bill.firebasetesting2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

    DatabaseReference databaseParam;

    ListView listViewParams;

    List<Param> paramList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        databaseParam = FirebaseDatabase.getInstance().getReference("params");

        Button button = (Button) findViewById(R.id.buttonChange);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goToMainActivity();
            }

        });

        listViewParams = (ListView) findViewById(R.id.listViewParams);

        paramList = new ArrayList<>();

    }

    private void goToMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseParam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                paramList.clear();

                for(DataSnapshot paramSnapshot : dataSnapshot.getChildren()){
                    Param param = paramSnapshot.getValue(Param.class);

                    paramList.add(param);
                }

                ParamList adapter = new ParamList(ReadActivity.this, paramList);
                listViewParams.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
