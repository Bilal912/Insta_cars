package com.panaceasoft.admotors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpScreen extends AppCompatActivity {
    TextView txt,btn;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        txt=findViewById(R.id.txt1);
        img=findViewById(R.id.img);
        String text ="By clicking 'Sign Up' you are agree to our terms and conditions as well as our privacy policy";
        SpannableString ss = new SpannableString(text);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpScreen.this, TestScreen.class));
            }
        });
        btn=findViewById(R.id.btnlogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpScreen.this, TestScreen.class);
                startActivity(intent);
            }
        });

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(SignUpScreen.this, "one",Toast.LENGTH_SHORT).show();
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(SignUpScreen.this, "two",Toast.LENGTH_SHORT).show();
            }
        };
        ss.setSpan(clickableSpan1,43,63, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2,79,93, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txt.setText(ss);
        txt.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
