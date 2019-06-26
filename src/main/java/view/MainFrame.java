package view;

import config.Constant;
import util.DOMUtils;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * @Description: 主菜单
 * @Author: taoxudong
 * @CreateDate: 2019/6/26 11:14
 * @Version: 1.0
 */
public class MainFrame extends JFrame {
    private JPanel rightPanel;

    public MainFrame(String title) {
        super(title);
        init();
    }

    public void init() {

        Container container = this.getContentPane();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(Constant.DONG_HANG_KE_CANG);//东航客舱
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode(Constant.HOME_PAGE);//主页

        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode(Constant.TEMPLATE_LIST);//模版列表

        DefaultMutableTreeNode child3 = new DefaultMutableTreeNode(Constant.RESOURCE_ADD);//资源添加
        DefaultMutableTreeNode child31 = new DefaultMutableTreeNode(Constant.CONSTANT_BROAD);//常量广播词
        DefaultMutableTreeNode child32 = new DefaultMutableTreeNode(Constant.VARIABLE_BROAD);//变量广播词

        DefaultMutableTreeNode child4 = new DefaultMutableTreeNode(Constant.PREVIEW_RESOURCE_XML);//资源XML预览
        DefaultMutableTreeNode child5 = new DefaultMutableTreeNode(Constant.EXPORT);//导出
        root.add(child1);
        root.add(child2);
        root.add(child3);
        root.add(child4);
        root.add(child5);
        child3.add(child31);
        child3.add(child32);
        JTree tree = new JTree(root);
        // 设置树显示根节点句柄
        tree.setShowsRootHandles(true);

        tree.setPreferredSize(new Dimension(200, 0));
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                treeSelect(e);
            }
        });

        // 创建滚动面板，包裹树（因为树节点展开后可能需要很大的空间来显示，所以需要用一个滚动面板来包裹）
        JScrollPane scrollPane = new JScrollPane(tree);

        container.add(scrollPane, BorderLayout.WEST);

        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.pink);
        //p.setPreferredSize(new Dimension(180, 400));
        container.add(rightPanel, BorderLayout.CENTER);
        this.setBounds(400, 100, 1000, 800);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }

    private void treeSelect(TreeSelectionEvent e) {
        rightPanel.removeAll();
        String selectLastPathName = e.getPath().getLastPathComponent().toString();
        if (Constant.CONSTANT_BROAD.equals(selectLastPathName)) {
            JPanel panel = new ConstantBroad().init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (Constant.VARIABLE_BROAD.equals(selectLastPathName)) {
            JPanel panel = new VariableBroad().init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);
        } else if (Constant.HOME_PAGE.equals(selectLastPathName)) {
            JPanel panel = new Index().init();
            panel.setBackground(Color.GREEN);
            panel.setBounds(5, 5, 800, 800);
            rightPanel.add(panel);

        } else if (Constant.PREVIEW_RESOURCE_XML.equals(selectLastPathName)) {

            String document = DOMUtils.documentToString(DOMUtils.getDocument(Constant.IMPORT_VOICE_OPEN_URL + "resource.xml"), "utf8");
            JTextArea jTextArea = new JTextArea(document);
            jTextArea.setEditable(false);
            jTextArea.setBackground(Color.GREEN);
            jTextArea.setBounds(5, 5, 800, 800);
            rightPanel.add(jTextArea);
        } else {
            JLabel l = new JLabel(e.getPath().getLastPathComponent().toString());
            l.setBounds(5, 190, 250, 20);
            rightPanel.add(l);

        }

        rightPanel.repaint();
    }

}
