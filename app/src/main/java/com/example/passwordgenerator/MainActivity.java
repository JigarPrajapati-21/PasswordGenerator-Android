package com.example.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText generatedPassword;
    SeekBar passwordLengthSeekBar;
    TextView passwordLengthtxt,statustxt;
    CheckBox incNumber,incUpperCase,incLowerCase,incSymbols;//,incSpace;
    Button generatePasswordBtn,savePasswordBtn,viewSavePasswordBtn;

    int passwordlength=8;
    String password;
    StringBuilder finalPassword=new StringBuilder();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generatedPassword=findViewById(R.id.generatedPassword);
        passwordLengthSeekBar=findViewById(R.id.passwordLengthSeekBar);
        passwordLengthtxt=findViewById(R.id.passwordLengthtxt);
        statustxt=findViewById(R.id.statustxt);
        incLowerCase=findViewById(R.id.incLowerCase);
       // incSpace=findViewById(R.id.incSpace);
        incNumber=findViewById(R.id.incNumber);
        incUpperCase=findViewById(R.id.incUpperCase);
        incSymbols=findViewById(R.id.incSymbols);

        generatePasswordBtn=findViewById(R.id.generatePasswordBtn);
        savePasswordBtn=findViewById(R.id.savePasswordBtn);
        viewSavePasswordBtn=findViewById(R.id.viewSavePasswordBtn);

        passwordLengthSeekBar.setProgress(8);
        passwordLengthtxt.setTextColor(Color.BLUE);
        statustxt.setTextColor(Color.BLUE);

        passwordLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passwordlength=progress;//+8;
                passwordLengthtxt.setText("Password Length : "+passwordlength);

                if(progress<=4)
                {
                    statustxt.setText("Very Weak");
                    statustxt.setTextColor(Color.RED);
                }
                if(progress>=5 && progress<=7)
                {
                    statustxt.setText("Weak");
                    statustxt.setTextColor(Color.RED);
                }
                if(progress>=8 && progress<=9)
                {
                    statustxt.setText("Good");
                    statustxt.setTextColor(Color.BLUE);
                }
                if(progress>=10 && progress<=11)
                {
                    statustxt.setText("Strong");
                    statustxt.setTextColor(Color.BLUE);
                }
                if(progress>=12)
                {
                    statustxt.setText("Very Strong");
                    statustxt.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        //generate password btn
        generatePasswordBtn.setOnClickListener(v ->{
            if(!incNumber.isChecked()&&!incLowerCase.isChecked()&&!incUpperCase.isChecked()&&!incSymbols.isChecked())
            {
                Toast.makeText(MainActivity.this,"Pls select atleast one option" ,Toast.LENGTH_LONG).show();
            }else{
                password=generatePassword();
                generatedPassword.setText(password);
            }

        });


        //save password btn
        savePasswordBtn.setOnClickListener(v -> {
            String passwordValue=generatedPassword.getText().toString();

            if(passwordValue.isEmpty()){
                Toast.makeText(MainActivity.this,"Please Generate Password" ,Toast.LENGTH_LONG).show();

            }else{
                String passwordKey=String.valueOf(System.currentTimeMillis());

                SharedPreferences sharedPreferences=getSharedPreferences("saved_passwords", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();

                editor.putString(passwordKey,passwordValue);

                editor.apply();

                Toast.makeText(MainActivity.this,"Saved" ,Toast.LENGTH_LONG).show();

            }

        });


        viewSavePasswordBtn.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);
        });



    }

    private String generatePassword() {

        finalPassword.setLength(0);

        String lowercases="abcdefghijklmnopqrstuvwxyz";
        String uppercases="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers="0123456789";
        String symbols="!@#$%^&*()-_=+<>/?[]{}~|\\.,";
       // String space=" ";

        String useCharacters="";

        List<Character> password=new ArrayList<>();
        Random random=new Random();

        if(incNumber.isChecked())
        {
            password.add(numbers.charAt(random.nextInt(numbers.length())));
            useCharacters+=numbers;
        }
        if(incUpperCase.isChecked())
        {
            password.add(uppercases.charAt(random.nextInt(uppercases.length())));
            useCharacters+=uppercases;
        }
        if(incLowerCase.isChecked())
        {
            password.add(lowercases.charAt(random.nextInt(lowercases.length())));
            useCharacters+=lowercases;
        }
        if(incSymbols.isChecked())
        {
            password.add(symbols.charAt(random.nextInt(symbols.length())));
            useCharacters+=symbols;
        }
//        if(incSpace.isChecked())
//        {
//            password.add(space.charAt(0));
//            useCharacters+=space;
//        }




        while (password.size()<passwordlength)
        {
            int index=random.nextInt(useCharacters.length());
            password.add(useCharacters.charAt(index));
        }

        Collections.shuffle(password);


        for(int i=0;i<passwordlength;i++)
        {
            finalPassword.append(password.get(i));
        }

        return finalPassword.toString();
    }
}