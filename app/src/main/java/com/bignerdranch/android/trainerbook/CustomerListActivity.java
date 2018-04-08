package com.bignerdranch.android.trainerbook;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CustomerListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CustomerListFragment();
    }
}
