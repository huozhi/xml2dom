import cn.seu.edu.sax.DomNode;
import cn.seu.edu.sax.DomTree;
import cn.seu.edu.sax.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by giles on 2015/4/20.
 */
public class GWindow extends JFrame {

    public JTextField pathField;
    public JTextField keysField;
    public JButton searchButton;
    public JButton getDomButton;
    public JTextArea logArea;

    public ArrayList<JPanel> rows;
    public Container pane;
    public GridBagLayout layout;
    public GridBagConstraints constraints;

    public Parser parser;

    public GWindow() {
        init();
    }

    public void reinit() {
        parser = new Parser();

    }

    public void init() {
        // window init
        parser = new Parser();
        this.setTitle("Fast SAX Parser");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Dimension windowSzie = this.getToolkit().getScreenSize();
        this.setBounds(
                windowSzie.width / 2 - 300,
                windowSzie.height / 2 - 250,
                600,
                500);
        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        this.setLayout(layout);
        pane = this.getContentPane();

        rows = new ArrayList<JPanel>();

        this.addComponents();
        this.setLayout();

        this.setVisible(true);
    }

    public void addComponents() {
        pathField = new JTextField(24);
        keysField = new JTextField(24);

        rows.add(new JPanel());
        rows.get(0).add(new JLabel("path"));
        rows.get(0).add(pathField);
        pathField.setText("source.xml");

        rows.add(new JPanel());
        rows.get(1).add(new JLabel("find"));
        rows.get(1).add(keysField);
        keysField.setText("book");

        rows.add(new JPanel());

        searchButton = new JButton("Find");
        getDomButton = new JButton("Dom Tree");

        searchButton.addActionListener(e -> {
            // search
            new Thread(() -> {
                searchWorker();
            }).start();
        });

        getDomButton.addActionListener(e -> {
            // parse dom
            new Thread(() -> {
                parseDomWorker();
            }).start();
        });

        rows.add(new JPanel());
        rows.get(2).add(searchButton);
        rows.get(2).add(getDomButton);

        logArea = new JTextArea(12, 40);
        rows.add(new JPanel());
        rows.get(3).add(logArea);


        for (int i = 0; i < rows.size(); i++)
            pane.add(rows.get(i));
    }

    protected void searchWorker() {
        reinit();
        String path = pathField.getText();
        String key = keysField.getText();
        parser.setFile(path);
        parser.parseAll();
        ArrayList<DomNode> res = parser.search(key);
        StringBuilder sb = new StringBuilder();
        sb.append("Count of Dom Node Found: ");
        sb.append(res.size());
        sb.append("\n");
        for (DomNode n : res) {
            sb.append(n.DFSPrintDom());
            sb.append("\n");
        }

        logArea.setText(sb.toString());
    }

    protected void parseDomWorker() {
        reinit();
        String path = pathField.getText();
        keysField.setText("------------");
        parser.setFile(path);
        DomTree domTree = parser.parseAll();

        logArea.setText(domTree.DFSPrintDom());
    }



    protected void setLayout() {
        constraints.gridwidth = 24;
        constraints.gridheight = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;

        layout.setConstraints(rows.get(0), constraints);
        constraints.gridy = 3;
        layout.setConstraints(rows.get(1), constraints);
        constraints.gridy = 5;
        layout.setConstraints(rows.get(2), constraints);
        constraints.gridy = 7;
        constraints.gridheight = 4;
        layout.setConstraints(rows.get(3), constraints);
    }
}
