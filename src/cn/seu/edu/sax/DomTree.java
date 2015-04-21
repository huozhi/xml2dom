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

    public String DFSPrintDom() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dom Header\n");
        DFSPrintDom(this.head, 0, sb);

        sb.append("\nDom Body\n");
        DFSPrintDom(this.body, 0, sb);
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
