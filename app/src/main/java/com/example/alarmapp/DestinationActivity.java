package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.alarmapp.databinding.ActivityDestinationBinding;
import com.example.alarmapp.databinding.ActivityMainBinding;

public class DestinationActivity extends AppCompatActivity {
    ActivityDestinationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_destination);

    }
}