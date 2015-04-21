package cn.seu.edu.sax;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by giles on 2015/4/18.
 */

public class Parser {
//    Scanner fileSc;
    Scanner tokenSc;
//    Scanner sc;
    File file;
    DomTree domTree;
    DomNode currNode = null;
    DomNodeType currType = null;
    Stack<DomNode> stk; // use to traversal dom tree

    public Parser() {
        init();
    }

    public Parser(File file) {

        setFile(file.getAbsolutePath());
        init();
    }

    public DomTree parseAll() {
//        domTree.head = parseHead();
        domTree.body = parseBody();

        return domTree;
    }

    public ArrayList<DomNode> search(String name) {
        ArrayList<DomNode> res = new ArrayList<DomNode>();
        search(domTree.body, name, res);
        return res;
    }

    public void search(DomNode root, String name, ArrayList<DomNode> res) {
        if (root == null) return;
        for (int i = 0; i < root.children.size(); i++) {
            DomNode node = root.children.get(i);
            if (node != null) {
                if (node.name.equals(name)) {
                    res.add(node);
                }
                else {
                    search(node, name, res);
                }
            }
        }
    }

    public void init() {
        domTree = new DomTree();
        domTree.head = null;//new DomNode("xml", DomNodeType.HEAD);
        domTree.body = new DomNode("xml", DomNodeType.ROOT);
        stk = new Stack<DomNode>();
        stk.push(domTree.body);
    }

    public void setFile(String path) {
        this.file = new File(path);
        try {
            tokenSc = new Scanner(file);//.useDelimiter("\\ |\\>");
            tokenSc.useDelimiter("\\ |\\>|\\?\\>");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    protected DomNode parseHead() {
        boolean finish = false;
        while (tokenSc.hasNext() && !finish) {
            String token = tokenSc.next(); // read current token
            System.out.println("head: " + token);
            if (token.isEmpty() || token.matches("(?s).*[\\n\\r].*")) continue;

            currType = getType(token);
            if (currType == DomNodeType.HEAD) {
                domTree.head = new DomNode(token.substring(1), DomNodeType.HEAD);
            }
            else if (currType == DomNodeType.ATTR) {
                String[] pair = parseAttr(token);
                domTree.head.setAttr(pair[0], pair[1]);
            }
            else if (currType == DomNodeType.END) {
                // close parsing header node
                finish = true;
                break;
            }
        }
//        if (domTree.head == null)
//            System.out.println("null head");
        return domTree.head;
    }

    public static DomNodeType getType(String token) {
        try {

            if (token.startsWith("<?")) {
                return DomNodeType.HEAD;
            }
            else if (token.charAt(token.length() - 1) == '?' ||
                    token.startsWith("</")) {
                return DomNodeType.END;
            }
            else if (token.contains("=")) {
                return DomNodeType.ATTR;
            }
            else if (token.charAt(0) == '<') {
                return DomNodeType.START;
            }
            else {
                return DomNodeType.END;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("ROOT " + token);
        return DomNodeType.END.ROOT;
    }

    public static String[] parseAttr(String token) {
        String[] pair = token.split("(\\ *=\\ *\")|(\"\\?*)");
        return pair;
    }


    public DomNode process() {
        boolean finish = false;
        if (tokenSc.hasNext()) {
            String token = tokenSc.next(); // read current token
            System.out.println("token: " + token);

            if (token.isEmpty() || token.matches("(?s).*[\\n\\r].*")) {
                return new DomNode("", DomNodeType.EMPTY);
            }

            currType = getType(token);
            if (currType == DomNodeType.START) {
                System.out.println("start: " + token);
                currNode = new DomNode(token.substring(1), currType);
                DomNode parent = stk.peek();

                // push parent to stack
                stk.push(currNode);
                parent.appendChild(currNode);

            }
            else if (currType == DomNodeType.ATTR) {
                System.out.println("attr: " + token);
                String[] pair = parseAttr(token);
                currNode.setAttr(pair[0], pair[1]);
            }
            else if (currType == DomNodeType.END) {
                System.out.println("end: " + token);
                // close parsing header node
                String[] tks;
                tks = token.split("</");
                if (tks.length > 1 && tks[0].matches("[a-zA-Z]+")) {
                    System.out.println("values");
                    for (String s : tks)
                        System.out.print(s + ",");
                    System.out.println("\n" + (currNode == null ? "null" : "full"));
                    currNode.value = tks[0];
                }
                if (!stk.empty()) {
                    currNode = stk.pop();
                }
            }
        }
        else {
            finish = true;
        }
        return finish ? null : currNode;
    }

    public DomNode parseBody() {
        while (process() != null) { }
//        if (domTree.body == null)
//            System.out.println("null body");
        return domTree.body;
    }


}
