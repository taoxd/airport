package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Description: 首页
 * @Author: taoxudong
 * @CreateDate: 2019/6/26 13:55
 * @Version: 1.0
 */
public class Index extends JPanel {

    public Index() {
        this.setName("index");
    }

    /**
     * Create the frame.
     */
    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 977, 778);
        this.setBackground(new Color(255, 255, 255));
        this.setForeground(new Color(255, 255, 255));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        //setContentPane(contentPane);

        JLabel lblXml = new JLabel("XML命名:");
        lblXml.setFont(new Font("宋体", Font.PLAIN, 16));
        lblXml.setBounds(239, 44, 81, 15);
        this.add(lblXml);

        JComboBox xmlComboBox = new JComboBox();
        xmlComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        xmlComboBox.setModel(new DefaultComboBoxModel(new String[]{"登机广播"}));
        xmlComboBox.setBounds(318, 36, 105, 31);
        this.add(xmlComboBox);

        JButton addTypeButton = new JButton("新增类别");
        addTypeButton.setFont(new Font("宋体", Font.PLAIN, 16));
        addTypeButton.setForeground(new Color(255, 255, 255));
        addTypeButton.setBackground(new Color(30, 144, 255));
        addTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BulletBox frame = new BulletBox();
                frame.setVisible(true);
            }
        });
        addTypeButton.setBounds(564, 37, 104, 31);
        this.add(addTypeButton);

        JLabel chnLable = new JLabel("广播词中文名-Chn:");
        chnLable.setFont(new Font("宋体", Font.PLAIN, 16));
        chnLable.setBounds(167, 105, 142, 15);
        this.add(chnLable);

        JTextField chnField = new JTextField();
        chnField.setBounds(318, 98, 105, 31);
        this.add(chnField);
        chnField.setColumns(10);


        JLabel engLable = new JLabel("英文别名-Eng:");
        engLable.setFont(new Font("宋体", Font.PLAIN, 16));
        engLable.setBounds(449, 105, 105, 15);
        this.add(engLable);

        JTextField engField = new JTextField();
        engField.setBounds(563, 98, 105, 31);
        this.add(engField);
        engField.setColumns(10);

        JLabel label = new JLabel("语种选择:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(234, 169, 72, 15);
        this.add(label);

        JComboBox languageCcomboBox = new JComboBox();
        languageCcomboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        languageCcomboBox.setModel(new DefaultComboBoxModel(new String[]{"Chn","Eng"}));
        languageCcomboBox.setBounds(318, 161, 225, 31);
        this.add(languageCcomboBox);

        JLabel label_1 = new JLabel("固定广播词:");
        label_1.setFont(new Font("宋体", Font.PLAIN, 16));
        label_1.setBounds(217, 231, 88, 31);
        this.add(label_1);

        JTextArea constBroadList = new JTextArea();
        constBroadList.setBackground(SystemColor.control);
        constBroadList.setForeground(new Color(0, 0, 0));
        constBroadList.setFont(new Font("宋体", Font.PLAIN, 16));
        constBroadList.setLineWrap(true);
        constBroadList.setBounds(318, 235, 340, 192);
        this.add(constBroadList);

        JLabel label_2 = new JLabel("可变广告词:");
        label_2.setFont(new Font("宋体", Font.PLAIN, 16));
        label_2.setBounds(216, 464, 93, 31);
        this.add(label_2);

        JComboBox variableBroadComboBox = new JComboBox();
        variableBroadComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        variableBroadComboBox.setModel(new DefaultComboBoxModel(new String[]{"城市"}));
        variableBroadComboBox.setBounds(318, 464, 225, 31);
        this.add(variableBroadComboBox);

        JLabel label_3 = new JLabel("模版内容:");
        label_3.setFont(new Font("宋体", Font.PLAIN, 16));
        label_3.setBounds(232, 534, 72, 25);
        this.add(label_3);

        JTextField templateContent = new JTextField();
        templateContent.setHorizontalAlignment(SwingConstants.RIGHT);
        templateContent.setEditable(false);
        templateContent.setFont(new Font("宋体", Font.PLAIN, 16));
        templateContent.setBounds(317, 534, 340, 86);
        this.add(templateContent);
        templateContent.setColumns(20);

        JButton resetButton = new JButton("重置");
        resetButton.setForeground(new Color(255, 255, 255));
        resetButton.setBackground(new Color(30, 144, 255));
        resetButton.setFont(new Font("宋体", Font.PLAIN, 16));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        resetButton.setBounds(316, 651, 93, 31);
        this.add(resetButton);

        JButton submitButton = new JButton("提交");
        submitButton.setForeground(new Color(255, 255, 255));
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        submitButton.setFont(new Font("宋体", Font.PLAIN, 16));
        submitButton.setBounds(564, 651, 93, 31);
        this.add(submitButton);

        return this;
    }


}
