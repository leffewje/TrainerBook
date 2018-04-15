package com.bignerdranch.android.trainerbook.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.trainerbook.Session;
import com.bignerdranch.android.trainerbook.database.CustomerDbSchema.SessionTable;

import java.util.Date;
import java.util.UUID;

public class SessionCursorWrapper extends CursorWrapper {
    public SessionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Session getSession() {
        String uuidString = getString(getColumnIndex(SessionTable.Cols.UUID));
        String title = getString(getColumnIndex(SessionTable.Cols.TITLE));
        long date = getLong(getColumnIndex(SessionTable.Cols.DATE));
        int isCompleted = getInt(getColumnIndex(SessionTable.Cols.COMPLETED));
        String sign = getString(getColumnIndex(SessionTable.Cols.SIGN));
        String sessionCustomerId = getString(getColumnIndex(SessionTable.Cols.SESSION_CUSTOMER_ID));
        String price = getString(getColumnIndex(SessionTable.Cols.PRICE));
        int isPaid = getInt(getColumnIndex(SessionTable.Cols.PAID));

        Session session = new Session(UUID.fromString(uuidString), UUID.fromString(sessionCustomerId));
        session.setTitle(title);
        session.setDate(new Date(date));
        session.setComplete(isCompleted != 0);
        session.setSign(sign);
        session.setPrice(price);
        session.setPaid(isPaid != 0);

        return session;
    }
}
