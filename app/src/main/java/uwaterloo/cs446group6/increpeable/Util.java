package uwaterloo.cs446group6.increpeable;

public class Util {
    public static String convertNumber(int num){
        String result;
        if (num >= 1000000000){
            result = String.format("%.1f", num / 1000000000.0) + "B";
        } else if (num >= 1000000){
            result = String.format("%.1f", num / 1000000.0) + "M";
        } else if (num >= 1000){
            result = String.format("%.1f", num / 1000.0) + "K";
        } else {
            result = String.valueOf(num);
        }
        return result;
    }
}
