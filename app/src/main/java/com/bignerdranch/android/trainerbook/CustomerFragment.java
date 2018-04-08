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

import java.util.UUID;

public class CustomerFragment extends Fragment {
    private static final String ARG_CUSTOMER_ID = "customer_id";

    private Customer mCustomer;
    private EditText mNameField;
    private Button mDateButton;
    private EditText mBilling;

    public static CustomerFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        CustomerFragment fragment = new CustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID customerId = (UUID) getArguments().getSerializable(ARG_CUSTOMER_ID);
        mCustomer = CustomerGroup.get(getActivity()).getCustomer(customerId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer, container, false);

        mNameField = (EditText) v.findViewById(R.id.customer_name);
        mNameField.setText(mCustomer.getName());
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
        mBilling.setText(mCustomer.getBilling());
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
