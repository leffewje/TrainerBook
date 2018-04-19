package com.bignerdranch.android.trainerbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.trainerbook.database.CustomerBaseHelper;
import com.bignerdranch.android.trainerbook.database.CustomerDbSchema.SessionTable;
import com.bignerdranch.android.trainerbook.database.SessionCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SessionGroup {
    private static SessionGroup sSessionGroup;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static SessionGroup get(Context context) {
        if (sSessionGroup == null) {
            sSessionGroup = new SessionGroup(context);
        }
        return sSessionGroup;
    }

    private SessionGroup(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CustomerBaseHelper(mContext).getWritableDatabase();
    }

    public void addSession(Session s){
        ContentValues values = getContentValues(s);

        mDatabase.insert(SessionTable.NAME, null, values);
    }

    public List<Session> getSessions() {
        List<Session> sessions = new ArrayList<>();

        SessionCursorWrapper cursor = querySessions(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                sessions.add(cursor.getSession());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return sessions;
    }

    public Session getSession(UUID id) {
        SessionCursorWrapper cursor = querySessions(
                SessionTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSession();
        } finally {
            cursor.close();
        }
    }

    public void updateSession(Session session) {
        String uuidString = session.getId().toString();
        ContentValues values = getContentValues(session);

        mDatabase.update(SessionTable.NAME, values, SessionTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private SessionCursorWrapper querySessions(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SessionTable.NAME,
                null,   // columns - null selects all columns
                whereClause,
                whereArgs,
                null,   // groupBy
                null,   // having
                null    //orderBy
        );

        return new SessionCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Session session) {
        ContentValues values = new ContentValues();
        values.put(SessionTable.Cols.UUID, session.getId().toString());
        values.put(SessionTable.Cols.TITLE, session.getTitle());
        values.put(SessionTable.Cols.DATE, session.getDate().getTime());
        values.put(SessionTable.Cols.COMPLETED, session.isComplete() ? 1 : 0);
        values.put(SessionTable.Cols.SIGN, session.getSign());
        values.put(SessionTable.Cols.SESSION_CUSTOMER_ID, session.getSessionCustomerId().toString());
        values.put(SessionTable.Cols.PRICE, session.getPrice());
        values.put(SessionTable.Cols.PAID, session.isPaid() ? 1 : 0);

        return values;
    }
}
