/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/2/21 18:18
 */

public class SearchResult {
    private String str;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public String getStr() {
        return str;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public SearchResult(String str, Integer count) {
        this.str = str;
        this.count = count;
    }

    public static SearchResult of(String str, Integer count) {
        return new SearchResult(str, count);
    }

    public static SearchResult zeroCntStr(String str) {
        return new SearchResult(str, 0);
    }

    @Override
    public String toString() {
        return "StringWithCnt{" +
                "str='" + str + '\'' +
                ", count=" + count +
                '}';
    }
}
