package com.company;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class StorageSelector {

    protected String getStorageLocation() {
        return storageLocation;
    }

    protected void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    protected String getLocalDateNow() {
        return localDateNow;
    }

    private String storageLocation;
    private LocalDateTime localDate = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String localDateNow = formatter.format(localDate);

    abstract void writeToStorage (Account account) throws SQLException, ClassNotFoundException;

    abstract void initFromStorage (String accountNumber,
                                      Account account) throws SQLException, ClassNotFoundException;

}
