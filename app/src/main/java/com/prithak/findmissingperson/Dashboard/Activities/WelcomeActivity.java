package com.prithak.findmissingperson.Dashboard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prithak.findmissingperson.Authentication.AuthActivity;
import com.prithak.findmissingperson.Dashboard.MainActivity;
import com.prithak.findmissingperson.R;

public class WelcomeActivity extends AppCompatActivity {
 RelativeLayout welcomescreen;
    private FirebaseAuth auth ;
    private int aa ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_welcome );
        welcomescreen=findViewById( R.id.welcomescreen );
        auth = FirebaseAuth.getInstance();
        welcomescreen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getAlarm = getIntent().getStringExtra("alarmDialog");
                final FirebaseUser user = auth.getCurrentUser();
                if (user!=null ) {
                    if (user.isEmailVerified()) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(WelcomeActivity.this, "Email is not Verified", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } );
    }

}
