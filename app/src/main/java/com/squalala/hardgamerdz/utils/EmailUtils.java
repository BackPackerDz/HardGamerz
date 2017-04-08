package com.squalala.hardgamerdz.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;

import java.util.regex.Pattern;


/**
 * Created by Ravi on 01/06/15.
 */
public class EmailUtils {

    private static String TAG = EmailUtils.class.getSimpleName();

    public static String getEmail(Context context) {
        // Supérieur à 2.1
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.ECLAIR) {

            Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
            Account[] accounts = AccountManager.get(context).getAccounts();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    String possibleEmail = account.name;
                    return possibleEmail;
                }
            }
        }
        // Égale à 2.1
        else {
            Account[] accounts = AccountManager.get(context).getAccounts();
            if (accounts.length > 0) {
                String possibleEmail = accounts[0].name;
                return possibleEmail;
            }
        }

        return "no_adress";
    }

}
