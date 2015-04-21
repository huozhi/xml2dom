package cn.seu.edu.sax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by giles on 2015/4/18.
 */

public class DomNode {
    public String name;
    public String value;
    public List<DomNode> children;
    public Map<String, String> attrs;
    public DomNodeType type;

    public DomNode() {
        this.name = null;
        this.value = null;
        this.type = DomNodeType.ROOT;
        init();
    }

    public DomNode(String name, DomNodeType type) {
        this.name = name;
        this.type = type;
//        this.layer = layer;
        init();
    }


    public DomNode(String name, String value, DomNodeType type) {
        this.name = name;
        this.value = value;
        this.type = type;
//        this.layer = layer;
        init();
    }

    public void init() {
        this.children = new ArrayList<DomNode>();
        this.attrs = new HashMap<String, String>();
    }

    public void setAttr(String key, String value) {
        attrs.put(key, value);
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }

    public void appendChild(DomNode child) {
        children.add(child);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public String DFSPrintDom() {
        StringBuilder sb = new StringBuilder();
        DFSPrintDom(this, 0, sb);

        return sb.toString();
    }

    public void DFSPrintDom(DomNode root, int layer, StringBuilder sb) {
        if (root != null) {
            for (int i = 0; i < layer; i++) {
                System.out.print(" ");
                sb.append(" ");
            }
            sb.append(root.name + " ");
            System.out.printf("%s ", root.name);
            if (root.value != null && root.value != null) {
                System.out.println(root.value);
                sb.append(root.value);
            }
            else {
                System.out.println();
            }
            sb.append("\n");
            for (int i = 0; i < root.children.size(); i++) {
                DFSPrintDom(root.children.get(i), layer + 1, sb);
            }
        }
    }
}
