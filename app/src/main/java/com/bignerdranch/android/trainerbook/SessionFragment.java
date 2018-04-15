package com.bignerdranch.android.trainerbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import static android.widget.CompoundButton.*;

public class SessionFragment extends Fragment {
    private static final String ARG_SESSION_ID = "session_id";
    public static final String EXTRA_SESSION_TITLE = "com.bignerdranch.android.TrainerBook.session_title";
    public static final String EXTRA_SESSION_DATE = "com.bignerdranch.android.TrainerBook.session_date";
    public static final String EXTRA_SESSION_PRICE = "com.bignerdranch.android.TrainerBook.session_price";

    private Session mSession;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mCompletedCheckBox;
    private EditText mSignField;
    private Button mReceiptButton;
    private Button mPayButton;
    private Button mSaveSession;

    public static SessionFragment newInstance(UUID sessionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SESSION_ID, sessionId);

        SessionFragment fragment = new SessionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID sessionId = (UUID) getArguments().getSerializable(ARG_SESSION_ID);
        mSession = SessionGroup.get(getActivity()).getSession(sessionId);
    }

    @Override
    public void onPause() {
        super.onPause();

        SessionGroup.get(getActivity()).updateSession(mSession);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session, container, false);

        mTitleField = (EditText) v.findViewById(R.id.session_name);
        mTitleField.setText(mSession.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSession.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });

        mDateButton = (Button) v.findViewById(R.id.session_date);
        mDateButton.setText(mSession.getDate().toString());
        mDateButton.setEnabled(false);

        mCompletedCheckBox = (CheckBox)v.findViewById(R.id.session_complete);
        mCompletedCheckBox.setChecked(mSession.isComplete());
        mCompletedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSession.setComplete(isChecked);
            }
        });

        mSignField = (EditText) v.findViewById(R.id.signature_box);
        mSignField.setText(mSession.getSign());
        mSignField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSession.setSign(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });

        mSaveSession = (Button) v.findViewById(R.id.save_session);
        mSaveSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mReceiptButton = (Button) v.findViewById(R.id.receipt_button);
        mReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSession.isPaid()) {
                    Intent intent = new Intent(getActivity(), ReceiptActivity.class);
                    intent.putExtra(EXTRA_SESSION_TITLE, mSession.getTitle());
                    intent.putExtra(EXTRA_SESSION_DATE, mSession.getDate().toString());
                    intent.putExtra(EXTRA_SESSION_PRICE, mSession.getPrice());
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), R.string.unpaid_session, Toast.LENGTH_LONG).show();
                }
            }
        });

        mPayButton = (Button) v.findViewById(R.id.pay_button);
        mPayButton.setText(getString(R.string.pay_button, mSession.getPrice()));
        if(mSession.isPaid()) {
            mPayButton.setEnabled(false);
        } else {
            mPayButton.setEnabled(true);
        }
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession.setPaid(true);
                if (mSession.isPaid()) {
                    Toast.makeText(getActivity(), R.string.payment_successful, Toast.LENGTH_LONG).show();
                    mPayButton.setEnabled(false);
                }
            }
        });

        return v;
    }
}
