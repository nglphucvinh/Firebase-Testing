package com.example.bill.firebasetesting2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

    public static final String PARAM_ID = "paramid";
    public static final String PARAM_NAME = "paramname";

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

//        listViewParams.setOnClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i. long l){
//
//            }
//
//        });
        listViewParams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected paramhttps://leasedline.co.uk/leased-line/adsl-vs-leased-lines-which-one-is-best-for-your-business/
                Param param = paramList.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);

                //putting param name and id to intent
                intent.putExtra(PARAM_ID, param.getParamID());
                intent.putExtra(PARAM_NAME, param.getParamName());

                //starting the activity with intent
                startActivity(intent);
            }
        });
        listViewParams.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Param param = paramList.get(position);
                showUpdateDialog(param.getParamID(), param.getParamName());

                return false;
            }
        });
    }

    private void goToMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

    }

    private void showUpdateDialog(final String paramID, String paramName){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // Inflate the View
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        // Use custom layout
        dialogBuilder.setView(dialogView);
        // Lookup view for data population
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Spinner spinnerLocations = (Spinner) dialogView.findViewById(R.id.spinner);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        // Set title
        dialogBuilder.setTitle("Updating Name - " + paramName);
        // Create alert dialog & show it
        final AlertDialog alertDialog =  dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String location = spinnerLocations.getSelectedItem().toString();

                if(TextUtils.isEmpty(name)){
                    editTextName.setError("Name required!");
                    return;
                }

                updateParam(paramID, name, location);
                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteParam(paramID);
            }
        });
    }

    private void deleteParam(String paramID){
        DatabaseReference drParam = FirebaseDatabase.getInstance().getReference("params").child(paramID);
        DatabaseReference drItem = FirebaseDatabase.getInstance().getReference("items").child(paramID);

        drParam.removeValue();
        drItem.removeValue();

        Toast.makeText(this,"Param Deleted", Toast.LENGTH_LONG).show();
    }

    private boolean updateParam(String id, String name, String location){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("params");

        Param param = new Param(id, name, location);

        databaseReference.setValue(param);

        Toast.makeText(this, "Param Updated Succesfully", Toast.LENGTH_LONG).show();

        return true;
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
                // Log.w(this, "Failed to read value.", error.toException());
            }
        });
    }
}
