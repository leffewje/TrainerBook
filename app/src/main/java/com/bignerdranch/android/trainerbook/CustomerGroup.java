package com.bignerdranch.android.trainerbook;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerGroup {
    private static CustomerGroup sCustomerGroup;

    private List<Customer> mCustomers;

    public static CustomerGroup get(Context context) {
        if (sCustomerGroup == null) {
            sCustomerGroup = new CustomerGroup(context);
        }
        return sCustomerGroup;
    }

    private CustomerGroup(Context context) {
        mCustomers = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Customer customer = new Customer();
            customer.setName("Customer #" + i);
            mCustomers.add(customer);
        }
    }

    public List<Customer> getCustomers() {
        return mCustomers;
    }

    public Customer getCustomer(UUID id) {
        for (Customer customer : mCustomers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }

        return null;
    }
}
