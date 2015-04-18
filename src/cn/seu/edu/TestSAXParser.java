package cn.seu.edu;

import cn.seu.edu.sax.DomNode;
import cn.seu.edu.sax.Parser;

import java.io.File;

/**
 * Created by giles on 2015/4/19.
 */
public class TestSAXParser {

    public static void main(String[] args) {
        //Parser parser = new Parser();
        Parser parser = new Parser();
        DomNode body = parser.parseAll(new File("source.xml"));
        parser.DFSPrintDom(body);
    }
}
