package com.example.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class AccountsManager {
    public static AccountsManager instance;

    public enum AccessPermission{ //Note that everything is accessible to super users
        SuperUserOnly, //only superusers can access it
        Professor, //only professors can access it
        Owner, //only the owner of the item can access it
        Global //everyone can access it
    }

    public static List<Account> registeredAccounts = new ArrayList<>();
    public static Account loggedAccount;

    public static void Initialize(){
        registeredAccounts = new ArrayList<Account>();
        registeredAccounts.add(new Account("Youssef Chebil", "211-6463", Account.UserRole.SuperUser));
        registeredAccounts.add(new Account("Ons Oueniche", "211-4060", Account.UserRole.Student));
        registeredAccounts.add(new Account("Ahmed Khemiri", "211-3798", Account.UserRole.Student));
        registeredAccounts.add(new Account("Yassine Chaouch", "211-5468", Account.UserRole.Professor));
        loggedAccount = GetAccountOfName("Ahmed Khemiri");
        System.out.println("Logged In As " + loggedAccount.name);
    }

    public static Account GetAccountOfName(String name){
        for(int i = 0;i<registeredAccounts.size();i++){
            if(registeredAccounts.get(i).name.toLowerCase(Locale.ROOT).compareTo(name.toLowerCase(Locale.ROOT)) == 0) return registeredAccounts.get(i);
        }
        return null;
    }

    public static Account GetAccountOfSID(String SID){
        for(int i = 0;i<registeredAccounts.size();i++){
            System.out.println("Comparing ." + registeredAccounts.get(i).SID + ". and ." + SID + ".");
            if(registeredAccounts.get(i).SID.compareTo(SID) == 0) return registeredAccounts.get(i);
        }
        return null;
    }

    public static AccessPermission DecodeAccessPermission(int code){
        switch (code){
            case 0:
                return AccessPermission.Global;
            case 1:
                return AccessPermission.Owner;
            case 2:
                return AccessPermission.Professor;
            case 3:
                return AccessPermission.SuperUserOnly;
        }

        return AccessPermission.SuperUserOnly;
    }

    public static boolean HasAccess(AccessPermission accessPermission, String creatorSID){
        if(loggedAccount.role == Account.UserRole.SuperUser) return true;
        if(accessPermission == AccessPermission.Global) return true;

        if(creatorSID == loggedAccount.SID) return true;

        if(loggedAccount.role == Account.UserRole.Professor){
            return accessPermission == AccessPermission.Professor;
        }

        return false;
    }

    public static int EncodeAccessPermission(AccessPermission accessPermission){
        if(accessPermission == AccessPermission.Global){
            return 0;
        }else if(accessPermission == AccessPermission.Owner){
            return 1;
        }else if(accessPermission == AccessPermission.Professor){
            return 2;
        }else if(accessPermission == AccessPermission.SuperUserOnly){
            return 3;
        }

        return -1;
    }
}
