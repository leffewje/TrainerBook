package com.bignerdranch.android.trainerbook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.UUID;


public class SessionActivity extends SingleFragmentActivity {

    private static final String EXTRA_SESSION_ID = "com.bignerdranch.android.TrainerBook.session_id";

    public static Intent newIntent(Context packageContext, UUID sessionId) {
        Intent intent = new Intent(packageContext, SessionActivity.class);
        intent.putExtra(EXTRA_SESSION_ID, sessionId);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoff:
                LogoffDialog logoffDialog = new LogoffDialog();
                logoffDialog.show(getSupportFragmentManager(), "Logging Off");
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Fragment createFragment() {
        UUID sessionId = (UUID) getIntent().getSerializableExtra(EXTRA_SESSION_ID);
        return SessionFragment.newInstance(sessionId);
    }

}
