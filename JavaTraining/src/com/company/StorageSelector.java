package com.company;

import java.sql.SQLException;

public abstract class StorageSelector {

    protected String getStorageLocation() {
        return storageLocation;
    }

    protected void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    private String storageLocation;

    abstract void writeToStorage (Account account) throws SQLException, ClassNotFoundException;

}
