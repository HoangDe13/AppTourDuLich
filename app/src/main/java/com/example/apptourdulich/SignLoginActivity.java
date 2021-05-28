package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.content.IntentFilter;

import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class SignLoginActivity extends AppCompatActivity {
 Button dangnhap;
 TextView  dangki,Demo;
 EditText edtSoDienThoai;
 ImageView imageView;
NetworkChangeListener networkChangeListener=new NetworkChangeListener();
  CallbackManager callbackManager = CallbackManager.Factory.create();
         private FirebaseAuth mFirebaseAuth;
         private LoginButton loginButton;

private static final  String Email="email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_login);
//facebook
        mFirebaseAuth=FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        dangnhap=findViewById(R.id.btndn);
        loginButton=findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(Email));

        edtSoDienThoai=findViewById(R.id.edtSDT);

        dangki=findViewById(R.id.btnDangKi);

        callbackManager=CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(SignLoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                handleFacebookToken(loginResult.getAccessToken());
                Intent i=new Intent(SignLoginActivity.this,Home.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignLoginActivity.this,"Login Cancel",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignLoginActivity.this,"Login error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        dangki=findViewById(R.id.btnDangKi);

        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SignLoginActivity.this,Register.class);
                startActivity(i);
            }
        });
      edtSoDienThoai=findViewById(R.id.edtSDT);
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SoDienThoai= edtSoDienThoai.getText().toString().trim();
                if(SoDienThoai.isEmpty())
                {
                    edtSoDienThoai.setError("Phone is requied");
                    edtSoDienThoai.requestFocus();
                    return;
                }
               else{
               dangnhap.setVisibility(View.VISIBLE);

                PhoneAuthOptions options;
                options = PhoneAuthOptions.newBuilder(mFirebaseAuth)
                        .setPhoneNumber("+84"+edtSoDienThoai.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60l, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(SignLoginActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                dangnhap.setVisibility(View.VISIBLE);


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                dangnhap.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onCodeSent(@NonNull String s,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                               dangnhap.setVisibility(View.VISIBLE);
                                Intent i=new Intent(SignLoginActivity.this,Confirm_otp.class);

                                i.putExtra("SoDienThoai",edtSoDienThoai.getText().toString());
                                i.putExtra("codeotp",s);
                                startActivity(i);
                            }
                        } )          // OnVerificationStateChangedCallbacks
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);


            }}
        });
    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential=FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user= mFirebaseAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    Toast.makeText(SignLoginActivity.this,"Login error",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void updateUI(FirebaseUser user) {
        String username,photo,Sodienthoai;
        if(user != null){
            username=user.getDisplayName();
            Sodienthoai=user.getPhoneNumber();
            if(user.getPhotoUrl()!=null)
            {
                photo=user.getPhotoUrl().toString();
            }
        }else{
            username="user";
            photo="no";

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
    }

    @Override
    protected void onStop() {

        super.onStop();
        unregisterReceiver(networkChangeListener);
    }
}