import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/2/21 18:26
 */

public class Lists2 {
    public static  <T> List<T> of(T... items) {
        if (null == items || items.length == 0) {
            return new ArrayList<>();
        }
        List<T> ans = new ArrayList<>(items.length);
        for (T item : items) {
            ans.add(item);
        }
        return ans;
    }

    public static <T> List<T> contact(List<T>... lists) {
        if (null == lists || lists.length == 0){
            return new ArrayList<>();
        }
        List<T> ans = new LinkedList<>();
        for (List<T> list : lists) {
            if (null != list && !list.isEmpty()){
                ans.addAll(list);
            }
        }
        return ans;
    }
}
