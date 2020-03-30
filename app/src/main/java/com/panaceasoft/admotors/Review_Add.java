package com.panaceasoft.admotors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panaceasoft.admotors.ui.item.detail.ItemActivity;
import com.panaceasoft.admotors.ui.item.detail.ItemFragment;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import screen.Review_adapter;
import screen.Review_model;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class Review_Add extends AppCompatActivity {
    TextView tv_date;
    CharSequence s;
    EditText ed_msg;
    public String message,user_id,item_id;
    Button Submit;

    ImageView imgstar1,imgstar2,imgstar3,imgstar4,imgstar5;
    public String stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review__add);

        tv_date=findViewById(R.id.date_review);
        imgstar1=findViewById(R.id.img_star1);
        imgstar2=findViewById(R.id.img_star2);
        imgstar3=findViewById(R.id.img_star3);
        imgstar4=findViewById(R.id.img_star4);
        imgstar5=findViewById(R.id.img_star5);
        ed_msg=findViewById(R.id.ed_message);
        Submit=findViewById(R.id.btn_submit);


        Date d = new Date();
        s = DateFormat.format("yyyy-MM-dd", d.getTime());
        tv_date.setText(s+"");

        imgstar1.setOnClickListener(view -> {

            stars="1";
            imgstar1.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar2.setColorFilter(getResources().getColor(R.color.md_grey_400));
            imgstar3.setColorFilter(getResources().getColor(R.color.md_grey_400));
            imgstar4.setColorFilter(getResources().getColor(R.color.md_grey_400));
            imgstar5.setColorFilter(getResources().getColor(R.color.md_grey_400));

        });
        imgstar2.setOnClickListener(view -> {
            stars="2";
            imgstar1.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar2.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar3.setColorFilter(getResources().getColor(R.color.md_grey_400));
            imgstar4.setColorFilter(getResources().getColor(R.color.md_grey_400));
            imgstar5.setColorFilter(getResources().getColor(R.color.md_grey_400));
        });
        imgstar3.setOnClickListener(view -> {
            stars="3";
            imgstar1.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar2.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar3.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar4.setColorFilter(getResources().getColor(R.color.md_grey_400));
            imgstar5.setColorFilter(getResources().getColor(R.color.md_grey_400));
        });
        imgstar4.setOnClickListener(view -> {
            stars="4";
            imgstar1.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar2.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar3.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar4.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar5.setColorFilter(getResources().getColor(R.color.md_grey_400));
        });
        imgstar5.setOnClickListener(view -> {
            stars="5";
            imgstar1.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar2.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar3.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar4.setColorFilter(getResources().getColor(R.color.colorPromary));
            imgstar5.setColorFilter(getResources().getColor(R.color.colorPromary));
        });




    user_id=getIntent().getStringExtra("USER_ID");
    item_id=getIntent().getStringExtra("ITEM_id");

    Submit.setOnClickListener(view -> {
        message=ed_msg.getText().toString();
        getData1(item_id,user_id,message,stars,s.toString());



    });




    }
    private void getData1(String car_id,String user_id,String message,String star,String date) {


        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, "http://zahoortravels.bazar.com.pk/index.php/main/add_feedback?date="+date+"&star="+star+"&message="+message+"" +
                "&car_id="+car_id+"&user_id="+user_id+"", response -> {
                    Boolean status = null;

                    try {

                        status=response.getBoolean("response");
                        String msg=response.getString("data");
                          makeText(this, msg, LENGTH_SHORT).show();
                          Intent i=new Intent(Review_Add.this, ItemActivity.class);
                          startActivity(i);





                    }
                    catch (Exception e){

                        makeText(this,"No Data Found", LENGTH_SHORT).show();
                    }

                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               Toast.makeText(Review_Add.this, "Connection Error", LENGTH_LONG).show();
            }
        });

        jsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonRequest);
    }
}
