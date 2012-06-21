import java.awt.Color;

import javax.swing.JLabel;

	class Node extends JLabel {
		private int prevX, prevY;
		
		
		public Node(String text) {
			super(text);
			
			NodeMouseListener listener = new NodeMouseListener(this);
			this.addMouseListener(listener);
			this.addMouseMotionListener(listener);
			this.setHorizontalAlignment(JLabel.CENTER);
		}
		
		public void select() {
			setBackground(Color.YELLOW);
			MindMapFrame.selectedNode = this;
		}
		public void deselect() {
			setBackground(Color.CYAN);
			MindMapFrame.selectedNode = null;
		}
		
		

		public int getPrevX() {
			return prevX;
		}

		public void setPrevX(int prevX) {
			this.prevX = prevX;
		}

		public int getPrevY() {
			return prevY;
		}

		public void setPrevY(int prevY) {
			this.prevY = prevY;
		}
	}