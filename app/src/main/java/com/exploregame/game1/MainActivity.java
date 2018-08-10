package com.exploregame.game1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        }

    public void begin(View x){
            TextView incorrect=(TextView)findViewById(R.id.textView);
        EditText user=(EditText)findViewById(R.id.editText);
            String value = user.getText().toString();
            if (TextUtils.isEmpty(value)){
                user.setText("");
                incorrect.setText("Invalid username.");

            }else{
                Integer HP=100, mula=0;
                Intent send = new Intent(getApplicationContext(), gamescreen.class);
                send.putExtra("value",value);
                send.putExtra("HP",100);
                send.putExtra("mula",0);
                startActivity(send);
            }
    }
}