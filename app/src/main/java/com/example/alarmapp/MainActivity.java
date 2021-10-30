package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.alarmapp.BroadCastReceiver.AlarmReceiver;
import com.example.alarmapp.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        createNotificationChannel();
        binding.selectTimeBtn.setOnClickListener(v -> {
        showTimePicker();
        });
        binding.setAlarmBtn.setOnClickListener(v->{
        setAlarm();
        });
        binding.cancelAlarmBtn.setOnClickListener(v->{
            cancelAlarm();
        });
    }

    private void cancelAlarm() {
        Intent intent=new Intent(this,AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        if (alarmManager==null){
            alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();

    }

    private void setAlarm() {
       alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this, "Alarm Set SuccessFully", Toast.LENGTH_SHORT).show();
    }

    private void showTimePicker() {
        picker=new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();
        picker.show(getSupportFragmentManager(),"AlarmApp");
        picker.addOnPositiveButtonClickListener(v -> {
           if (picker.getHour()>12){
               binding.selectedTime.setText(String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",picker.getMinute())+" PM");

           }else{
               binding.selectedTime.setText(picker.getHour()+" : "+ picker.getMinute() + " AM");
           }
           calendar=Calendar.getInstance();
           calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
           calendar.set(Calendar.MINUTE,picker.getMinute());
           calendar.set(Calendar.SECOND,0);
           calendar.set(Calendar.MILLISECOND,0);

        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="androidReminderChannel";
            String description="Channel For Alarm Manager";
            int importance=NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("AlarmApp",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }
}