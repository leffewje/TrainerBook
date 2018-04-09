package com.bignerdranch.android.trainerbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.trainerbook.database.CustomerBaseHelper;
import com.bignerdranch.android.trainerbook.database.CustomerCursorWrapper;
import com.bignerdranch.android.trainerbook.database.CustomerDbSchema;
import com.bignerdranch.android.trainerbook.database.CustomerDbSchema.CustomerTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerGroup {
    private static CustomerGroup sCustomerGroup;

    //private List<Customer> mCustomers;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CustomerGroup get(Context context) {
        if (sCustomerGroup == null) {
            sCustomerGroup = new CustomerGroup(context);
        }
        return sCustomerGroup;
    }

    private CustomerGroup(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CustomerBaseHelper(mContext)
                .getWritableDatabase();
        /*mCustomers = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Customer customer = new Customer();
            customer.setName("Customer #" + i);
            mCustomers.add(customer);
        }*/
    }

    public void addCustomer(Customer c) {
        //mCustomers.add(c);
        ContentValues values = getContentValues(c);

        mDatabase.insert(CustomerTable.NAME, null, values);
    }

    public List<Customer> getCustomers() {
        //return mCustomers;
        List<Customer> customers = new ArrayList<>();

        CustomerCursorWrapper cursor = queryCustomers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                customers.add(cursor.getCustomer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return customers;
    }

    public Customer getCustomer(UUID id) {
        /*for (Customer customer : mCustomers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }*/

        CustomerCursorWrapper cursor = queryCustomers(
                CustomerTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try{
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCustomer();
        } finally {
            cursor.close();
        }
    }

    public void updateCustomer(Customer customer) {
        String uuidString = customer.getId().toString();
        ContentValues values = getContentValues(customer);

        mDatabase.update(CustomerTable.NAME, values,
                CustomerTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    private CustomerCursorWrapper queryCustomers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CustomerTable.NAME,
                null,   //columns - null selects all columns
                whereClause,
                whereArgs,
                null,   //groupBy
                null,   //having
                null    //orderBy
        );
        return new CustomerCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(CustomerTable.Cols.UUID, customer.getId().toString());
        values.put(CustomerTable.Cols.NAME, customer.getName());
        values.put(CustomerTable.Cols.DATE, customer.getDate().getTime());
        values.put(CustomerTable.Cols.BILLING, customer.getBilling());

        return values;
    }
}
