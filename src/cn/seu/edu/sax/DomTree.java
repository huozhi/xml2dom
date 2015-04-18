package cn.seu.edu.sax;

/**
 * Created by giles on 2015/4/18.
 */
public class DomTree {
    public DomNode root;

    public DomTree() {
        this.root = new DomNode();
//        this.root.setAttr("version", "1.0");
//        this.root.setAttr("encoding", "utf-8");
    }

    public void insertNode(DomNode node) {
        root.appendChild(node);
    }



}
