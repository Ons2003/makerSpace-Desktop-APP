package com.example.testing;

public class CreationInfo {
    public Account creator;
    public AccountsManager.AccessPermission accessPermission;

    public CreationInfo(String creatorSID, AccountsManager.AccessPermission accessPermission){
        this.accessPermission = accessPermission;
        creator = AccountsManager.GetAccountOfSID(creatorSID);
        System.out.println("looking for " + creatorSID);
        System.out.println("looking for " + creator.SID);
    }
}
