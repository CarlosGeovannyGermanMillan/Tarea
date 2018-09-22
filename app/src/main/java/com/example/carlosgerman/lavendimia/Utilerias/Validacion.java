package com.example.carlosgerman.lavendimia.Utilerias;

import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

//Clase para Validar caracteres dentro de Cajas de textos

public class Validacion {
    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private static final String PHONE_REGEX_MEX = "\\d{10}";
    private static final String CP_REGEX_MEX = "\\d{5}";
    private static final String DATE_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    // Error Messages
//	private static final String REQUIRED_MSG = "Requerido";
//	private static final String EMAIL_MSG = "Correo electronico invalido";
//	private static final String PHONE_MSG = "###-#######";
//	private static final String COMPARE_STRINGS = "Las contrase�as no coinciden";

    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required, String message, String messageRequerido) {
        return isValid(editText, EMAIL_REGEX, message, required, messageRequerido);
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required, String message, String messageRequerido) {
        return isValid(editText, PHONE_REGEX, message, required, messageRequerido);
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumberMex(EditText editText, boolean required, String message, String messageRequerido) {
        return isValid(editText, PHONE_REGEX_MEX, message, required, messageRequerido);
    }

    public static boolean isCPMEx(EditText editText, boolean required, String message, String messageRequerido) {
        return isValid(editText, CP_REGEX_MEX, message, required, messageRequerido);
    }

    // call this method when you need to check date validation
    public static boolean isDateValid(EditText editText, boolean required, String message, String messageRequerido) {
        return isValid(editText, DATE_REGEX, message, required, messageRequerido);
    }

    public static boolean IsPassword(TextView editText, String message) {
        editText.setError(null);
        String password = editText.getText().toString();
        char clave;
        boolean cont1=false,cont2=false,cont3=false;
        for (byte i = 0; i < password.length(); i++) {
            clave = password.charAt(i);
            String passValue = String.valueOf(clave);
            if (passValue.matches("[A-Z]"))  {
                cont1=true;
            }
            if (passValue.matches("[a-z]"))  {
                cont2=true;
            }
            if (passValue.matches("[0-9]"))  {
                cont3=true;
            }

        }

        if (cont1 && cont2 && cont3){
            return true;
        }
        else
        editText.setError(message);
        return false;

    }

    public static boolean compareStrings(EditText editText, EditText confirm, String compareMessage, String requiredMessage) {
        confirm.setError(null);
        // text required and editText is blank, so return false
        if (!hasText(confirm, requiredMessage)) return false;
        if (!editText.getText().toString().equals(confirm.getText().toString())) {
            confirm.setError(compareMessage);
            return false;
        } else
            return true;
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required, String messageRequerido) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (required && !hasText(editText, messageRequerido)) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText, String message) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(message);
            return false;
        }

        return true;
    }

    public static boolean hasText(TextView editText, String message) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(message);
            return false;
        }

        return true;
    }



}
