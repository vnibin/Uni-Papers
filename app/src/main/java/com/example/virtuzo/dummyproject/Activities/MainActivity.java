package com.example.virtuzo.dummyproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.virtuzo.dummyproject.Fragments.Others;
import com.example.virtuzo.dummyproject.Fragments.University;
import com.example.virtuzo.dummyproject.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   // private Button mlogoutbtn;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private Boolean bool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bool= getIntent().getBooleanExtra("fingerPrint", false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mAuth=FirebaseAuth.getInstance();
      if(!bool) {
          mAuthListener = new FirebaseAuth.AuthStateListener() {
              @Override
              public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                  if (firebaseAuth.getCurrentUser() == null) {

                      startActivity(new Intent(MainActivity.this, Login_Acitivity.class));
                  }
              }
          };


      }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!bool) {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


//            int backButtonCount=0;
//            if(backButtonCount >= 1)
//            {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//            else
//            {
//                Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
//                backButtonCount++;
//            }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment=null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.university) {
            fragment=new University();
            // Handle the camera action
        }
       else if (id == R.id.nav_logout) {


            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Logging out....");
            progressDialog.show();



            mAuth.signOut();

        }

        else if (id == R.id.others) {
            fragment=new Others();



        } else if (id == R.id.exit) {

            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        if(fragment!=null)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction f1=fragmentManager.beginTransaction();
            f1.replace(R.id.frame1,fragment);
            f1.commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
