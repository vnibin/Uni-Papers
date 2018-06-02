package com.example.virtuzo.dummyproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.virtuzo.dummyproject.Activities.MainActivity;
import com.example.virtuzo.dummyproject.R;

/**
 * Created by Virtuzo on 24/04/18.
 */

public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;
    private FingerPrintHandler.Callback listener;



    public FingerPrintHandler(Context context, FingerPrintHandler.Callback callback) {
        this.context = context;
        this.listener=callback;
    }


    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {   // for fingerprint authentication
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }

    public interface Callback
    {
        public void onFingerprintscan();
    }


    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an Auth Error. " + errString, false);


    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Auth Failed. ", false);

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: " + helpString, false);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("You can now access the app.", true);


    }

    private void update(String s, boolean b) {

        TextView paraLabel = (TextView) ((Activity) context).findViewById(R.id.paralabel);
        ImageView imageView = (ImageView) ((Activity) context).findViewById(R.id.fingerprintimage);

        paraLabel.setText(s);


        if (b == false) {

            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        } else {

            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            imageView.setImageResource(R.mipmap.action_done);
            listener.onFingerprintscan();


        }

    }
}
