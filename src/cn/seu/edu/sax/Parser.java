package cn.seu.edu.sax;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by giles on 2015/4/18.
 */

public class Parser {
    public Parser() {

    }

    public static DomNodeType getType(String token) {
        try {

            if (token.charAt(0) == '?') {
                return DomNodeType.ROOT;
            }
            else if (token.contains("=")) {
                return DomNodeType.ATTR;
            }
            else if (token.charAt(0) == '<' && token.charAt(1) != '/') {
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
        String[] pair = token.split("(\\ *=\\ *\")|(\"\\?*)");
        //pair[1] = pair[1].substring(1, );
        return pair;
    }

    public DomNode parseAll(File file) {
        DomNodeType prevType = DomNodeType.NULL;
        DomNodeType currType = null;

        Scanner sc2 = null;
        try {
            sc2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DomNode header = null;

        boolean finish = false;
        while (sc2.hasNextLine() && !finish) {
            Scanner tk = new Scanner(sc2.nextLine()).useDelimiter("\\<|\\>|\\ ");
            while (tk.hasNext()) {
                String token = tk.next(); // read current token
                if (token.isEmpty() || token.equals("\n")) continue;

                currType = getType(token);
                if (currType == DomNodeType.ROOT) {
                    header = new DomNode(token.substring(1), DomNodeType.ROOT);
                }
                else if (currType == DomNodeType.ATTR) {
                    String[] pair = parseAttr(token);
                    header.setAttr(pair[0], pair[1]);
                }
                else if (currType == DomNodeType.END) {
                    // close parsing header node
                    finish = true;
                    break;
                }

            }
        }

        // keep on processing with xml body
        DomNode body = new DomNode("xml", DomNodeType.ROOT);
        DomNode prevNode = body;
        // DomNode parentNode = body;
        DomNode currNode = null;
        int layer = 0;
        Stack<DomNode> stk = new Stack<DomNode>();
        stk.push(body);
        while (sc2.hasNextLine()) {
            Scanner tk = new Scanner(sc2.nextLine()).useDelimiter("\\ |\\>");
            while (tk.hasNext()) {
                String token = tk.next(); // read current token
                if (token.isEmpty() || token.equals("\n")) continue;
                currType = getType(token);
                if (currType == DomNodeType.START) {
                    currNode = new DomNode(token.substring(1), currType);
                    DomNode parent = stk.peek();
                    // push parent to stack
                    stk.push(currNode);

                    parent.appendChild(currNode);
                    // just get previous node, but not used
                    prevNode = currNode;

                }
                else if (currType == DomNodeType.ATTR) {
                    String[] pair = parseAttr(token);
                    currNode.setAttr(pair[0], pair[1]);
                    System.out.printf("%s=%s %s\n", pair[0], pair[1], currType.toString());
                }
                else if (currType == DomNodeType.END) {
                    // close parsing header node
                    String[] tks = token.split("</");
                    System.out.println(tks.length);
                    if (tks.length > 1 && !tks[0].isEmpty()) {
                        currNode.value = tks[0];
                    }

                    System.out.print("peek " + (stk.empty() ? "empty " : stk.peek().name) + " ");
                    System.out.printf("%s %s %s\n", token, currNode.value, currType.toString());
                    System.out.printf("%s : %s\n", currNode.name, tks[0]);
                    if (!stk.empty()) {
                        // just get previous node, but not used
                        prevNode = stk.pop();
                    }
                }
            }
        }
        return body;
    }

    public void DFSPrintDom(DomNode root) {
        DFSPrintDom(root, 0);
    }

    public void DFSPrintDom(DomNode root, int layer) {
        if (root != null) {
            for (int i = 0; i < layer; i++)
                System.out.print(" ");
            System.out.printf("%s ", root.name);
            if (root.value != null && root.value != null)
                System.out.println(root.value);

            else
                System.out.println();

            for (int i = 0; i < root.children.size(); i++) {
                DFSPrintDom(root.children.get(i), layer + 1);
            }
        }
    }
}
