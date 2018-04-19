package com.bignerdranch.android.trainerbook;

import java.util.Date;
import java.util.UUID;

public class Customer {
    private UUID mId;
    private String mName;
    private Date mDate;
    private String mBilling;

    public Customer() {
        this(UUID.randomUUID());
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

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
