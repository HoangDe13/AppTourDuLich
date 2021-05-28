package com.example.apptourdulich;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

public class NetworkChangeListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Common.IsConnectedToInternet(context)){
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            View layout_dialog= LayoutInflater.from(context).inflate(R.layout.check_internet,null);
            builder.setView(layout_dialog);
            Button button=layout_dialog.findViewById(R.id.btnRetry);
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    onReceive(context,intent);
                }
            });
        }
    }
}
