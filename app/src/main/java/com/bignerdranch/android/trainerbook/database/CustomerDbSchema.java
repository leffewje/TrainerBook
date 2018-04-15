package com.bignerdranch.android.trainerbook.database;

public class CustomerDbSchema {
    public static final class CustomerTable {
        public static final String NAME = "customers";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String DATE = "date";
            public static final String BILLING = "billing";
        }
    }

    public static final class SessionTable {
        public static final String NAME = "sessions";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String COMPLETED = "completed";
            public static final String SIGN = "sign";
            public static final String SESSION_CUSTOMER_ID = "session_customer_id";
            public static final String PRICE = "price";
            public static final String PAID = "paid";
        }
    }
}
