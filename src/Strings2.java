/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/2/21 18:29
 */

public class Strings2 {
    public static boolean isBlank(final String str){
        return null == str || "".equals(str);
    }
    public static boolean isNotBlank(final String str){
        return !isBlank(str);
    }

    public static String reverse(String str){
        if (null == str || "".equals(str)){
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }
}
