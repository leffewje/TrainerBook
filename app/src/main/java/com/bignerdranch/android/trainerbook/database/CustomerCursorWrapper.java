package com.bignerdranch.android.trainerbook.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.trainerbook.Customer;
import com.bignerdranch.android.trainerbook.database.CustomerDbSchema.CustomerTable;

import java.util.Date;
import java.util.UUID;

public class CustomerCursorWrapper extends CursorWrapper {
    public CustomerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Customer getCustomer() {
        String uuidString = getString(getColumnIndex(CustomerTable.Cols.UUID));
        String name = getString(getColumnIndex(CustomerTable.Cols.NAME));
        long date = getLong(getColumnIndex(CustomerTable.Cols.DATE));
        String billing = getString(getColumnIndex(CustomerTable.Cols.BILLING));

        Customer customer = new Customer(UUID.fromString(uuidString));
        customer.setName(name);
        customer.setDate(new Date(date));
        customer.setBilling(billing);

        return customer;
    }
}
