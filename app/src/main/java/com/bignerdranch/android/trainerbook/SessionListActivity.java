package com.bignerdranch.android.trainerbook;

import android.support.v4.app.Fragment;

public class SessionListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SessionListFragment();
    }
}
