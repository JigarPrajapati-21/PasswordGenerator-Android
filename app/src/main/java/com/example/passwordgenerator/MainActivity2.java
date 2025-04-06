package com.example.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    TextView savePasswordtxt;
    Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        savePasswordtxt=findViewById(R.id.savePasswordtxt);
        deleteButton=findViewById(R.id.deleteButton);

//
//        SharedPreferences sharedPreferences=getSharedPreferences("saved_passwords", Context.MODE_PRIVATE);
//
//        savePasswordtxt.setText(sharedPreferences.getAll().toString());


        SharedPreferences sharedPreferences = getSharedPreferences("saved_passwords", Context.MODE_PRIVATE);

        Map getallpassword=sharedPreferences.getAll();
//        savePasswordtxt.setText(getallpassword.toString());
        StringBuilder formattedOutput = new StringBuilder();
        getallpassword.forEach((k,v)->formattedOutput.append("\nDate : "+ formateDateTime(k)).append("\npassword : "+v +"\n"));
        savePasswordtxt.setText(formattedOutput.toString());

        //
//// Get all saved passwords
//        Map<String, ?> allPasswords = sharedPreferences.getAll();
//
//// Create a StringBuilder to format the output
//        StringBuilder formattedOutput = new StringBuilder();
//
//        for (Map.Entry<String, ?> entry : allPasswords.entrySet()) {
//            String timestamp = entry.getKey();
//            String password = (String) entry.getValue();
//
//            // Format the output for each password
//            formattedOutput.append("Saved on: ")
//                    .append(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(Long.parseLong(timestamp))))
//                    .append("\nPassword: ")
//                    .append(password)
//                    .append("\n\n");  // Add a blank line between each entry
//        }
//
//// Set the formatted output to the TextView
//        savePasswordtxt.setText(formattedOutput.toString());


        deleteButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            savePasswordtxt.setText("No saved password");
            Toast.makeText(MainActivity2.this,"All passwords deleted" ,Toast.LENGTH_LONG).show();
        });




    }

    private Object formateDateTime(Object k) {
       // return new Date((Long) k).toString();
       // return new Date(Long.parseLong((String) k)).toString();
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault())
                .format(new Date(Long.parseLong((String) k)));
    }
}

