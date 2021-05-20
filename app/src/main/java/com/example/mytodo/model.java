package com.example.mytodo;

import java.io.Serializable;
import java.util.HashMap;

public class model implements Serializable {
    private String Task, DueDate, UserID, Key;

    public model(){}

    public void setTask(String task) {
        this.Task = task;
    }

    public void setDueDate(String dueDate) {
        this.DueDate = dueDate;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public String getTask() {
        return Task;
    }

    public String getDueDate() {
        return DueDate;
    }

    public String getUserID() {
        return UserID;
    }

    public String getKey() {
        return Key;
    }

    public HashMap<String, String> toFirebaseObject(){
        HashMap<String, String> task = new HashMap<String, String>();
        task.put("UserID", UserID);
        task.put("key", Key);
        task.put("Task", Task);
        task.put("DueDate", DueDate);

        return task;
    }

}
