package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
* @Description:    首页
* @Author:         taoxudong
* @CreateDate:     2019/6/26 13:55
* @Version:        1.0
*/
public class Index   {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Index(){};
	/**
	 * Create the frame.
	 */
	public JPanel init() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 977, 778);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		//setContentPane(contentPane);
		
		JLabel lblXml = new JLabel("XML命名:");
		lblXml.setFont(new Font("宋体", Font.PLAIN, 16));
		lblXml.setBounds(239, 44, 81, 15);
		contentPane.add(lblXml);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"登机广播"}));
		comboBox.setBounds(318, 36, 105, 31);
		contentPane.add(comboBox);
		
		JLabel lblchn = new JLabel("广播词中文名-Chn:");
		lblchn.setFont(new Font("宋体", Font.PLAIN, 16));
		lblchn.setBounds(167, 105, 142, 15);
		contentPane.add(lblchn);
		
		textField = new JTextField();
		textField.setBounds(318, 98, 105, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("新增类别");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 16));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(30, 144, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BulletBox frame = new BulletBox();
				frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(564, 37, 104, 31);
		contentPane.add(btnNewButton);
		
		JLabel lbleng = new JLabel("英文别名-Eng:");
		lbleng.setFont(new Font("宋体", Font.PLAIN, 16));
		lbleng.setBounds(449, 105, 105, 15);
		contentPane.add(lbleng);
		
		textField_1 = new JTextField();
		textField_1.setBounds(563, 98, 105, 31);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label = new JLabel("语种选择:");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(234, 169, 72, 15);
		contentPane.add(label);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("宋体", Font.PLAIN, 16));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Chn"}));
		comboBox_1.setBounds(318, 161, 225, 31);
		contentPane.add(comboBox_1);
		
		JLabel label_1 = new JLabel("固定广播词:");
		label_1.setFont(new Font("宋体", Font.PLAIN, 16));
		label_1.setBounds(217, 231, 88, 31);
		contentPane.add(label_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);
		textArea.setForeground(new Color(0, 0, 0));
		textArea.setFont(new Font("宋体", Font.PLAIN, 16));
		textArea.setLineWrap(true);
		textArea.setBounds(318, 235, 340, 192);
		contentPane.add(textArea);
		
		JLabel label_2 = new JLabel("可变广告词:");
		label_2.setFont(new Font("宋体", Font.PLAIN, 16));
		label_2.setBounds(216, 464, 93, 31);
		contentPane.add(label_2);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setFont(new Font("宋体", Font.PLAIN, 16));
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"城市"}));
		comboBox_2.setBounds(318, 464, 225, 31);
		contentPane.add(comboBox_2);
		
		JLabel label_3 = new JLabel("模版内容:");
		label_3.setFont(new Font("宋体", Font.PLAIN, 16));
		label_3.setBounds(232, 534, 72, 25);
		contentPane.add(label_3);
		
		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_2.setEditable(false);
		textField_2.setFont(new Font("宋体", Font.PLAIN, 16));
		textField_2.setBounds(317, 534, 340, 86);
		contentPane.add(textField_2);
		textField_2.setColumns(20);
		
		JButton btnNewButton_1 = new JButton("重置");
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(30, 144, 255));
		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(316, 651, 93, 31);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("提交");
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2.setBackground(new Color(30, 144, 255));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 16));
		btnNewButton_2.setBounds(564, 651, 93, 31);
		contentPane.add(btnNewButton_2);

		return contentPane;
	}
}
