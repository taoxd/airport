package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import java.awt.Font;
/**
 * 树
 * @author ZYY
 *
 */
public class Tree extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTree tree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tree frame = new Tree();
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
	public Tree() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 934, 699);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		tree = new JTree();
		tree.setShowsRootHandles(true);
		tree.setFont(new Font("宋体", Font.PLAIN, 16));
		tree.setEditable(true);
		tree.setBounds(10, 10, 243, 529);
		contentPane.add(tree);
		

	}
}
