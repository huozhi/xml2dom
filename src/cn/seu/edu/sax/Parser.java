package cn.seu.edu.sax;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by giles on 2015/4/18.
 */

public class Parser {
    Scanner tokenSc;
    File file;
    DomTree domTree;
    DomNode currNode = null;
    DomNodeType currType = null;
    DomNodeType prevType = DomNodeType.HEAD;
    Stack<DomNode> stk; // use to traversal dom tree

    public Parser() {
        init();
    }


    public DomTree parseAll() {
        boolean process = handleToken();
        while (process)
            process = handleToken();
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
        domTree.head = new DomNode("head", DomNodeType.ROOT);
        domTree.body = new DomNode("body", DomNodeType.ROOT);
        stk = new Stack<DomNode>();
        stk.push(domTree.body);
    }

    public void setFile(String path) {
        this.file = new File(path);
        try {
            tokenSc = new Scanner(file);
            tokenSc.useDelimiter("\\ |\\>|\\?\\>");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected String readToken() {
        if (tokenSc.hasNext()) {
           return tokenSc.next();
        }
        return null;
    }

    protected boolean handleToken() {
        String token = readToken();
        if (token == null) return false;
        if (token.isEmpty() || token.matches("(?s).*[\\n\\r].*")) return true;
        DomNodeType type = getType(token);
        if (type == DomNodeType.HEAD) {
            // head data
            handleHead(token);
            prevType = type;
        }
        else if (type == DomNodeType.ATTR) {
            if (prevType == DomNodeType.HEAD) {
                handleHead(token);
            }
            else {
                handleBody(token);
            }
        }
        else {
            // body data
            handleBody(token);
            prevType = type;
        }

        return true;
    }

    protected void handleHead(String token) {
        DomNodeType type = getType(token);
        DomNode headNode;
        if (type == DomNodeType.HEAD) {
            headNode = new DomNode("xml", DomNodeType.HEAD);
            domTree.head.appendChild(headNode);
        }
        else if (type == DomNodeType.ATTR) {
            String[] attrs = token.split("\\ ");
            for (int i = 1; i < attrs.length; i++) {
                List<DomNode> children = domTree.head.children;
                headNode = children.get(children.size() - 1); // get last
                String[] map = parseAttr(attrs[i]);
                headNode.setAttr(map[0], map[1]);

            }
        }
    }

    protected void handleBody(String token) {
        if (token.isEmpty() || token.matches("(?s).*[\\n\\r].*")) {
            return;
        }

        currType = getType(token);
        if (currType == DomNodeType.START) {
            currNode = new DomNode(token.substring(1), currType);
            DomNode parent = stk.peek();

            // push parent to stack
            stk.push(currNode);
            parent.appendChild(currNode);

        }
        else if (currType == DomNodeType.ATTR) {
            String[] pair = parseAttr(token);
            currNode.setAttr(pair[0], pair[1]);
        }
        else if (currType == DomNodeType.END) {
            // close parsing header node
            String[] tks;
            tks = token.split("</");
            if (tks.length > 1 && tks[0].matches("[a-zA-Z]+")) {
                currNode.value = tks[0];
            }
            if (!stk.empty()) {
                currNode = stk.pop();
            }
        }
    }


    public static DomNodeType getType(String token) {
        try {

            if (token.startsWith("<?")) {
                return DomNodeType.HEAD;
            }
            else if (token.indexOf("</") >= 0 || token.endsWith("?>")) {
                return DomNodeType.END;
            }
            else if (token.contains("=")) {
                return DomNodeType.ATTR;
            }
            else if (token.charAt(0) == '<' && !token.startsWith("<?")) {
                return DomNodeType.START;
            }
            else {
                return DomNodeType.END;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DomNodeType.END.ROOT;
    }

    public static String[] parseAttr(String token) {
        String[] pair = token.split("\\ *=\\ *");
        pair[1] = pair[1].substring(1, pair[1].length() - 2);
        return pair;
    }

}
