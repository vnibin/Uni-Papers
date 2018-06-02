package com.example.virtuzo.dummyproject.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtuzo.dummyproject.R;
import com.example.virtuzo.dummyproject.utils.FingerPrintHandler;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Login_Acitivity extends AppCompatActivity {
    private SignInButton mgooglebutton;                         //for google login
    private TextView TextView_signup;
    private  static final int RC_SIGN_IN=1;      //for google login
    private GoogleApiClient mGoogleApiClient;            //for google login
    private FirebaseAuth mAuth,mAuth1;                      //for google login
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG=" LOGIN_ACTIVITY"; //for google login
    private EditText editText_email;
    private EditText editText_password;
    private Button loginbutton;
    ProgressBar progressBar;
    public Boolean bool;


    //Fingerprint authentication

    private TextView mheaderlabel;
    private ImageView mfingerprintimage;
    private  TextView mparalabel;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME="AndroidKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__acitivity);
        editText_email = (EditText) findViewById(R.id.input_email);
        editText_password = (EditText) findViewById(R.id.input_password);
        TextView_signup = (TextView) findViewById(R.id.link_signup);
        loginbutton = (Button) findViewById(R.id.btn_login);


        fingerPrintAuthenticate();
//  for Google login

//        if (bool) {
//            //Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
//
     // }
            mAuth = FirebaseAuth.getInstance();
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() != null) {
                        startActivity(new Intent(Login_Acitivity.this, MainActivity.class));
                    }
                }
            };


            mgooglebutton = (SignInButton) findViewById(R.id.googlebtn);

            // Configure Google Sign In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            Toast.makeText(Login_Acitivity.this, " you got an error", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            mgooglebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });


            TextView_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(Login_Acitivity.this, Signin_Acitivity.class));
                }
            });

            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginUser();

                }
            });


    }

    private  void loginUser()
    {
        //String name=editText_name.getText().toString().trim();
        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();


        if (email.isEmpty()) {
            editText_email.setError(" Email is required");
            editText_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText_email.setError("please enter a valid email address");
            editText_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editText_email.setError(" Password is required");
            editText_email.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editText_password.setError("Minimum length of password should be atleast 6");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Intent intent =new Intent(Login_Acitivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {

                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });

    }






        @Override
    protected void onStart() {
        super.onStart();
       // if(!bool) {
            mAuth.addAuthStateListener(mAuthListener);
        //}


    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        ///--------------Google  Login

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            //Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);


            if(result.isSuccess())
            {
                GoogleSignInAccount account=result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            /*try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }*/
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        // Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging in..");
        progressDialog.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // Sign in success, update UI with the signed-in user's information

                        Log.d(TAG, "signInWithCredential:ONcomplete :"+task.isSuccessful());
                        FirebaseUser user = mAuth.getCurrentUser();
                        //updateUI(user);
                        if (!task.isSuccessful())
                        {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                            Toast.makeText(Login_Acitivity.this,"Authentication failed",Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }


        public Boolean fingerPrintAuthenticate()
        {
            mheaderlabel=(TextView)findViewById(R.id.headinglabel);
            mfingerprintimage=(ImageView) findViewById(R.id.fingerprintimage);
            mparalabel=(TextView)findViewById(R.id.paralabel);

            //T check 1: android version should be greater or equal to marshmallow
            // check 2: Device has fingerprint scanner
            // check 3: Have permission to use fingerprint scanner in the app
            // check 4: lock screen is secured with atleast 1 type of lock
            //Todo check 5: atleast 1 fingerprint is registered



            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)  // check 1: android version should be greater or equal to marshmallow
            {
                fingerprintManager=(FingerprintManager)getSystemService(FINGERPRINT_SERVICE);
                keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                if(!fingerprintManager.isHardwareDetected())        // check 2: Device has fingerprint scanner
                {
                    mparalabel.setText("Fingerprint scanner is not detected in this device");
                }
                else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED)     //  check 3: Have permission to use fingerprint scanner in the app
                {
                    mparalabel.setText("Permission not granted to use Fingerprint scanner");
                }

                else if(!keyguardManager.isKeyguardSecure())      //  check 4: lock screen is secured with atleast 1 type of lock
                {
                    mparalabel.setText("Add Lock to your phone in settings");
                }

                else if (!fingerprintManager.hasEnrolledFingerprints()){

                    mparalabel.setText("You should add atleast 1 Fingerprint to use this Feature");    // check 5: atleast 1 fingerprint is registered

                }
                else {

                    mparalabel.setText("Place your Finger on Scanner to Access the App.");
                    generateKey();


                    if(cipherInit()) {

                        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                        FingerPrintHandler fingerprintHandler = new FingerPrintHandler(this, new FingerPrintHandler.Callback() {
                            @Override
                            public void onFingerprintscan() {

                                Intent intent = new Intent(Login_Acitivity.this, MainActivity.class);
                                intent.putExtra("fingerPrint", true);
                                startActivity(intent);

                            }
                        });
                        fingerprintHandler.startAuth(fingerprintManager, cryptoObject);

                    }
                }

            }
        return true;
        }



    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() {                // generate and stores keys in keystore

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

        } catch (KeyStoreException | IOException | CertificateException
                | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | NoSuchProviderException e) {

            e.printStackTrace();

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {

            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }

    }

}

