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

	/////패널
	JPanel mainPane = new JPanel();
	JPanel bodyPane;
	JPanel attriPane;
	JPanel drawPane;
	/////버튼
	JButton btnNode;
	JButton btnNode2;
	JButton btnNew;
	JButton btnClose;
	JButton btnOpen;
	JButton btnSave;
	JButton btnSaveAs;
	/////메뉴
	JMenu viewMenu;
	JMenu editMenu;
	JMenu closeMenu;
	JMenu fileMenu;
	/////아이템
	JMenuItem closeItem;
	JMenuItem newItem;
	/////필드
	JTextField tfName;
	/////메뉴바
	JMenuBar menuBar;
	/////툴바
	JToolBar tBar;

	int t = -1; // 노드생성 버튼클릭수

	// 노드 사이즈
	private static final int WIDTH = 60;
	private static final int HEIGHT = 40;

	///// 노드를 저장
	static public Node selectedNode = null;
	static public Vector<Node> nodeSet = new Vector<Node>();

	int bX = 160, bY = 130;// 노드 초기위치

	public MindMapFrame() {
		super("마인드 맵");

		tfName = new JTextField(10);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileMenu = new JMenu("New");
		newItem = new JMenuItem("새로만들기");
		fileMenu.add(newItem);
		newItem.addActionListener(new Actionbutton());
		editMenu = new JMenu("Open");
		editMenu.add(new JMenuItem("열기"));
		viewMenu = new JMenu("Save");
		viewMenu.add(new JMenuItem("저장"));
		viewMenu.add(new JMenuItem("다른이름으로 저장"));
		closeMenu = new JMenu("Close");
		closeItem = new JMenuItem("닫기");
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

		///// 속성,마인드맵 전체프레임

		bodyPane = new JPanel();
		bodyPane.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints(); // 그리드 백 상수

		mainPane.add(bodyPane);
		bodyPane.setOpaque(true);
		bodyPane.setBackground(Color.WHITE);
		setSize(300, 300);
		setVisible(true);

		/////속성

		attriPane = new JPanel();
		attriPane.setPreferredSize(new Dimension(200, 200));
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.0;
		c.weighty = 1.0;
		bodyPane.add(attriPane, c);
		attriPane.setOpaque(true);

		attriPane.add(new JLabel("좌표          "));
		attriPane.add(new JTextField(10));
		attriPane.add(new JLabel("너비/높이"));
		attriPane.add(new JTextField(10));
		attriPane.add(new JLabel("노드  이름"));
		attriPane.add(tfName);

		///// 노드생성버튼
		btnNode = new JButton("노드생성");
		btnNode.addActionListener(new Actionbutton());
		btnNode.addMouseListener(new NodeMouseListener(null) {
			public void mousePressed(MouseEvent e) {
				t++;
			}
		});

		///// 노드이름변경버튼
		btnNode2 = new JButton("이름변경");
		btnNode2.addActionListener(new Actionbutton());
		attriPane.add(btnNode);
		attriPane.add(btnNode2);

		attriPane.setBackground(new Color(95, 165, 185, 200));
		setSize(300, 300);
		setVisible(true);

		///// 그리기 pane

		drawPane = new JPanel(null);
		c.weightx = 0.7;
		c.fill = GridBagConstraints.BOTH;
		bodyPane.add(drawPane, c);

		setSize(100, 100);
		setVisible(true);

		///// 툴바

		tBar = new JToolBar();
		btnNew = new JButton(new ImageIcon("./img/new.png"));
		btnOpen = new JButton(new ImageIcon("./img/open.png"));
		btnSave = new JButton(new ImageIcon("./img/save.png"));
		btnSaveAs = new JButton(new ImageIcon("./img/saveas.png"));
		btnClose = new JButton(new ImageIcon("./img/close.png"));

		///// 툽팁

		btnNew.setToolTipText("새로만들기");
		btnOpen.setToolTipText("파일열기");
		btnSave.setToolTipText("저장");
		btnSaveAs.setToolTipText("다른이름으로저장");
		btnClose.setToolTipText("닫기");

		btnClose.addActionListener(new CloseActionListener()); ///// 닫기
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

	///// 노드의 생성, 노드의 선택 , 파일 새로 열기 이벤트 (inner)

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
					b.setText("루트");
					b.setBackground(Color.gray);
				} else {
					b.setText("노드" + t);
					b.setBackground(Color.CYAN);
				}
				drawPane.add(b);

				repaint();
			} else if (ba.getSource() == btnNode2) {
				if (selectedNode == null)
					return;

				selectedNode.setText(tfName.getText());

			} else if (ba.getSource() == newItem || ba.getSource() == btnNew) {
				int result = JOptionPane.showConfirmDialog(null, "새문서를 만듭니다.",
						"닫기", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.CLOSED_OPTION) {
					// 사용자가 "예"나 "아니오"의 선택없이 다이얼로그창을닫은 경우
					return; // not exited
				} else if (result == JOptionPane.YES_OPTION) {
					// 사용자가 "예"를 선택한 경우
					// 새문서
					for (int i = 0; i < nodeSet.size(); i++) {
						drawPane.remove(nodeSet.get(i));
					}
					nodeSet.clear(); // 노드 초기화
					bX = 160;
					bY = 130;
					t = -1;
					repaint();

				} else { // 사용자가 "아니오"를 선택한 경우
					return; // not exited

				}
			}

		}

	}

	///// 프로그램 닫기 이벤트

	class CloseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnClose || e.getSource() == closeItem) {

				int result = JOptionPane.showConfirmDialog(null,
						"프로그램을 종료합니다.", "닫기", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.CLOSED_OPTION) {
					// 사용자가 "예"나 "아니오"의 선택없이 다이얼로그창을닫은 경우
					return; // not exited
				} else if (result == JOptionPane.YES_OPTION) {
					// 사용자가 "예"를 선택한 경우
					System.exit(0);
				} else { // 사용자가 "아니오"를 선택한 경우
					return; // not exited
				}

			}

		}

	}
	
	/////메인
	static public void main(String[] arg) {
		new MindMapFrame();
	}
}

///// 노드의 이동 및 삭제 이벤트

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