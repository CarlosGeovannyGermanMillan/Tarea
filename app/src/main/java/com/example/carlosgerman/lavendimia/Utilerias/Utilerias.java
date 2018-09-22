package com.example.carlosgerman.lavendimia.Utilerias;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//Utilerias base de java
public class Utilerias {

    public static String parsearNumeroAString(Double cantidad){
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat formatter = new DecimalFormat("###,###,###.##", otherSymbols);

        String numeroString = "0";
        if(cantidad % 1 == 0)
            numeroString = "$" + formatter.format(cantidad) + ".00";
        else
            numeroString = "$" + formatter.format(cantidad) + "";
         return numeroString;
    }

    public static String parsearStringANumero (String Valor)
    {

        //String numeroString
        if(Valor.charAt(0)==36) {
            String[] parts = Valor.split("\\$");
            String numeroString = parts[1].replace(",", "");
            //numeroString.replace(".00","");
            Double numeroDoble = Double.valueOf(numeroString);
            Integer numeroInteger = numeroDoble.intValue();
            return numeroInteger.toString();

        }
        else

            return null;

    }

    public static String capitalize(String text){
        String c = (text != null)? text.trim() : "";
        String[] words = c.split(" ");
        String result = "";
        for(String w : words){
            result += (w.length() > 1? w.substring(0, 1).toUpperCase(Locale.US) + w.substring(1, w.length()).toLowerCase(Locale.US) : w) + " ";
        }
        return result.trim();
    }

    public static String obtenerFechaFormateada(Date fecha, String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(fecha);
    }

    public static String parsearNumeroaDecimal (Double num)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###.##");
        String yourFormattedString = formatter.format(num);
        return yourFormattedString;
    }

    public static String currency(Double num){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("$#,###.00", symbols);
        return decimalFormat.format(num);
    }
    /*



     */

}
