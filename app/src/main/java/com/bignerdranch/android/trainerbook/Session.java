package com.bignerdranch.android.trainerbook;

import java.util.Date;
import java.util.UUID;

public class Session {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mComplete;

    public Session() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isComplete() {
        return mComplete;
    }

    public void setComplete(boolean complete) {
        mComplete = complete;
    }
}
