package view;

import config.Constant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Description: 添加常量广播词
 * @Author: taoxudong
 * @CreateDate: 2019/6/25 14:56
 * @Version: 1.0
 */
public class JFrameRun extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrameRun frame = new JFrameRun();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public JFrameRun() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 657, 331);
        contentPane = new JPanel();
        contentPane.setForeground(Color.BLACK);
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        //语种下拉框
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"Chn"}));
        comboBox.setFont(new Font("宋体", Font.PLAIN, 24));
        comboBox.setToolTipText("1");
        comboBox.setBounds(89, 36, 91, 36);
        contentPane.add(comboBox);

        //资源名称
        textField_2 = new JTextField();
        textField_2.setBounds(89, 101, 385, 36);
        contentPane.add(textField_2);
        textField_2.setColumns(10);


        //导入音频显示文件名
        JLabel lblBoardmp = new JLabel();
        lblBoardmp.setBounds(89, 149, 299, 15);
        contentPane.add(lblBoardmp);

        JButton btnNewButton = new JButton("导入音频");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(Constant.IMPORT_VOICE_OPEN_URL);
                int val = fc.showOpenDialog(null);    //文件打开对话框
                if (val == fc.APPROVE_OPTION) {
                    //正常选择文件
                    lblBoardmp.setText(fc.getSelectedFile().toString());
                } else {
                    //未正常选择文件，如选择取消按钮
                    lblBoardmp.setText("未选择文件");
                }
            }
        });

        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 12));
        btnNewButton.setBackground(new Color(30, 144, 255));
        btnNewButton.setForeground(new Color(240, 248, 255));
        btnNewButton.setBounds(503, 104, 91, 28);
        contentPane.add(btnNewButton);

        JLabel label = new JLabel("语种:");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(38, 49, 54, 15);
        contentPane.add(label);

        JLabel label_1 = new JLabel("资源:");
        label_1.setFont(new Font("宋体", Font.PLAIN, 16));
        label_1.setBounds(38, 109, 54, 15);
        contentPane.add(label_1);

        JButton button = new JButton("提交");
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(30, 144, 255));
        button.setFont(new Font("宋体", Font.PLAIN, 16));
        button.setBounds(89, 174, 93, 36);
        contentPane.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(112233);
            }
        });
    }
}
