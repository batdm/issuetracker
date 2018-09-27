package com.axmor.status;

import java.util.List;

import static com.axmor.Main.connectDB;

public class StatusDao {
    public List<Status> statusList;
    public Iterable<Status> getAllStatus() {
        statusList = connectDB.model.getAllStatus();
        return statusList;
    }
}
