import java.util.Arrays;
import java.util.List;

/**
 * {@code Author} FuYouJ
 * {@code Date} ${DATE} ${TIME}
 */

public class Main {
    public static void main(String[] args) {
        Memory memory = new Memory();

        memory.insert("付有杰");
        memory.insert("付有杰");

        memory.insert("付付有杰");
        memory.insert("付付有杰杰");

        System.out.println("精确搜索付有杰");
        for (SearchResult result : memory.exactQuery("付有杰")) {
            System.out.println(result);
        }
        System.out.println("搜索%有杰");
        for (SearchResult result : memory.leftLike("有杰")) {
            System.out.println(result);
        }
        System.out.println("搜索付有%");
        for (SearchResult result : memory.rightLike("付有")) {
            System.out.println(result);
        }
        System.out.println("搜索%有%");
        for (SearchResult result : memory.fullLike("有")) {
            System.out.println(result);
        }
    }
}