import java.util.stream.Stream;

// --== CS400 File Header Information ==--
// Name: Xin Cai
// Email: xcai72@wisc.edu
// Team: DF blue
// Role: Backend developer
// TA: Dan
// Lecturer: Gary Dahl
// Notes to Grader: None

public class RedBlackTreeTester {

  public static void main(String[] args) {
    Integer[] res = {46, 22, 43, 40, 52, 31, 10, 49, 16, 19, 34, 37, 28, 13, 25};
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    
    // check insert
    Stream.of(res).forEach(e -> { 
      tree.insert(e); 
      tree.showTree();
    });
    
    System.out.println(">".repeat(50));
    // check remove
    while (!tree.isEmpty()) {
      tree.remove(tree.root.data);
      tree.showTree();
    }
  }

}
