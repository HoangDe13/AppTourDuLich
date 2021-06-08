package com.example.apptourdulich;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Confirm_otp extends AppCompatActivity {
    Button btnXacNhan;

    TextView SoDienThoai;
    EditText otp;
    private String codeotp;
    TextView guilai;
    private FirebaseAuth mFirebaseAuth;


    
    NetworkChangeListener networkChangeListener=new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_otp);
        mFirebaseAuth=FirebaseAuth.getInstance();
        btnXacNhan=findViewById(R.id.btnXacNhanOTP);
        SoDienThoai=findViewById(R.id.tvSoDienThoaiConfirm);
        otp= findViewById(R.id.edtOtp);
        guilai=findViewById(R.id.tvGuiLai);
        Bundle b= getIntent().getExtras();
        String sdt=(b.getString("SoDienThoai"));

        SoDienThoai.setText(sdt);
        codeotp=getIntent().getStringExtra("codeotp");

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Vui Lòng Nhập Mã OTP",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(codeotp!= null)
                {
                    btnXacNhan.setVisibility(View.VISIBLE);
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(codeotp,otp.getText().toString());
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            btnXacNhan.setVisibility(View.VISIBLE);
                            if(task.isSuccessful())
                            {
                                Intent intent= new Intent(getApplicationContext(),Home.class);
                                intent.putExtra("SoDienThoai",SoDienThoai.getText().toString());
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"OTP Không Đúng",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        guilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthOptions options;
                options = PhoneAuthOptions.newBuilder(mFirebaseAuth)
                        .setPhoneNumber("+84"+SoDienThoai.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60l, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(Confirm_otp.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                guilai.setVisibility(View.VISIBLE);


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                guilai.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onCodeSent(@NonNull String news,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                guilai.setVisibility(View.VISIBLE);
                               codeotp=news;
                            }
                        } )          // OnVerificationStateChangedCallbacks
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
                updateUI(null);
            }

        });
    }
    private void updateUI(FirebaseUser user) {
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

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