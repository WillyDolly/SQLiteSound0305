package com.khoaphamtraining.khoapham.sqlitesound0305;


import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

   
 Button btnGhi, btnDung, btnPhat, btnDungPhat;
    
private MediaRecorder myRecorder;
   
 private MediaPlayer myPlayer;
   
 private String outputFile = null;

   
 EditText edtTenBH, edtMoTa;
    
Button btnLuu;

  
  @Override
    protected void onCreate(Bundle savedInstanceState) {
       
 super.onCreate(savedInstanceState);
       
 setContentView(R.layout.activity_main);

      
  AnhXa();

      
  final SQLite db = new SQLite(this, "Nhac.sqlite", null, 1);

        
db.QueryData("CREATE TABLE IF NOT EXISTS BaiHat(id INTEGER PRIMARY KEY AUTOINCREMENT, TenBH VARCHAR, MoTa VARCHAR, FileGhiAm BLOB)");


    

    btnLuu.setOnClickListener(new View.OnClickListener() {
           
 @Override
            public void onClick(View v) {
                
String tenBH = edtTenBH.getText().toString();
                
String moTa = edtMoTa.getText().toString();

                // chuyen file am thanh -> byte[]
                // ham FileLocal_To_Byte() lay tu Snippet Android
                db.INSERT_GHIAM(tenBH, moTa, FileLocal_To_Byte(outputFile));

               
 Toast.makeText(MainActivity.this, "Luu thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

     

   btnGhi.setOnClickListener(new View.OnClickListener() {
         
   @Override
            public void onClick(View v) {

              
  outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/khoaphamvn.3gpp";
         
       myRecorder = new MediaRecorder();
           
     myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
           
     myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
           
     myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
              
      myRecorder.setOutputFile(outputFile);

             
      start(v);
            }
        });

       
 btnDung.setOnClickListener(new View.OnClickListener() {
            
@Override
            public void onClick(View v) {
                stop(v);
            }
        });

      
  btnPhat.setOnClickListener(new View.OnClickListener() {
          
  @Override
            public void onClick(View v) {
                play(v);
            }
        });

       
 btnDungPhat.setOnClickListener(new View.OnClickListener() {
           
 @Override
            public void onClick(View v) {
                stopPlay(v);
            }
        });

    }

   
 public byte[] FileLocal_To_Byte(String path){
      
  File file = new File(path);
     
   int size = (int) file.length();
    
    byte[] bytes = new byte[size];
        
try {
         
   BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
           
 buf.read(bytes, 0, bytes.length);
        
    buf.close();
        }
 catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        
    e.printStackTrace();
        } 
catch (IOException e) {
            // TODO Auto-generated catch block
           
 e.printStackTrace();
        }
       
 return bytes;
    }

   
 
public void start(View view){
       
 try {
            myRecorder.prepare();
          
  myRecorder.start();
        }
 catch (IllegalStateException e) {
            e.printStackTrace();
        }
 catch (IOException e) {
            e.printStackTrace();
        }
        
Toast.makeText(getApplicationContext(), "Start recording...",
                Toast.LENGTH_SHORT).show();
    }

   

 public void stop(View view){
        try {
            myRecorder.stop();
            myRecorder.release();
          
  myRecorder  = null;

           
 Toast.makeText(getApplicationContext(), "Stop recording...",
                    Toast.LENGTH_SHORT).show();
        } 
catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

   

 public void play(View view) {
        try{
           
 myPlayer = new MediaPlayer();
           
 myPlayer.setDataSource(outputFile);
         
  myPlayer.prepare();
           
 myPlayer.start();

           
 Toast.makeText(getApplicationContext(), "Start play the recording...",Toast.LENGTH_SHORT).show();
        } 
catch (Exception e) {
            e.printStackTrace();
        }
    }



   

 public void stopPlay(View view) {
        try {
          
  if (myPlayer != null) {
              
  myPlayer.stop();
               
 myPlayer.release();
             
   myPlayer = null;

              
  Toast.makeText(getApplicationContext(), "Stop playing the recording...", Toast.LENGTH_SHORT).show();
            }
        }
 catch (Exception e) {
            e.printStackTrace();
        }
    }


   

 private void AnhXa(){
        btnGhi = (Button) findViewById(R.id.buttonGhi);
        btnDung = (Button) findViewById(R.id.buttonDung);
        btnPhat = (Button) findViewById(R.id.buttonPhat);
        btnDungPhat = (Button) findViewById(R.id.buttonDungPhat);
        edtMoTa = (EditText) findViewById(R.id.editTextMoTa);
        edtTenBH = (EditText) findViewById(R.id.editTextTenBH);
        btnLuu = (Button) findViewById(R.id.buttonLuu);
    }
}
