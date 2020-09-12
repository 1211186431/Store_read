package com.example.store_read;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    //SharedPreferences文件
    private final static String SharedPreferencesFileName="config";
    private final static String Key_UserName="UserName";//用户名
    private final static String Key_UserNum="UserNum";//num
    private final static String MyFileName="data.txt";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获得SharedPreferences实例
        preferences=getSharedPreferences(SharedPreferencesFileName,
        MODE_PRIVATE);
        editor=preferences.edit();
        Button btnstore1=(Button)findViewById(R.id.store);
        Button btnread1=(Button)findViewById(R.id.read);
        Button btnstore2=(Button) findViewById(R.id.store2);
        Button btnread2=(Button)findViewById(R.id.read2);
        btnstore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name=(EditText) findViewById(R.id.nameInput);
                final String  UserName=name.getText().toString();
                EditText Num=(EditText) findViewById(R.id.numInput);
                final String  UserNum=Num.getText().toString();
                editor.putString(Key_UserName,UserName);
                editor.putString(Key_UserNum,UserNum);
                editor.apply();
            }
        });
        btnread1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName=preferences.getString(Key_UserName,null);
                String strNum=preferences.getString(Key_UserNum,null);
                if(strName!=null&&strNum!=null&&!strName.equals("")&&!strNum.equals("")){
                    Toast.makeText(MainActivity.this,"姓名："+strName+" 学号："+strNum,Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(MainActivity.this,"无数据",Toast.LENGTH_LONG).show();
            }
        });
        btnstore2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                OutputStream out=null;
                try {
                    FileOutputStream fileout=openFileOutput(MyFileName,MODE_PRIVATE);
                    out=new BufferedOutputStream(fileout);
                    EditText name=(EditText) findViewById(R.id.nameInput);
                    final String  UserName=name.getText().toString();
                    EditText Num=(EditText) findViewById(R.id.numInput);
                    final String  UserNum=Num.getText().toString();
                    String w="name:"+UserName+" num:"+UserNum;
                    try {
                        out.write(w.getBytes(StandardCharsets.UTF_8));
                    }
                    finally{
                        if (out != null)
                            out.close();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        btnread2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream in=null;
                try {
                    FileInputStream fileInputStream = openFileInput(MyFileName);
                    in=new BufferedInputStream(fileInputStream);
                    int c;
                    StringBuilder stringBuilder=new StringBuilder("");
                    try{
                        while ((c=in.read())!=-1) {
                            stringBuilder.append((char)c);
                        }
                        Toast.makeText(MainActivity.this,stringBuilder.toString(),Toast.LENGTH_LONG).show();
                    }
                    finally {
                        if(in!=null)
                            in.close();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}