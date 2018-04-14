package com.bignerdranch.android.trainerbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class CustomerListFragment extends Fragment {

    private RecyclerView mCustomerRecyclerView;
    private CustomerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        mCustomerRecyclerView = (RecyclerView) view
                .findViewById(R.id.customer_recycler_view);
        mCustomerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_customer_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_customer:
                Customer customer = new Customer();
                CustomerGroup.get(getActivity()).addCustomer(customer);
                Intent intent = CustomerActivity.newIntent(getActivity(), customer.getId());
                startActivity(intent);
                return true;
            case R.id.logoff:
                FragmentManager manager = getFragmentManager();
                LogoffDialog logoffDialog = new LogoffDialog();
                logoffDialog.show(manager, "Logging Off");
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        CustomerGroup customerGroup = CustomerGroup.get(getActivity());
        List<Customer> customers = customerGroup.getCustomers();

        if(mAdapter == null) {
            mAdapter = new CustomerAdapter(customers);
            mCustomerRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCustomers(customers);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CustomerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mDateTextView;
        private Customer mCustomer;
        private ImageView mPhotoView;
        private File mPhotoFile;

        public CustomerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_customer, parent, false));
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.customer_name);
            mDateTextView = (TextView) itemView.findViewById(R.id.customer_date);
            mPhotoView = (ImageView) itemView.findViewById(R.id.customer_photo);
        }

        public void bind(Customer customer) {
            mCustomer = customer;
            mNameTextView.setText(mCustomer.getName());
            mDateTextView.setText(mCustomer.getDate().toString());
            mPhotoFile = CustomerGroup.get(getActivity()).getPhotoFile(mCustomer);
            if (mPhotoFile == null || !mPhotoFile.exists()) {
                mPhotoView.setImageDrawable(null);
            } else {
                Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
                mPhotoView.setImageBitmap(bitmap);
            }
        }

        @Override
        public void onClick(View view) {
            Intent intent = CustomerActivity.newIntent(getActivity(), mCustomer.getId());
            startActivity(intent);
        }
    }

    private class CustomerAdapter extends RecyclerView.Adapter<CustomerHolder> {

        private List<Customer> mCustomers;

        public CustomerAdapter(List<Customer> customers) {
            mCustomers = customers;
        }

        @NonNull
        @Override
        public CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CustomerHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerHolder holder, int position) {
            Customer customer = mCustomers.get(position);
            holder.bind(customer);
        }

        @Override
        public int getItemCount() {
            return mCustomers.size();
        }

        public void setCustomers(List<Customer> customers) {
            mCustomers = customers;
        }
    }

    /*private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }*/
}
