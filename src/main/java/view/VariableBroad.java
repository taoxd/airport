package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 变量广播词
 *
 * @author ZYY
 */
public class VariableBroad {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;

    public VariableBroad() {


    }

    public JPanel init() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 549, 376);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        //setContentPane(contentPane);

        JLabel label = new JLabel("新增变量类别:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(47, 72, 111, 43);
        contentPane.add(label);

        textField = new JTextField();
        textField.setBounds(159, 72, 206, 43);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("展示类型:");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel.setBounds(79, 136, 79, 43);
        contentPane.add(lblNewLabel);

        JButton button = new JButton("提交");
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(new Color(255, 255, 255));
        button.setFont(new Font("宋体", Font.PLAIN, 16));
        button.setBounds(202, 246, 99, 36);
        contentPane.add(button);

        JComboBox comboBox = new JComboBox();
        comboBox.setBackground(new Color(255, 255, 255));
        comboBox.setFont(new Font("宋体", Font.PLAIN, 16));
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"下拉框"}));
        comboBox.setBounds(159, 141, 206, 38);
        contentPane.add(comboBox);

        return contentPane;
    }

}
