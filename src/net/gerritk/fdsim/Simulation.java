package net.gerritk.fdsim;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import net.gerritk.fdsim.entities.Entity;
import net.gerritk.fdsim.gui.Button;
import net.gerritk.fdsim.gui.objects.*;
import net.gerritk.util.*;

public class Simulation extends JPanel implements Runnable {
	private static final long serialVersionUID = 3904671781449439735L;
	
	public static final String VERSION = "0.0.1 DEV", COPY = "(c) 2012 - K.Design - Gerrit Kaul - Feuerwehr Braunschweig";
	public static final int MODE_EXPERT = 0, MODE_SIMPLE = 1;
	
	private static Simulation instance;
	private static Playground playground;
	private static OwnFont clockFont;
	private static long simTime;
	
	private static JFrame frame;
	private static Map<RenderingHints.Key, Object> renderMap;
	private static int mode;
	private static boolean paused;
	
	private static KeyHandler keyHandler;
	private static MouseHandler mouseHandler;
	private static ButtonHandler buttonHandler;
	private static FrameHandler frameHandler;
	
	private static boolean keyControl;
	private static boolean keyRotatePlus;
	private static boolean keyRotateMinus;
	
	// GUI
	public static ArrayList<Button> buttons = new ArrayList<Button>();
	private static BottomBar bottomBar;
	
	public Simulation(Dimension d, int mode) {
		setMode(mode);
		simTime = -60 * 60 * 1000;
		
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		buttonHandler = new ButtonHandler();
		frameHandler = new FrameHandler();
		
		// FONT
		clockFont = new OwnFont("DS-DIGIT.TTF", Font.TRUETYPE_FONT);
		
		renderMap = new HashMap<RenderingHints.Key, Object>();
		renderMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderMap.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		renderMap.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		renderMap.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		renderMap.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		setPreferredSize(d);
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point wp = new Point((int) (screenSize.getWidth() - d.getWidth()) / 2, (int) (screenSize.getHeight() - d.getHeight()) / 2);
		
		// GUI
		bottomBar = new BottomBar(0, 30);
		
		frame = new JFrame("Feuerwehr Planspiel Simulation " + VERSION);
		frame.addKeyListener(keyHandler);
		frame.addComponentListener(frameHandler);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		frame.pack();
		frame.setLocation(wp);		
		frame.setMinimumSize(new Dimension(640 + frame.getInsets().left + frame.getInsets().right, 480 + frame.getInsets().top + frame.getInsets().bottom));
	}
	
	public static void reset() {
		playground = new Playground(playground.getTitle(), playground.getSize(), playground.getStart());
	}
	
	public void run() {
		long lastRunned = System.currentTimeMillis();
		long delta = 0;

		// TODO PLAYGROUND
		playground = new Playground("Test", new Dimension(1024, 512), new Point(100, 200));
		
		while(frame.isVisible()) {
			// Calculate Delta
			delta = System.currentTimeMillis() - lastRunned;
						
			if(!isPaused()) {
				if(playground != null) {
					playground.update(delta);
				}
				
				simTime += System.currentTimeMillis() - lastRunned;
				
				keyHandler.control();
			}
			
			// GUI
			bottomBar.update(delta);
			
			repaint();
			lastRunned = System.currentTimeMillis();
			
			try {
				Thread.sleep(1000 / 100);
			} catch(InterruptedException e) {
				// Nothing?
			}
		}
	}
	
	public void paintComponent(Graphics gra) {
		Graphics2D g = (Graphics2D) gra;
		g.setRenderingHints(renderMap);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// PLAYGROUND
		if(playground != null) {
			playground.draw(g);
		}
		
		// GUI
		bottomBar.draw(g);
		
		if(isPaused()) {
			String paused = "Pausiert";
			String pausedInfo = "Drücken Sie 'P' oder den Pausieren-Knopf zum Fortfahren.";
			
			GraphicsUtil.setAlpha(g, 0.4f);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			GraphicsUtil.setAlpha(g, 1);
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", Font.BOLD, 24));
			g.drawString(paused, (getWidth() - StringUtil.getWidth(paused, g)) / 2, (getHeight() - StringUtil.getHeight(paused, g)) / 2);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", Font.ITALIC, 14));
			g.drawString(pausedInfo, (getWidth() - StringUtil.getWidth(pausedInfo, g)) / 2, (getHeight() + StringUtil.getHeight(pausedInfo, g)) / 2);
		}
		
		// COPY
		g.setColor(Color.GRAY);
		g.setFont(new Font("Verdana", Font.PLAIN, 8));
		g.drawString(COPY, getWidth() - StringUtil.getWidth(COPY, g) - 2, StringUtil.getHeight(COPY, g));
	}
	
	/*
	 * KeyHandler
	 */	
	public class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
				keyControl = true;
			} else if(e.getKeyCode() == KeyEvent.VK_Q) {
				keyRotatePlus = true;
			} else if(e.getKeyCode() == KeyEvent.VK_E) {
				keyRotateMinus = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_P) {
				setPaused(!isPaused());
			} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				frame.dispose();
			} else if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
				keyControl = false;
			} else if(e.getKeyCode() == KeyEvent.VK_Q) {
				keyRotatePlus = false;
			} else if(e.getKeyCode() == KeyEvent.VK_E) {
				keyRotateMinus = false;
			}
			
			if(keyControl) {
				if(e.getKeyChar() >= 48 && e.getKeyChar() <= 57) {
					int mode = e.getKeyChar() - 49;			
					setMode(mode == -1 ? 9 : mode);
				}
			}
		}
		
		public void control() {
			if((keyRotatePlus || keyRotateMinus) && playground.getSelectedEntity() != null) {
				double r = (keyRotateMinus ? -0.5 : 0.5); 
				if(keyControl) {
					r = 45 - playground.getSelectedEntity().getRotation() % 45;
					
					playground.getSelectedEntity().setRotation(playground.getSelectedEntity().getRotation() + (keyRotateMinus ? r - 90 : r));
					keyRotatePlus = false;
					keyRotateMinus = false;
				} else {
					playground.getSelectedEntity().setRotation(playground.getSelectedEntity().getRotation() + r);
				}
			}
		}
	}
	
	/*
	 * MouseHandler
	 */
	public class MouseHandler extends MouseAdapter {
		private Point mouse;
		private Point lastDrag;
		private boolean mousebutton[] = new boolean[3];
		
		@Override
		public void mouseMoved(MouseEvent e) {
			mouse = e.getPoint();
			lastDrag = mouse;
        	
			for(Button b : buttons) {
	        	if(b.contains(mouse)) {
	        		b.setHover(true);
	        	} else if(b.isHover()) {
	        		b.setHover(false);
	        	}
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			Point drag = e.getPoint();
			
			if(mousebutton[2] && !isPaused()) {
				playground.setOffsetX(playground.getOffsetX() + drag.x - lastDrag.x);
				playground.setOffsetY(playground.getOffsetY() + drag.y - lastDrag.y);
			} else if(mousebutton[0] && !isPaused()) {
				if(!keyControl) {
					Entity se = playground.getSelectedEntity();
					if(se != null) {
						se.setX(se.getX() + drag.x - lastDrag.x);
						se.setY(se.getY() + drag.y - lastDrag.y);
					}
				} else if(playground.getSelectedEntity() != null) {
					System.out.println("Rotate...");
					// TODO Rotate
				}
			}
			
			lastDrag = drag;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			mouse = e.getPoint();
			
			for(Button b : buttons) {
				if(b.contains(mouse)) {
					b.press(e.getButton());
				}
			}
			
			if(e.getButton() != MouseEvent.NOBUTTON) {
				mousebutton[e.getButton() - 1] = true;
				
				if(mousebutton[0] && !isPaused() && !keyControl) {
					playground.setSelectedEntity(playground.getEntity(mouse));
				}
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() != MouseEvent.NOBUTTON) {
				mousebutton[e.getButton() - 1] = false;
			}
		}
	}
	
	/*
	 * ButtonHandler
	 */
	public class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			if(cmd.equals("pause")) {
				setPaused(!isPaused());
			} else if (cmd.equals("reset")) {
				reset();
			}
		}
	}
	
	/*
	 * FrameHandler
	 */
	public class FrameHandler extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			playground.updateOffset();
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public static Simulation getInstance() {
		return instance;
	}
	
	public static Playground getPlayground() {
		return playground;
	}
	
	public static long getSimulationTime() {
		return simTime;
	}
	
	public static OwnFont getClockFont() {
		return clockFont;
	}
	
	public static int getMode() {
		return mode;
	}
	
	public static void setMode(int mode) {
		if(mode >= MODE_EXPERT && mode <= MODE_SIMPLE) {
			Simulation.mode = mode;
			// TODO Repaint & Update?
		}
	}
	
	public static boolean isPaused() {
		return Simulation.paused;
	}
	
	public static void setPaused(boolean paused) {
		Simulation.paused = paused;
	}
	
	public static KeyHandler getKeyHandler() {
		return keyHandler;
	}
	
	public static MouseHandler getMouseHandler() {
		return mouseHandler;
	}
	
	public static ButtonHandler getButtonHandler() {
		return buttonHandler;
	}
	
	/*
	 * MAIN-METHOD
	 */
	public static void main(String args[]) {		
		int mode = MODE_EXPERT;
		int width = 640;
		int height = 480;
		
		for(String arg : args) {
			String value = arg.substring(2);
			
			if(arg.startsWith("m:")) {
				if(value.equalsIgnoreCase("expert") || (NumberUtil.isNumber(value) && value.equalsIgnoreCase(MODE_EXPERT + ""))) {
					mode = MODE_EXPERT;
				} else if(value.equalsIgnoreCase("simple") || (NumberUtil.isNumber(value) && value.equalsIgnoreCase(MODE_SIMPLE + ""))) {
					mode = MODE_SIMPLE;
				}
			} else if(arg.startsWith("w:") || arg.startsWith("h:")) {
				int size;
				if(NumberUtil.isNumber(value) && (size = Integer.parseInt(value)) > 0) {
					if(arg.startsWith("w:")) {
						width = size;
					} else {
						height = size;
					}
				}
			}
		}
		
		instance = new Simulation(new Dimension(width, height), mode);
		
		instance.run();
	}
}
