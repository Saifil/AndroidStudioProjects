package com.example.saifil.power;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Button btn;
    private TextView textView;
    private TextView tv;
    private TextView tv_welcome;

    private Button reg_btn;
    private EditText et_aadhaar_num;
    private EditText et_mob_num;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = findViewById(R.id.btn_qr_id);
        reg_btn = findViewById(R.id.btn_signup_id);
        et_aadhaar_num = findViewById(R.id.aadhaar_num_id);
        et_mob_num = findViewById(R.id.mob_num_id);

        tv_welcome = findViewById(R.id.welcoe_txt_id);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiating the qr code scan
                qrScan.initiateScan();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mob = et_mob_num.getText().toString();
                String aadhaar_num = et_aadhaar_num.getText().toString();
                if (!mob.matches("") && !aadhaar_num.matches("")) {
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Enter Aadhaar and Mobile", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    //xml comes here

                    List<String> aadhaarList = new ArrayList<String>();

                    for (int i = 0; i <= result.getContents().length() - 1; i++) {
                        if (result.getContents().charAt(i) == '"') {
                            int j = i + 1;
                            String myString = "";
                            while ((j <= result.getContents().length() - 1) && (result.getContents().charAt(j) != '"')) {
                                myString += result.getContents().charAt(j++);
                            }
                            aadhaarList.add(myString);
                            i = j + 1;
                        }
                    }

                    et_aadhaar_num.setText(aadhaarList.get(2)); //auto fill aadhaar
                    tv_welcome.setText("Welcome, " + aadhaarList.get(3));

                    for (int i = 0; i <= aadhaarList.size() - 1; i++) {
                        Log.i("TAG",String.valueOf(i) + " " + aadhaarList.get(i));
                    }



                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
