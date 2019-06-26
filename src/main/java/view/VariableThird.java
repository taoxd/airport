package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 变量广播词第三极
 * @author ZYY
 *
 */
public class VariableThird extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VariableThird frame = new VariableThird();
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
	public VariableThird() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 967, 569);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Chn"}));
		comboBox.setFont(new Font("宋体", Font.PLAIN, 24));
		comboBox.setToolTipText("1");
		comboBox.setBounds(223, 167, 91, 36);
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(223, 236, 385, 36);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\u5BFC\u5165\u97F3\u9891");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 12));
		btnNewButton.setBackground(new Color(30, 144, 255));
		btnNewButton.setForeground(new Color(240, 248, 255));
		btnNewButton.setBounds(629, 239, 91, 28);
		contentPane.add(btnNewButton);
		
		JLabel label = new JLabel("新增语种:");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(141, 174, 91, 29);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("新增语种广播词:");
		label_1.setFont(new Font("宋体", Font.PLAIN, 16));
		label_1.setBounds(93, 238, 120, 36); 
		contentPane.add(label_1);
		
		JLabel lblBoardmp = new JLabel("board.mp3 \uFF08\u5BFC\u5165\u97F3\u9891\u540E\u663E\u793A\u5BFC\u5165\u97F3\u9891\u6587\u4EF6\u540D\uFF09");
		lblBoardmp.setBounds(223, 283, 299, 15);
		contentPane.add(lblBoardmp);
		
		JButton button = new JButton("\u63D0\u4EA4");
		button.setForeground(new Color(255, 255, 255));
		button.setBackground(new Color(30, 144, 255));
		button.setFont(new Font("宋体", Font.PLAIN, 16));
		button.setBounds(501, 332, 93, 36);
		contentPane.add(button);
		
		JButton button_1 = new JButton("删除");
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("宋体", Font.PLAIN, 16));
		button_1.setBackground(new Color(30, 144, 255));
		button_1.setBounds(277, 332, 93, 36);
		contentPane.add(button_1);
		
		JLabel label_2 = new JLabel("所属类别:");
		label_2.setFont(new Font("宋体", Font.PLAIN, 16));
		label_2.setBounds(141, 58, 91, 28);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("变量广告词:");
		label_3.setFont(new Font("宋体", Font.PLAIN, 16));
		label_3.setBounds(126, 111, 91, 28);
		contentPane.add(label_3);
	}

}
