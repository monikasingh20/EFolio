package com.deepbench.eventfolio.myproject;

import android.text.format.Time;

import java.util.Date;

/**
 * Created by tarun on 12/11/17.
 */

public class Event {
    private String eventTitle;
    private String venueName;
    private String userName;
    private int minParticipants;
    private String eventDate;
    private String eventStartTime;
    private String eventDescription;
    private int interested;

    public Event(String userName, String title, String venue, int part, String eDate, String eTime, String eDesc) {
        this.userName = userName;
        this.eventTitle = title;
        this.venueName = venue;
        this.eventDate = eDate;
        this.minParticipants = part;
        this.eventStartTime = eTime;
        this.eventDescription = eDesc;
        this.interested = 0;
    }

    public Event() {
        // Empty Constructor
    }

    public String getEventTitle(){
        return this.eventTitle;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getVenueName(){
        return this.venueName;
    }
    public String getEventDescription(){
        return this.eventDescription;
    }
    public int getMinParticipants(){
        return this.minParticipants;
    }
    public String getEventDate(){
        return this.eventDate;
    }
    public String getEventStartTime(){
        return this.eventStartTime;
    }
    public int getInterested(){
        return this.interested;
    }
    public void setInterested(int n){
        this.interested = n;
    }

}
