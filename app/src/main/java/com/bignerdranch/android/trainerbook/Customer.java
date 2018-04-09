package com.bignerdranch.android.trainerbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Customer {
    private UUID mId;
    private String mName;
    private Date mDate;
    private String mBilling;
    //private List<Session> mSessions;

    public Customer() {
        this(UUID.randomUUID());
        //mId = UUID.randomUUID();
        //mDate = new Date();
        /*mSessions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Session session = new Session();
            session.setTitle("Session #" + i);
            session.setComplete(i % 2 == 0);
            mSessions.add(session);
        }*/
    }

    public Customer(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getBilling() {
        return mBilling;
    }

    public void setBilling(String billing) {
        mBilling = billing;
    }

    /*public List<Session> getSessions() {
        return mSessions;
    }

    public Session getSession(UUID id) {
        for (Session session : mSessions) {
            if (session.getId().equals(id)) {
                return session;
            }
        }

        return null;
    }*/
}
