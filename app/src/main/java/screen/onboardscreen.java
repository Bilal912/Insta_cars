package screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.panaceasoft.admotors.MainActivity;
import com.panaceasoft.admotors.R;

public class onboardscreen extends AppCompatActivity {
    ViewPager viewPager;

    SharedPreferences sharedPreferences;
    Boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboardscreen);
        viewPager=findViewById(R.id.viewpager);

        screenadapter screenadapter= new screenadapter(getSupportFragmentManager());
        viewPager.setAdapter(screenadapter);

        sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);

        firstTime = sharedPreferences.getBoolean("firstTime",true);

        if (firstTime){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    firstTime = false;
                    editor.putBoolean("firstTime",firstTime);
                    editor.apply();

        }
        else {
            Intent i  = new Intent(onboardscreen.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
