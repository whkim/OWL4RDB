import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class MindMapFrame extends JFrame {

	/////�г�
	JPanel mainPane = new JPanel();
	JPanel bodyPane;
	JPanel attriPane;
	JPanel drawPane;
	/////��ư
	JButton btnNode;
	JButton btnNode2;
	JButton btnNew;
	JButton btnClose;
	JButton btnOpen;
	JButton btnSave;
	JButton btnSaveAs;
	/////�޴�
	JMenu viewMenu;
	JMenu editMenu;
	JMenu closeMenu;
	JMenu fileMenu;
	/////������
	JMenuItem closeItem;
	JMenuItem newItem;
	/////�ʵ�
	JTextField tfName;
	/////�޴���
	JMenuBar menuBar;
	/////����
	JToolBar tBar;

	int t = -1; // ������ ��ưŬ����

	// ��� ������
	private static final int WIDTH = 60;
	private static final int HEIGHT = 40;

	///// ��带 ����
	static public Node selectedNode = null;
	static public Vector<Node> nodeSet = new Vector<Node>();

	int bX = 160, bY = 130;// ��� �ʱ���ġ

	public MindMapFrame() {
		super("���ε� ��");

		tfName = new JTextField(10);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileMenu = new JMenu("New");
		newItem = new JMenuItem("���θ����");
		fileMenu.add(newItem);
		newItem.addActionListener(new Actionbutton());
		editMenu = new JMenu("Open");
		editMenu.add(new JMenuItem("����"));
		viewMenu = new JMenu("Save");
		viewMenu.add(new JMenuItem("����"));
		viewMenu.add(new JMenuItem("�ٸ��̸����� ����"));
		closeMenu = new JMenu("Close");
		closeItem = new JMenuItem("�ݱ�");
		closeItem.addActionListener(new CloseActionListener());
		closeMenu.add(closeItem);

		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(closeMenu);
		setJMenuBar(menuBar);

		mainPane.setLayout(new BorderLayout());

		add(mainPane);
		setSize(300, 300);
		setVisible(true);

		///// �Ӽ�,���ε�� ��ü������

		bodyPane = new JPanel();
		bodyPane.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints(); // �׸��� �� ���

		mainPane.add(bodyPane);
		bodyPane.setOpaque(true);
		bodyPane.setBackground(Color.WHITE);
		setSize(300, 300);
		setVisible(true);

		/////�Ӽ�

		attriPane = new JPanel();
		attriPane.setPreferredSize(new Dimension(200, 200));
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.0;
		c.weighty = 1.0;
		bodyPane.add(attriPane, c);
		attriPane.setOpaque(true);

		attriPane.add(new JLabel("��ǥ          "));
		attriPane.add(new JTextField(10));
		attriPane.add(new JLabel("�ʺ�/����"));
		attriPane.add(new JTextField(10));
		attriPane.add(new JLabel("���  �̸�"));
		attriPane.add(tfName);

		///// ��������ư
		btnNode = new JButton("������");
		btnNode.addActionListener(new Actionbutton());
		btnNode.addMouseListener(new NodeMouseListener(null) {
			public void mousePressed(MouseEvent e) {
				t++;
			}
		});

		///// ����̸������ư
		btnNode2 = new JButton("�̸�����");
		btnNode2.addActionListener(new Actionbutton());
		attriPane.add(btnNode);
		attriPane.add(btnNode2);

		attriPane.setBackground(new Color(95, 165, 185, 200));
		setSize(300, 300);
		setVisible(true);

		///// �׸��� pane

		drawPane = new JPanel(null);
		c.weightx = 0.7;
		c.fill = GridBagConstraints.BOTH;
		bodyPane.add(drawPane, c);

		setSize(100, 100);
		setVisible(true);

		///// ����

		tBar = new JToolBar();
		btnNew = new JButton(new ImageIcon("./img/new.png"));
		btnOpen = new JButton(new ImageIcon("./img/open.png"));
		btnSave = new JButton(new ImageIcon("./img/save.png"));
		btnSaveAs = new JButton(new ImageIcon("./img/saveas.png"));
		btnClose = new JButton(new ImageIcon("./img/close.png"));

		///// ����

		btnNew.setToolTipText("���θ����");
		btnOpen.setToolTipText("���Ͽ���");
		btnSave.setToolTipText("����");
		btnSaveAs.setToolTipText("�ٸ��̸���������");
		btnClose.setToolTipText("�ݱ�");

		btnClose.addActionListener(new CloseActionListener()); ///// �ݱ�
		btnNew.addActionListener(new Actionbutton());
		tBar.add(btnNew);
		tBar.add(btnOpen);
		tBar.add(btnSave);
		tBar.add(btnSaveAs);
		tBar.add(btnClose);

		mainPane.add(tBar, BorderLayout.NORTH);
		setVisible(true);

		setSize(700, 500);
		setVisible(true);
	}

	///// ����� ����, ����� ���� , ���� ���� ���� �̺�Ʈ (inner)

	private class Actionbutton implements ActionListener {

		public void actionPerformed(ActionEvent ba) {

			if (ba.getSource() == btnNode) {
				Node b = new Node("");
				nodeSet.add(b);

				bX += 20;
				bY += 20;

				b.setOpaque(true);
				b.setLocation(bX, bY);
				b.setSize(WIDTH, HEIGHT);

				if (t == 0) {
					b.setText("��Ʈ");
					b.setBackground(Color.gray);
				} else {
					b.setText("���" + t);
					b.setBackground(Color.CYAN);
				}
				drawPane.add(b);

				repaint();
			} else if (ba.getSource() == btnNode2) {
				if (selectedNode == null)
					return;

				selectedNode.setText(tfName.getText());

			} else if (ba.getSource() == newItem || ba.getSource() == btnNew) {
				int result = JOptionPane.showConfirmDialog(null, "�������� ����ϴ�.",
						"�ݱ�", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.CLOSED_OPTION) {
					// ����ڰ� "��"�� "�ƴϿ�"�� ���þ��� ���̾�α�â������ ���
					return; // not exited
				} else if (result == JOptionPane.YES_OPTION) {
					// ����ڰ� "��"�� ������ ���
					// ������
					for (int i = 0; i < nodeSet.size(); i++) {
						drawPane.remove(nodeSet.get(i));
					}
					nodeSet.clear(); // ��� �ʱ�ȭ
					bX = 160;
					bY = 130;
					t = -1;
					repaint();

				} else { // ����ڰ� "�ƴϿ�"�� ������ ���
					return; // not exited

				}
			}

		}

	}

	///// ���α׷� �ݱ� �̺�Ʈ

	class CloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnClose || e.getSource() == closeItem) {

				int result = JOptionPane.showConfirmDialog(null,
						"���α׷��� �����մϴ�.", "�ݱ�", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.CLOSED_OPTION) {
					// ����ڰ� "��"�� "�ƴϿ�"�� ���þ��� ���̾�α�â������ ���
					return; // not exited
				} else if (result == JOptionPane.YES_OPTION) {
					// ����ڰ� "��"�� ������ ���
					System.exit(0);
				} else { // ����ڰ� "�ƴϿ�"�� ������ ���
					return; // not exited
				}

			}

		}

	}
	
	/////����
	static public void main(String[] arg) {
		new MindMapFrame();
	}
}

///// ����� �̵� �� ���� �̺�Ʈ

class NodeMouseListener implements MouseMotionListener, MouseListener {
	private Node node;

	static public int x;
	static public int y;

	public NodeMouseListener(Node node) {
		this.node = node;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

		x = node.getParent().getMousePosition().x - node.getPrevX();
		y = node.getParent().getMousePosition().y - node.getPrevY();

		node.setLocation(x, y);

		System.out.println(x + "," + y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		node.setPrevX(e.getX());
		node.setPrevY(e.getY());
		if (MindMapFrame.selectedNode == null) {
			node.select();
		} else if (MindMapFrame.selectedNode != node) {
			MindMapFrame.selectedNode.deselect();
			node.select();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}