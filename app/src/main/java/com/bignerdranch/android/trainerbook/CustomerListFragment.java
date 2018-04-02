package com.bignerdranch.android.trainerbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CustomerListFragment extends Fragment {

    private RecyclerView mCustomerRecyclerView;
    private CustomerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        mCustomerRecyclerView = (RecyclerView) view
                .findViewById(R.id.customer_recycler_view);
        mCustomerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CustomerGroup customerGroup = CustomerGroup.get(getActivity());
        List<Customer> customers = customerGroup.getCustomers();

        mAdapter = new CustomerAdapter(customers);
        mCustomerRecyclerView.setAdapter(mAdapter);
    }

    private class CustomerHolder extends RecyclerView.ViewHolder {
        public CustomerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_customer, parent, false));
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

        }

        @Override
        public int getItemCount() {
            return mCustomers.size();
        }
    }
}
