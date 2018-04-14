package com.bignerdranch.android.trainerbook;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class CustomerFragment extends Fragment {
    private static final String ARG_CUSTOMER_ID = "customer_id";
    private static final int REQUEST_PHOTO = 2;

    private Customer mCustomer;
    private EditText mNameField;
    private Button mDateButton;
    private EditText mBilling;
    private Button mSaveButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private ImageButton mAddSessionButton;

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
        mPhotoFile = CustomerGroup.get(getActivity()).getPhotoFile(mCustomer);
    }

    @Override
    public void onPause() {
        super.onPause();

        CustomerGroup.get(getActivity())
                .updateCustomer(mCustomer);
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

        mSaveButton = (Button) v.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), CustomerListActivity.class);
                startActivity(intent2);
            }
        });

        mPhotoButton = (ImageButton) v.findViewById(R.id.customer_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(true/*canTakePhoto*/);

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(), "com.bignerdranch.android.TrainerBook.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager().queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.customer_photo);
        updatePhotoView();

        mAddSessionButton = (ImageButton) v.findViewById(R.id.add_session_button);
        mAddSessionButton.setEnabled(true);
        mAddSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session session = new Session();
                SessionGroup.get(getActivity()).addSession(session);
                Intent intent3 = SessionActivity.newIntent(getActivity(), session.getId());
                startActivity(intent3);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(), "com.bignerdranch.android.TrainerBook.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
