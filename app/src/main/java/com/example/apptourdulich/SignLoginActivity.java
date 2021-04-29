package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class SignLoginActivity extends AppCompatActivity {
 Button dangnhap;
 TextView  dangki,Demo;
 EditText edtSoDienThoai;
 ImageView imageView;

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
        /*
        dangki=findViewById(R.id.btnDangKi);
        edtSoDienThoai=findViewById(R.id.edtSDT);
        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SignLoginActivity.this,sign_up.class);
                startActivity(i);
            }
        });
*/
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignLoginActivity.this,Confirm_otp.class);
                Bundle b= new Bundle();
                b.putString("SoDienThoai",edtSoDienThoai.getText().toString());
                i.putExtras(b);
                startActivity(i);
            }
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

}