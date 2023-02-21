import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 在内存中快速的模糊匹配查找字符串
 * {@code Author} FuYouJ
 * {@code Date} 2023/2/21 17:51
 */

public class StringTrie {
    private boolean wordEnd;
    private int wordCnt;
    private final ConcurrentSkipListMap<Character, StringTrie> chars;

    public StringTrie() {
        this.chars = new ConcurrentSkipListMap<>();
        this.wordEnd = false;
        this.wordCnt = 0;
    }

    public StringTrie insert(final String word) {
        if (null == word || "".equals(word)) {
            return null;
        }
        return insert(word.toCharArray());
    }

    public StringTrie insert(final char[] chars) {
        StringTrie node = this;
        for (char c : chars) {
            if (node.chars.containsKey(c)) {
                node = node.chars.get(c);
            } else {
                StringTrie trie = new StringTrie();
                node.chars.put(c, trie);
                node = trie;
            }
        }
        node.wordCnt++;
        node.wordEnd = true;
        return node;
    }

    private List<SearchResult> rightLikeQuery(final String actualStr) {
        if (Strings2.isBlank(actualStr)) {
            return Lists2.of(SearchResult.zeroCntStr(actualStr));
        }
        StringTrie search = search(actualStr);
        if (search == null) {
            return Lists2.of(SearchResult.zeroCntStr(actualStr));
        } else {
            List<SearchResult> ans = new LinkedList<>();
            return ans;
        }
    }

    private void bfs(StringTrie search, List<SearchResult> ans, String temp) {
        if (search == null){
            return;
        }
        if (search.wordEnd){
            ans.add(SearchResult.of(temp,search.wordCnt));
        }
        NavigableSet<Character> child = search.chars.keySet();
        for (Character c : child) {
            String t = temp + c;
            bfs(search.chars.get(c),ans,t);
        }

    }

    public List<SearchResult> exactQuery(String word) {
        if (Strings2.isBlank(word)){
            return new ArrayList<>();
        }
        StringTrie search = this.search(word);
        if (search == null || !search.wordEnd){
            return new ArrayList<>();
        }else {
            return Lists2.of(SearchResult.of(word,search.wordCnt));
        }
    }

    private StringTrie search(String word){
        return searchByArr(word.toCharArray());
    }
    private StringTrie searchByArr(char[] chars){
        StringTrie cursor = this;
        for (char c : chars) {
            if (cursor.contains(c)){
                cursor = cursor.next(c);
            }else {
                return null;
            }
        }
        return cursor;
    }

    private StringTrie next(char c) {
        return this.chars.get(c);
    }

    private boolean contains(char c) {
        return this.chars.containsKey(c);
    }
    public List<SearchResult> rightLike(String word){
        return rightLikeByArr(word.toCharArray());
    }

    public List<SearchResult> rightLikeByArr(char[] chars) {
        if (null == chars || chars.length == 0){
            return new ArrayList<>();
        }
        StringTrie search = searchByArr(chars);
        if (search == null){
            return new ArrayList<>();
        }
        List<SearchResult> ans = new LinkedList<>();
        Collection<Character> wordsPrefix = search.chars();
        for (Character prefix : wordsPrefix) {
            rightLikeBfs(ans,search.next(prefix),new String(chars)+prefix);
        }
        return ans;
    }

    private void rightLikeBfs(List<SearchResult> ans, StringTrie trie, String temp) {
//        if (trie == null){
//            ans.add(SearchResult.of(Strings2.reverse(temp),1));
//            return;
//        }
        if (trie.wordEnd){
            ans.add(SearchResult.of(Strings2.reverse(temp),trie.wordCnt));
        }
        for (Character c : trie.chars()) {
            String t = temp + c;
            rightLikeBfs(ans,trie.next(c),t);
        }
    }

    private Collection<Character> chars() {
        return this.chars.keySet();
    }

    public List<SearchResult> fullLike(String word) {
        StringTrie start = this;
        List<SearchResult> ans = new LinkedList<>();
        String temp = "";
        fullScan(start,temp,word,ans,false);
        return ans;
    }

    private void fullScan(StringTrie trie, String temp, String word, List<SearchResult> ans,boolean contains) {
        if (trie == null){
            return;
        }
        for (Character prefix : trie.chars()) {
            String t = temp + prefix;
            StringTrie next = trie.next(prefix);
            if (contains && next.wordEnd){
                ans.add(SearchResult.of(t,next.wordCnt));
                fullScan(next,t,word,ans,true);
            }else if (t.contains(word) && next.wordEnd){
                ans.add(SearchResult.of(t,next.wordCnt));
                fullScan(next,t,word,ans,true);
            }else {
                fullScan(next,t,word,ans,false);
            }
        }
    }
}
