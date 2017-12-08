package com.deepbench.eventfolio.myproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEvent extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    TextView eventDateTextView;
    TextView eventTimeTextView;
    int year_n, month_n, day_n;
    int hour_n, min_n;
    String timeFormat;
    Calendar mCurrentTimeStamp;
    Button mAddEventButton;
    Button mCancelButton;
    EditText newEventName;
    EditText mVenueName;
    EditText participants;
    TextView eventDate;
    TextView eventTime;
    EditText eventDescription;
    String USER_ID;
    String USER_NAME;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");
        mFirebaseAuth = FirebaseAuth.getInstance();

        eventDateTextView = (TextView) findViewById(R.id.newEventDate);
        eventTimeTextView = (TextView) findViewById(R.id.newEventTime);
        mAddEventButton = (Button) findViewById(R.id.newEventCreateButton);
        mCancelButton = (Button) findViewById(R.id.newEventCancelButton);
        newEventName = (EditText) findViewById(R.id.newEventName);
        mVenueName = (EditText) findViewById(R.id.newEventVenue);
        participants = (EditText) findViewById(R.id.newEventParticipants);
        eventDate = (TextView) findViewById(R.id.newEventDate);
        eventTime = (TextView) findViewById(R.id.newEventTime);
        eventDescription = (EditText) findViewById(R.id.newEventDescription);

        USER_ID = "tab3105";
        USER_NAME = "Anonymous";
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if( user != null) {
            // User is signed in
            USER_NAME = user.getDisplayName();
        }

        mCurrentTimeStamp = Calendar.getInstance();
        day_n = mCurrentTimeStamp.get(Calendar.DAY_OF_MONTH);
        month_n = mCurrentTimeStamp.get(Calendar.MONTH);
        year_n = mCurrentTimeStamp.get(Calendar.YEAR);
        hour_n =  mCurrentTimeStamp.get(Calendar.HOUR_OF_DAY);
        min_n = mCurrentTimeStamp.get(Calendar.MINUTE);

        month_n = month_n + 1;

        eventDateTextView.setText(day_n+"/"+month_n+"/"+year_n);
        eventDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        year_n = year;
                        month_n = monthOfYear;
                        day_n = dayOfMonth;
                        eventDateTextView.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                    }
                },year_n, month_n, day_n);
                datePickerDialog.show();
            }
        });

        selectedTimeFormat(hour_n);
        eventTimeTextView.setText(hour_n+":"+min_n+" "+timeFormat);
        eventTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        hour_n = hourOfDay;
                        min_n = minute;
                        eventTimeTextView.setText(hourOfDay+":"+minute+" "+timeFormat);
                    }
                },hour_n, min_n, true);
                timePickerDialog.show();
            }
        });

        mAddEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = new Event( USER_NAME, newEventName.getText().toString(), mVenueName.getText().toString(),
                        Integer.parseInt(participants.getText().toString()), eventDate.getText().toString(), eventTime.getText().toString(), eventDescription.getText().toString());

                mEventsDatabaseReference.push().setValue(event);

                Toast.makeText(CreateEvent.this, "Event Added Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CreateEvent.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvent.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Are you sure?");
                builder.setIcon(R.drawable.lights);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent intent = new Intent(CreateEvent.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    public int selectedTimeFormat(int hour) {
        if(hour == 0) {
            hour += 12;
            timeFormat = "AM";
        } else if(hour < 12) {
            timeFormat = "AM";
        } else if(hour == 12) {
            timeFormat = "PM";
        } else {
            hour -= 12;
            timeFormat = "PM";
        }
        return hour;
    }

}
