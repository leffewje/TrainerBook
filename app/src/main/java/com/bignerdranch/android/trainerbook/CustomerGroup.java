package com.bignerdranch.android.trainerbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.trainerbook.database.CustomerBaseHelper;
import com.bignerdranch.android.trainerbook.database.CustomerCursorWrapper;
import com.bignerdranch.android.trainerbook.database.CustomerDbSchema.CustomerTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerGroup {
    private static CustomerGroup sCustomerGroup;

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
    }

    public void addCustomer(Customer c) {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CustomerTable.NAME, null, values);
    }

    public List<Customer> getCustomers() {
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

    public File getPhotoFile(Customer customer) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, customer.getPhotoFilename());
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
