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
}
