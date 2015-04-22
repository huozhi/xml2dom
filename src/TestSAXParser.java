import cn.seu.edu.sax.DomNode;
import cn.seu.edu.sax.DomTree;
import cn.seu.edu.sax.Parser;

import java.util.ArrayList;

/**
 * Created by giles on 2015/4/19.
 */
public class TestSAXParser {

    public static void main(String[] args) {

//        new GWindow();
        Parser parser = new Parser();
        parser.setFile("group.xml");
        DomTree domTree = parser.parseAll();
        domTree.head.DFSPrintDom();
        domTree.body.DFSPrintDom();
        ArrayList<DomNode> res = parser.search("member");
        System.out.println("found");
        System.out.println(res.size());
    }
}
