package myapps.reader.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
    private static Pattern PHONE_REGEX = Pattern.compile("\\d{9}");
    private static Pattern NUMERIC_REGEX = Pattern.compile("[+]?[0-9][0-9]*");




    public static boolean isEmail(String contact) {
        return EMAIL_REGEX.matcher(contact).matches();
    }


    //TODO ustalić regex
    public static boolean isJabber(String contact) {
        return false;
    }


    public static boolean isPhone(String contact) {
        return PHONE_REGEX.matcher(contact
                .replaceAll("-","")
                .replaceAll(" ",""))
                .matches();
    }

    public static boolean isNumeric(String strNum){
        Matcher m = NUMERIC_REGEX.matcher(strNum);
        return (!(m.find() && m.group().equals(strNum)));
    }

}
