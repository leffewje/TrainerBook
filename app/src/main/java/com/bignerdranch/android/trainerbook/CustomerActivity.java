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

public class CustomerActivity extends SingleFragmentActivity {

    private static final String EXTRA_CUSTOMER_ID = "com.bignerdranch.android.TrainerBook.customer_id";

    public static Intent newIntent(Context packageContext, UUID customerId) {
        Intent intent = new Intent(packageContext, CustomerActivity.class);
        intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
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
        UUID customerId = (UUID) getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        return CustomerFragment.newInstance(customerId);
   }

}
