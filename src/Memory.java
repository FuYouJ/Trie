import java.util.*;
import java.util.stream.Collectors;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/2/21 21:01
 */

public class Memory {
    private final StringTrie prefix;
    private final StringTrie suffix;
    private final Map<Character, Set<StringTrie>> start;
    private final Map<Character, Set<StringTrie>> end;

    public Memory() {
        this.prefix = new StringTrie();
        this.suffix = new StringTrie();
        this.start = new HashMap<>();
        this.end = new HashMap<>();
    }

    public synchronized void insert(String word){
        if (Strings2.isBlank(word)){
            return;
        }
        char[] right2left = reverse(word);
        StringTrie p = prefix.insert(word);
        StringTrie s = suffix.insert(right2left);
        Character left = word.charAt(0);
        Character right = word.charAt(word.length() - 1);


        start.compute(left,(k,vs) ->{
            if (vs == null){
                vs = new HashSet<>();
            }
            vs.add(p);
            return vs;
        });

        end.compute(right,(k,vs) ->{
            if (vs == null){
                vs = new HashSet<>();
            }
            vs.add(s);
            return vs;
        });
    }

    private static char[] reverse(String word) {
        char[] right2left = new char[word.length()];
        int i = 0;
        int j = word.length() - 1;
        while (i < word.length()){
            right2left[j--] = word.charAt(i++);
        }
        return right2left;
    }

    public List<SearchResult> exactQuery(String word){
        if (Strings2.isBlank(word)){
            return new ArrayList<>();
        }
        return prefix.exactQuery(word);
    }

    /**
     * %abc
     * @param word abc
     * @return list
     */
    public List<SearchResult> leftLike(String word){
        if (Strings2.isBlank(word)){
            return new ArrayList<>();
        }
        return suffix.rightLikeByArr(reverse(word));
    }

    public List<SearchResult> rightLike(String word) {
        if (Strings2.isBlank(word)){
            return new ArrayList<>();
        }
        List<SearchResult> searchResults = prefix.rightLike(word);
        searchResults.forEach(e -> e.setStr(new String(reverse(e.getStr()))));
        return searchResults;
    }

    public List<SearchResult> fullLike(String word){
        if (Strings2.isBlank(word)){
            return new ArrayList<>();
        }
        return prefix.fullLike(word);
    }
}
