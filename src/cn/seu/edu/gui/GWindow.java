package cn.seu.edu.gui;


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
//    public JFrame this;
//    public JFileChooser inChooser;
//    public JFileChooser outChooser;
    public JTextField pathField;
    public JTextField keysField;
//    public JButton selectButton;
    public JButton searchButton;
//    public JButton submitButton;
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

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // search
                
            }
        });

        getDomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // parse dom
            }
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

    public void setLayout() {
        constraints.gridwidth = 24;
        constraints.gridheight = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;

//        rows.add(new JPanel());
        layout.setConstraints(rows.get(0), constraints);
//        rows.add(new JPanel());
        constraints.gridy = 3;
        layout.setConstraints(rows.get(1), constraints);
//        rows.add(new JPanel());
        constraints.gridy = 5;
        layout.setConstraints(rows.get(2), constraints);
//        rows.add(new JPanel());
        constraints.gridy = 7;
        constraints.gridheight = 4;
//        constraints.gridwidth = 24;
        layout.setConstraints(rows.get(3), constraints);
//        constraints.gridy = 9;
//        rows.add(new JPanel());
//        layout.setConstraints(rows.get(4), constraints);
    }

//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == selectButton) {
//            inChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            int ret = inChooser.showOpenDialog(this);
//            if (ret == JFileChooser.APPROVE_OPTION) {
//                pathField.setText(inChooser.getSelectedFile().getParent());
//            }
//        }
//    }


}
