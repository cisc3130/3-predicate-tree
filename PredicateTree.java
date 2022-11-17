import java.util.*;
import java.util.function.*;

public class PredicateTree<E> {

  class Node {
    // These fields are populated if the Node is interior (a predicate node)
    private Predicate<E> p;
    private String exp;       // string representation of the predicate (for printing)
    private Node trueBranch, falseBranch;

    // This field is populated if the Node is a leaf (a data node)
    private List<E> data;

    // constructors. A node can be constructed as a predicate node or as a data node
    public Node(Predicate<E> p, String exp) { 
      this.p = p; this.exp = exp; 
      trueBranch = new Node(new LinkedList<E>());
      falseBranch = new Node(new LinkedList<E>());
    }
    public Node(List<E> data) { this.data = data; }
    public Node(E elt) { data = new LinkedList<E>(); data.add(elt); }

    // check if the node is a predicate node or a data node (for convenience)
    public boolean hasPredicate() { return p != null; }
    public boolean hasData() { return data != null; }

    public String toString() {
      if (hasPredicate()) return exp;
      else return data.toString();
    }
  }

  Node root;

  public void add(List<E> data) {
    for (E elt : data) add(elt);
  }

  public Node add(E elt) {
    // implement. Traverse a single path through the interior predicate nodes by
    // testing each predicate against elt. If it's true, go down its trueBranch.
    // If it's false, go down its falseBranch.
    // If you reach a data node, add elt to its data list.
    // Return the node containing elt.
    // (hint: start with find)
    // don't forget about edge case of empty tree
  }

  public void add(Predicate<E> p, String exp) {
    /* implement. Find all data nodes in the tree. Replace each data node
    with a predicate node containing this p and exp. Add a new data node
    for the predicate node's trueBranch and falseBranch. For every element
    in the node's data list, add it to the correct branch's data node.
    For example, suppose you have the tree
                   Length > 5
                   /         \
          [hello, cocoa]  [goodbye, chameleon, computer]
    and you call add(str -> str.startsWith("c"), "Starts with c")
    The new tree will be
                    Length > 5
                   /         \
          Starts with 'c'   Starts with 'c'
            /       \         /         \
        [hello]  [cocoa]  [goodbye]   [chameleon, computer]

    */
  }

  public Node find(E elt) {
    // implement. Return Node where elt either is or belongs
  }

  public boolean contains(E elt) {
    // implement (hint: start with find)
  }

  public int countDataNodes() {
  
  }

  // print methods for convenience and testing
  public String toString() { return String.join("\n", toString(root)); }
  protected List<String> toString(Node nd) {
    if (nd == null) return null;
    List<String> res = new LinkedList<>();
    if (nd.hasData()) { res.add(nd.toString()); return res; }

    final int MINSEP = 2;
    int SEP = MINSEP;

    List<String> strFalse = toString(nd.falseBranch), strTrue = toString(nd.trueBranch);
    int strFalseLength = strFalse.get(0).length(),
        strTrueLength = strTrue.get(0).length(),
        strExpLength = nd.exp.length(),
        targetLength = Math.max(strFalseLength + SEP + strTrueLength, strExpLength + SEP);
    SEP = targetLength - (strFalseLength + strTrueLength);
    
    // first add current node's explanation
    res.add(" ".repeat((targetLength - strExpLength)/2) + nd.exp + " ".repeat((targetLength - strExpLength)/2));

    // // then " ".repeat(strFalseLength/2) + "/" + " ".repeat(strFalseLength/2) + " ".repeat(SEP) + " ".repeat(strTrueLength/2) + "\\" + " ".repeat(strTrueLength/2));

    Iterator<String> itFalse = strFalse.iterator(), itTrue = strTrue.iterator();
    while (itFalse.hasNext() && itTrue.hasNext()) {
      String fstr = itFalse.next(), tstr = itTrue.next();
      res.add(fstr + " ".repeat(SEP) + tstr);
    }
    while (itFalse.hasNext()) {
      String fstr = itFalse.next();
      res.add(fstr + " ".repeat(SEP) + " ".repeat(strTrueLength));
    }
    while (itTrue.hasNext()) {
      String tstr = itTrue.next();
      res.add(" ".repeat(strFalseLength) + " ".repeat(SEP) + tstr);
    }
    
    return res;
  }

  public static void main(String[] args) {
    PredicateTree<String> sptree = new PredicateTree<>();
    sptree.add(List.of("hello", "cocoa", "goodbye", "chameleon", "computer"));
    System.out.println(sptree);
    sptree.add(str -> str.length() > 5, "Length > 5");
    System.out.println(sptree);
    sptree.add(str -> str.startsWith("c"), "Starts with 'c'");
    System.out.println(sptree);
    sptree.add("rocket");
    System.out.println(sptree);

    System.out.println(sptree.find("rocket"));
    System.out.println(sptree.find("motorcycle"));
    
    System.out.println(sptree.countDataNodes());
  }

}
