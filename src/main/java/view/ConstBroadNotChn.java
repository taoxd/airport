package view;

import config.Constant;
import org.dom4j.Document;
import org.dom4j.Element;
import util.DOMUtils;
import util.SwingUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 常量广播词非中文实例
 *
 * @author ZYY
 */

public class ConstBroadNotChn extends JPanel {

    private static final long serialVersionUID = 1L;
    private TreePath treePath;
    private Element element;
    private Document document;


    public ConstBroadNotChn(TreePath treePath) {
        this.treePath = treePath;
        document = DOMUtils.getDocument(Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
        element = (Element) document.selectSingleNode("//resource[@value='" + treePath.getLastPathComponent().toString() + "']");


    }

    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 992, 796);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel label_2 = new JLabel("常量广播词:");
        label_2.setFont(new Font("宋体", Font.PLAIN, 16));
        label_2.setBounds(56, 70, 91, 36);
        this.add(label_2);

        JLabel constLabel = new JLabel();
        constLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        constLabel.setBounds(161, 70, 400, 36);
        constLabel.setText(treePath.getLastPathComponent().toString());
        this.add(constLabel);


        JLabel label = new JLabel("语种:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(106, 142, 54, 22);
        this.add(label);


        JLabel languageLabel = new JLabel();
        languageLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        languageLabel.setBounds(161, 142, 91, 22);
        languageLabel.setText(element.attributeValue("language"));
        this.add(languageLabel);


        JButton delButton = new JButton("删除");
        delButton.setForeground(Color.WHITE);
        delButton.setFont(new Font("宋体", Font.PLAIN, 16));
        delButton.setBackground(new Color(30, 144, 255));
        delButton.setBounds(150, 220, 93, 36);
        this.add(delButton);

        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //删除树节点
                JFrame jf = (JFrame) (getRootPane().getParent());
                SwingUtils.delNode(jf);

                //删除xml
                element.getParent().remove(element);
                //写xml文件
                try {
                    DOMUtils.writeXMLToFile(document, Constant.UPLOAD_RESOURCE_PATH + Constant.RESOURCE_NAME);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


        return this;

    }


}
