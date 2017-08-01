/*
Assignment 03
MainActivity.java
Names: Dhenuka Bhargavi Rangam & Casey Setzer
 */

package com.example.ranga.inclass03;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    static Handler handler;
    static String STATUS_UPDATE;
    final static String ALIST="ALIST";
    final static String TLIST="TLIST";
    static ProgressDialog progressDialog;
    SeekBar thrdPswdLen,thrdPswdCnt,AsynPswdLen,AsynPswdCnt;
    TextView thrdPswdLenTxt,thrdPswdCntTxt,AsynPswdLenTxt,AsynPswdCntTxt;
    static int TDPL,TDPC,ASPL,ASPC;
    static int length;
    int prog=0;
    static ArrayList<String> thrdPswdList = new ArrayList<String>();
    static ArrayList<String> AsynPswdList = new ArrayList<String>();
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i=new Intent(MainActivity.this,GeneratePasswordActivity.class);
        thrdPswdLenTxt = (TextView) findViewById(R.id.pswrdLgthThrdTxtID);
        thrdPswdCntTxt = (TextView) findViewById(R.id.passwrdCntThrdID);
        AsynPswdLenTxt = (TextView) findViewById(R.id.pswrdLgthAsyID);
        AsynPswdCntTxt = (TextView) findViewById(R.id.pswrdCntAsyID);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        i.putExtra(TLIST,thrdPswdList);
                        if(prog==TDPC+ASPC){
                            progressDialog.dismiss();
                            Log.d("demo","dismissed");
                            startActivity(i);
                        }
                        break;
                }
                return false;
            }

        });

        thrdPswdLen = (SeekBar) findViewById(R.id.seekBarPLThrd);
        thrdPswdLen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TDPL = i+7;
                length = TDPL;
                String txt = String.valueOf(TDPL);
                thrdPswdLenTxt.setText(txt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }});

        thrdPswdCnt = (SeekBar) findViewById(R.id.seekBarPCThrd);
        thrdPswdCnt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TDPC = i+1;
                length = TDPC;
                String txt = String.valueOf(TDPC);
                thrdPswdCntTxt.setText(txt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }});

        AsynPswdLen = (SeekBar) findViewById(R.id.seekBarPLAs);
        AsynPswdLen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ASPL = i+7;
                length = ASPL;
                String txt = String.valueOf(ASPL);
                AsynPswdLenTxt.setText(txt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }});
        AsynPswdCnt = (SeekBar) findViewById(R.id.seekBarPCAs);
        AsynPswdCnt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ASPC = i+1;
                length = ASPC;
                String txt = String.valueOf(ASPC);
                AsynPswdCntTxt.setText(txt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }});



    }

    public void generate(View view) {

        ExecutorService taskpool = Executors.newFixedThreadPool(2);
        taskpool.execute(new ThreadPool(TDPL,TDPC));
        new Async().execute(ASPC,ASPL);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Generating Passwords");
        progressDialog.setMax(TDPC+ASPC);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();



    }
    class ThreadPool implements Runnable{

        int length,count;

        public ThreadPool(int length, int count) {
            this.length = length;
            this.count = count;
        }

        @Override
        public void run() {
            Message msg=new Message();
            for (int i=0;i<count;i++){
                thrdPswdList.add(Util.getPassword(length));
                prog++;
                progressDialog.setProgress(prog);
            }
            msg.what=1;
            handler.sendMessage(msg);

        }
    }

    class Async extends AsyncTask<Integer,Integer,ArrayList<String>>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<String> arrayList) {
            super.onPostExecute(arrayList);
            i.putExtra(ALIST,AsynPswdList);
            if(prog==TDPC+ASPC){
                progressDialog.dismiss();
                Log.d("demo","dismiss");
                startActivity(i);

            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(prog);
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... integer) {
            for (int i = 0; i < integer[0]; i++) {
                AsynPswdList.add(Util.getPassword(integer[1]));
                prog++;
                publishProgress(prog);
               // return AsynPswdList;
            }
            return  AsynPswdList;
        }
    }


}
