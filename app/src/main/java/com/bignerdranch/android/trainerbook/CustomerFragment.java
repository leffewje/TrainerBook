package com.bignerdranch.android.trainerbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CustomerFragment extends Fragment {
    private Customer mCustomer;
    private EditText mNameField;
    private Button mDateButton;
    private EditText mBilling;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomer = new Customer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer, container, false);

        mNameField = (EditText) v.findViewById(R.id.customer_name);
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomer.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });

        mDateButton = (Button) v.findViewById(R.id.customer_date);
        mDateButton.setText(mCustomer.getDate().toString());
        mDateButton.setEnabled(false);

        mBilling = (EditText) v.findViewById(R.id.billing);
        mBilling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomer.setBilling(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });


        return v;
    }
}
