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

        Session session = new Session(UUID.fromString(uuidString));
        session.setTitle(title);
        session.setDate(new Date(date));
        session.setComplete(isCompleted != 0);
        session.setSign(sign);

        return session;
    }
}
