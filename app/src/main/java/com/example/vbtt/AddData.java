package com.example.vbtt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddData extends AppCompatActivity {
    private static Button time2, time1;
    private static TextView set_date, set_time;
    int f=0;
    private static final int Time_id1 = 1;
    private static final int Time_id2 = 2;
    EditText et1,et2;
    Button save;
    String src,des,arr,dep;
    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        mDatabaseHelper = new DatabaseHelper(this);
et1 = findViewById(R.id.src);
et2=findViewById(R.id.des);
save = findViewById(R.id.save);
time1 =findViewById(R.id.arrival);
time2=findViewById(R.id.departure);
time1.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f=1;
                showDialog(Time_id1);

            }
        }
);
        time2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        f=2;
                        showDialog(Time_id2);

                    }
                }
        );
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
AddData(et1.getText().toString(),et2.getText().toString(),arr,dep);
Intent in = new Intent(AddData.this,AdminSuccess.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(in);
            }
        });
    }
    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Time_id1:

                // Open the datepicker dialog
                return new TimePickerDialog(AddData.this, time_listener, hour,
                        minute, false);
            case Time_id2:

                // Open the timepicker dialog
                return new TimePickerDialog(AddData.this, time_listener, hour,
                        minute, false);

        }
        return null;
    }
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String timek = String.valueOf(hour) + ":" + String.valueOf(minute);
            //set_time.setText(time1);
            if(f==1) {
                arr = timek.toString();
                time1.setText("Arrival time:" + timek.toString());
            }
            else if(f==2) {
                dep = timek.toString();
                time2.setText("Departure time:" + timek.toString());

            }
        }
    };
    public void AddData(String src,String dest,String ar,String dept) {

        try {
            if (src != null && dest  != null && ar  != null&& dept  != null) {
                boolean insertData = mDatabaseHelper.addData(src,dest,ar,dept);

                if (insertData) {
                    toastMessage("Data Successfully Inserted!");
                } else {
                    toastMessage("Something went wrong");
                }
            }
        }
        catch (Exception e)
        {
            toastMessage(" "+e);
        }
    }
    public void toastMessage(String msg){
        Toast.makeText(this, " "+msg, Toast.LENGTH_SHORT).show();
    }
}