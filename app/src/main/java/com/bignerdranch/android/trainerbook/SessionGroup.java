package com.bignerdranch.android.trainerbook;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SessionGroup {
    private static SessionGroup sSessionGroup;

    private List<Session> mSessions;

    public static SessionGroup get(Context context) {
        if (sSessionGroup == null) {
            sSessionGroup = new SessionGroup(context);
        }
        return sSessionGroup;
    }

    private SessionGroup(Context context) {
        mSessions = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Session session = new Session();
            session.setTitle("Session #" + i);
            session.setComplete(i % 2 == 0);
            mSessions.add(session);
        }
    }

    public void addSession(Session s){
        mSessions.add(s);
    }

    public List<Session> getSessions() {
        return mSessions;
    }

    public Session getSession(UUID id) {
        for (Session session : mSessions) {
            if (session.getId().equals(id)){
                return session;
            }
        }

        return null;
    }
}
