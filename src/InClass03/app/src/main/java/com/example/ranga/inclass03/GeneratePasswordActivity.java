package com.example.ranga.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class GeneratePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_password);
        //create a new Intent to receive the arrays of passwords
        Intent intent = getIntent();
        ArrayList<String> threadResults = intent.getStringArrayListExtra(MainActivity.TLIST);
        ArrayList<String> asyncResults = intent.getStringArrayListExtra(MainActivity.ALIST);

        ScrollView threadResultScrollView = (ScrollView) this.findViewById(R.id.threadResultsScrollView);
        LinearLayout threadResultLinearLayout = (LinearLayout) findViewById(R.id.threadScroll);

            //loop through each string in threadResults
            for (int i=0; i<threadResults.size(); i++) {
                //create a new textView for each string in the array
                TextView textView = new TextView(this);

                //set the text of the textView to the string at point i in threadResults
                textView.setText(threadResults.get(i));

                //add this textView to the threadResultScrollView
                threadResultLinearLayout.addView(textView);
            }

        ScrollView asyncTaskResultScrollView = (ScrollView) this.findViewById(R.id.asyncTaskResultScrollView);
        LinearLayout asyncTaskResultLinearLayout = (LinearLayout) findViewById(R.id.asyncScroll);

            //loop through each string in threadResults
            for (int i=0; i<asyncResults.size(); i++) {
                //create a new textView for each string in the array
                TextView textView = new TextView(this);

                //set the text of the textView to the string at point i in threadResults
                textView.setText(asyncResults.get(i));

                //add this textView to the threadResultScrollView
                asyncTaskResultLinearLayout.addView(textView);
            }

    }
}
