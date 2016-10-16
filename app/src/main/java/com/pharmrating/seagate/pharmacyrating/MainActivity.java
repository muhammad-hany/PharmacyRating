package com.pharmrating.seagate.pharmacyrating;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText name;
    EditText description,phoneNumber;
    Button button;
    ProgressDialog progressDialog;
    private Spinner spinner;
    String phramacyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setDefaultLanguage(this);
        final FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference reference=firebaseDatabase.getReference().child("Rates");

        defineViews();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=ProgressDialog.show(MainActivity.this,"Please wait","Sending " +
                        "data",true,false);
                java.text.DateFormat format=new SimpleDateFormat("MMM dd, yyyy h:mm a");
                String date=format.format(new Date());
                reference.push().setValue(new Rate(ratingBar.getRating(), name.getText()
                        .toString(), description.getText().toString(),phoneNumber.getText().toString(),date,phramacyType), new DatabaseReference
                        .CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        progressDialog.dismiss();
                        if (databaseError==null){
                            description.setText("");
                            phoneNumber.setText("");
                            name.setText("");
                            ratingBar.setProgress(0);
                            button.setEnabled(false);
                            Toast.makeText(MainActivity.this,"Your rating has been submitted",Toast.LENGTH_LONG).show();
                        }else {
                            String error=databaseError.getMessage();
                            Toast.makeText(MainActivity.this,error,Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }



    private void defineViews() {
        ratingBar= (RatingBar) findViewById(R.id.ratingBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ratingBar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        name= (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);

        button= (Button) findViewById(R.id.button);
        button.setEnabled(false);
        phoneNumber= (EditText) findViewById(R.id.phone);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                button.setEnabled(true);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.spinner_choices,R.layout.my_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phramacyType=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public static void setDefaultLanguage(Context context) {
        Locale locale = new Locale(Locale.US.getLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
}
