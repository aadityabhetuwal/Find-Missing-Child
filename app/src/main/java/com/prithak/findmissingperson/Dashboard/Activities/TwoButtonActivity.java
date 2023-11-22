package com.prithak.findmissingperson.Dashboard.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.prithak.findmissingperson.Dashboard.Fragments.CasesViewActivity;
import com.prithak.findmissingperson.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TwoButtonActivity extends AppCompatActivity {
      CardView reportmissingperson,foundpersson;
      CircleImageView backarrowicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_two_button );
        backarrowicon = findViewById(R.id.backarrowicon);
        backarrowicon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        } );
        reportmissingperson=findViewById( R.id.reportmissingperson);
        foundpersson=findViewById( R.id.foundaperson);
        foundpersson.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( TwoButtonActivity.this, CasesViewActivity.class );
                startActivity(intent);
            }
        } );
        reportmissingperson.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Create_Fp_Case.class);
                startActivity(intent);
            }
        } );
    }
}
