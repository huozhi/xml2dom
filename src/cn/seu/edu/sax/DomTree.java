package cn.seu.edu.sax;

/**
 * Created by giles on 2015/4/18.
 */
public class DomTree {
    public DomNode root;
    public DomNode head;
    public DomNode body;

    public DomTree() {
        this.root = new DomNode();
    }

    public DomTree(DomNode root) {
        this.root = root;
    }

    public void insertNode(DomNode node) {
        root.appendChild(node);
    }

    public void DFSPrintDom() {
        DFSPrintDom(this.head, 0);
        DFSPrintDom(this.body, 0);
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
