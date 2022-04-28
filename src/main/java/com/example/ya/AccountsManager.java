package com.example.ya;

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

    public static Account loggedAccount;

    public static void Initialize(){
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
