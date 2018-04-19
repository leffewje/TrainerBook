package com.bignerdranch.android.trainerbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReceiptActivity extends AppCompatActivity {
    private Button mEmailButton;
    private TextView mReceiptSessionTitle;
    private TextView mReceiptSessionDate;
    private TextView mReceiptSessionPrice;
    private TextView mReceiptSessionCustomerName;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        mEmailButton = (Button)findViewById(R.id.email);
        mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getReceipt());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.receipt_subject));
                i = Intent.createChooser(i, getString(R.string.chooser_label));
                startActivity(i);
            }
        });

        mReceiptSessionTitle = (TextView)findViewById(R.id.receipt_session_title);
        mReceiptSessionTitle.setText((String)getIntent().getSerializableExtra(SessionFragment.EXTRA_SESSION_TITLE));

        mReceiptSessionCustomerName = (TextView)findViewById(R.id.receipt_session_customer_name);
        mReceiptSessionCustomerName.setText((String)getIntent().getSerializableExtra(SessionFragment.EXTRA_SESSION_CUSTOMER_NAME));

        mReceiptSessionDate = (TextView)findViewById(R.id.receipt_session_date);
        mReceiptSessionDate.setText((String)getIntent().getSerializableExtra(SessionFragment.EXTRA_SESSION_DATE));

        mReceiptSessionPrice = (TextView)findViewById(R.id.receipt_session_price);
        String price = "$";
        price = price.concat((String)getIntent().getSerializableExtra(SessionFragment.EXTRA_SESSION_PRICE));
        mReceiptSessionPrice.setText(price);
    }

    private String getReceipt() {
        String receipt = ((String)getIntent().getSerializableExtra(SessionFragment.EXTRA_SESSION_TITLE));
        receipt = receipt.concat("\n");
        receipt = receipt.concat((String)getIntent().getSerializableExtra(SessionFragment.EXTRA_SESSION_CUSTOMER_NAME));
        receipt = receipt.concat("\n");
        receipt = receipt.concat((String)getIntent().getSerializableExtra(SessionFragment.EXTRA_SESSION_DATE));
        receipt = receipt.concat("\n$");
        receipt = receipt.concat((String)getIntent().getSerializableExtra((SessionFragment.EXTRA_SESSION_PRICE)));
        receipt = receipt.concat("\nPaid");

        return receipt;
    }
}
