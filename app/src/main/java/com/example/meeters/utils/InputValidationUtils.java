
package com.example.meeters.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import android.widget.EditText;


public class InputValidationUtils
{

    /**
	 * 
	 */
    public static final String EMPTY = "empty";
    public static final String WEAK = "weak";
    public static final String MEDIUM = "medium";
    public static final String STRONG = "strong";

    public static boolean matchEmail(String text)
    {
        if (Pattern.compile("\\w[\\w.-]*@[\\w.]+\\.\\w+").matcher(text).matches())
        {
            return true;
        }
        return false;
    }

    public static boolean matchPhone(String text)
    {
        // TODO Only matches 10 digits US phone number, and first digit
        // should not be 0 or 1
        String text2digits = text.replaceAll("[^\\d]", "");
        if (Pattern.compile("[2-9][0-9]{9}").matcher(text2digits).matches())
        {
            return true;
        }

        return false;
    }

    public static boolean validateAccount(EditText etAccount)
    {

        String account = etAccount.getText().toString().trim();

        if (StringUtils.isBlank(account))
        {
            return false;
        }

        if (account.length() < 5)
        {
            return false;
        }

        if (matchPhone(account))
        {
            return true;
        }

        if (matchEmail(account))
        {
            return true;
        }

        return false;
    }

    public static boolean validatePwd(EditText etPwd)
    {
        String pwd = etPwd.getText().toString().trim();
        if (StringUtils.isBlank(pwd))
        {
            return false;
        }

        if (pwd.length() < 4)
        {// already checked invalidity of '>16'
            return false;
        }
        return true;
    }

    public static boolean validateNickname(EditText etNickname)
    {
        String nickname = etNickname.getText().toString().trim();
        if (StringUtils.isBlank(nickname))
        {
            return false;
        }

        if (nickname.length() < 3)
        {
            return false;
        }
        return true;
    }

    public static boolean validatePhone(EditText etPhone)
    {
        String phone = etPhone.getText().toString().trim();
        if (StringUtils.isBlank(phone))
        {
            return false;
        }
        return matchPhone(phone);
    }

    public static boolean validateEmail(EditText etEmail)
    {
        String email = etEmail.getText().toString().trim();
        if (StringUtils.isBlank(email))
        {
            return false;
        }
        return matchEmail(email);
    }

    // set the password strength criterion
    public static CharSequence checkPasswordStrength(CharSequence passwordChr)
    {

        /* @return Z = alphabets; S = digits; T = specific characters */

        String regexZ = "\\d*";
        String regexS = "[a-zA-Z]+";
        String regexT = "\\W+$";
        String regexZT = "\\D*";
        String regexST = "[\\d\\W]*";
        String regexZS = "\\w*";
        String regexZST = "[\\w\\W]*";
        String passwordStr = passwordChr.toString();
        if (passwordStr.isEmpty())
        {
            return EMPTY;
        }
        if (passwordStr.length() < 4)
        {
            return WEAK;
        }
        if (passwordStr.matches(regexZ))
        {
            return WEAK;
        }
        if (passwordStr.matches(regexS))
        {
            return WEAK;
        }
        if (passwordStr.matches(regexT))
        {
            return WEAK;
        }
        if (passwordStr.matches(regexZT))
        {
            return MEDIUM;
        }
        if (passwordStr.matches(regexST))
        {
            return MEDIUM;
        }
        if (passwordStr.matches(regexZS))
        {
            return MEDIUM;
        }
        if (passwordStr.matches(regexZST))
        {
            return STRONG;
        }
        else
            return STRONG;
    }

}
