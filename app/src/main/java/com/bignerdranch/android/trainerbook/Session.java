package com.bignerdranch.android.trainerbook;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Session {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mComplete;
    private String mSign;
    private UUID mSessionCustomerId;
    private String mPrice;
    private boolean mPaid;

    public Session(UUID sessionCustomerId) {
        this(UUID.randomUUID(), sessionCustomerId);
        //mId = UUID.randomUUID();
        //mDate = new Date();
    }

    public Session(UUID id, UUID sessionCustomerId) {
        mId = id;
        //mId = UUID.randomUUID();
        mDate = new Date();
        mSessionCustomerId = sessionCustomerId;
        Random rand = new Random();
        Double price = 1 + 99*rand.nextDouble();
        mPrice = String.format("%.2f", price);
        mPaid = false;
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

    public String getSign() {
        return mSign;
    }

    public void setSign(String sign) {
        mSign = sign;
    }

    public UUID getSessionCustomerId() {
        return mSessionCustomerId;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public boolean isPaid() {
        return mPaid;
    }

    public void setPaid(boolean paid) {
        mPaid = paid;
    }
}
