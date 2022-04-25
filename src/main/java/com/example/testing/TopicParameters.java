package com.example.testing;

public class TopicParameters {
    public String title;
    public Account.UserRole[] availableFor;
    public AccountsManager.AccessPermission viewPermission;
    public int displayCode; //corresponding (creation / editing) user interface

    public TopicParameters(String title, Account.UserRole availableFor, AccountsManager.AccessPermission viewPermission, int displayCode){
        this.title = title;
        this.availableFor = new Account.UserRole[]{availableFor};
        this.viewPermission = viewPermission;
        this.displayCode = displayCode;
    }

    public TopicParameters(String title, Account.UserRole[] availableFor, AccountsManager.AccessPermission viewPermission, int displayCode){
        this.title = title;
        this.availableFor = availableFor;
        this.viewPermission = viewPermission;
        this.displayCode = displayCode;
    }
}
