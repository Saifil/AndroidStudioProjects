package com.example.saifil.power;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

public class IndividualProject extends AppCompatActivity implements PaymentResultListener {

    private JSONObject project;

    private ImageView imageView;
    private TextView title_tv, desc_teaser_tv, pledged_tv, hired_txt_tv, desc_tv;
    private TextView owner_tv, days_tv, category_tv, percent_tv, location_tv, readmore_tv;
    private ProgressBar mProgress;
    private Button fund_btn, hire_btn;

    private String image_base_url = Constants.base_image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_project);

        try {
            //get the selected project object
            project = new JSONObject(getIntent().getStringExtra("ProjectObj"));

            imageView = findViewById(R.id.proj_img_id);
            mProgress = findViewById(R.id.percent_comp_id);

            fund_btn = findViewById(R.id.fund_btn_id);
            hire_btn = findViewById(R.id.hire_btn_id);

            title_tv = findViewById(R.id.txt_proj_title_id);
            desc_teaser_tv = findViewById(R.id.desc_teaser_id);
            pledged_tv = findViewById(R.id.pledged_id);
            hired_txt_tv = findViewById(R.id.hired_txt_id);
            desc_tv = findViewById(R.id.project_desc_txt_id);
            owner_tv = findViewById(R.id.txt_owner_id);
            days_tv = findViewById(R.id.txt_days_left_id);
            category_tv = findViewById(R.id.category_txt_id);
            percent_tv = findViewById(R.id.progress_fund_txt_id);
            location_tv  = findViewById(R.id.location_txt_id);
            readmore_tv = findViewById(R.id.readmore_id);

            readmore_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //normal focus on desc section
//                    View targetView = findViewById(R.id.project_desc_txt_id);
//                    targetView.getParent().requestChildFocus(targetView,targetView);

                    //smooth scroll to desc section
                    ScrollView sv = findViewById(R.id.scroll_view_id);
                    View insideView = findViewById(R.id.project_desc_txt_id);
                    sv.smoothScrollTo(0, (int)insideView.getY());
                }
            });

            //type : 0 = hire, 1 = fund, 2 = both
            int type = findType(project);

            //set image
            Glide.with(this).load(image_base_url + project.getString("project_image")).into(imageView);

            //set category
            category_tv.setText(project.getString("project_category"));

            //set title
            title_tv.setText(project.getString("project_title"));

            //set teaser text
            desc_teaser_tv.setText(project.getString("project_description"));

            if (type != 0) {
                int percent = calcPercent(project);

                //set progress bar
                mProgress.setProgress(percent);
                //set % text
                percent_tv.setText(String.valueOf(percent) + "% funded");
                //pledged
                String val = project.getString("project_funds_received") + "\npledged of "
                        + project.getString("project_funds");
                pledged_tv.setText(val);

            } else {
                mProgress.setVisibility(View.GONE); //makes progress bar disappear
                pledged_tv.setVisibility(View.GONE);
                percent_tv.setVisibility(View.GONE);
                fund_btn.setVisibility(View.GONE);
            }
            if (type != 1) {
                String val = project.getString("project_people_hired") + " / "
                        + project.getString("project_people") + "\nwomen hired";
                hired_txt_tv.setText(val);

            } else {
                hired_txt_tv.setVisibility(View.GONE);
                hire_btn.setVisibility(View.GONE);
            }

            //set days to go
//            days_tv.setText(project.getString("project_timestamp"));

            //set location
            String loc = project.getString("project_location") + " | "
                    + project.getString("project_state_location");
            location_tv.setText(loc);

            //set project desc
            desc_tv.setText(project.getString("project_description"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private int findType(JSONObject project) throws JSONException {
        String project_funds = project.getString("project_funds");
        String project_people = project.getString("project_people");

        if (!project_funds.equals("null") && !project_people.equals("null")) {
            return 2;
        } else if (!project_funds.equals("null")) {
            return 1;
        }
        return 0;
    }

    private int calcPercent(JSONObject jsonObject) throws JSONException {
        String project_funds = jsonObject.getString("project_funds");
        String project_people = jsonObject.getString("project_people");

        double percent;
        if (!project_funds.equals("null")) {
            double project_funds_received = Integer.parseInt(jsonObject.getString("project_funds_received"));
            percent = (project_funds_received / Integer.parseInt(project_funds)) * 100;
        } else {
            double project_people_hired = Integer.parseInt(jsonObject.getString("project_people_hired"));
            percent = (project_people_hired / Integer.parseInt(project_people)) * 100;
        }
        return (int)Math.ceil(percent);
    }

    private void startPayment() {
//
//        Checkout checkout = new Checkout();
//
//        //set app logo here
//        checkout.setImage(R.mipmap.ic_launcher);
//
//        //reference to current activity
//        final Activity activity = this;
//
//        //Pass your payment options to the Razorpay Checkout as a JSONObject
//        try {
//            JSONObject options = new JSONObject();
//
//            //Use the same name fields
//            options.put("name", "Merchant Name");
//            options.put("description", "Order #123456");
//            options.put("currency", "INR");
//            options.put("amount", amt * 100);
//
//            checkout.open(activity, options);
//        } catch(Exception e) {
//            Log.e("TAG", "Error in starting Razorpay Checkout", e);
//        }

    }

    @Override
    public void onPaymentSuccess(String s) { //called when payment is successful
        Toast.makeText(this, "Payment was successful", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) { //called when payment fails
        Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
    }
}
