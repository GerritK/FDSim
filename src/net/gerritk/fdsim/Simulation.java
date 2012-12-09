package net.gerritk.fdsim;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import net.gerritk.fdsim.gui.Button;
import net.gerritk.fdsim.gui.objects.*;
import net.gerritk.util.*;

public class Simulation extends JPanel implements Runnable {
	private static final long serialVersionUID = 3904671781449439735L;
	
	public static final String VERSION = "0.0.1 DEV", COPY = "(c) 2012 - K.Design - Gerrit Kaul - Feuerwehr Braunschweig";
	public static final int MODE_EXPERT = 0, MODE_SIMPLE = 1;
	
	public static Simulation instance;
	public static OwnFont clockFont;
	public static long simTime;
	
	private static JFrame frame;
	private static Map<RenderingHints.Key, Object> renderMap;
	private static int mode;
	private static boolean paused;
	
	private static KeyHandler keyHandler;
	private static MouseHandler mouseHandler;
	private static boolean key_control;
	
	// GUI
	public static ArrayList<Button> buttons = new ArrayList<Button>();
	private static BottomBar bottomBar;
	
	public Simulation(Dimension d, int mode) {
		setMode(mode);
		simTime = -60 * 60 * 1000;
		
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		
		// FONT
		clockFont = new OwnFont("DS-DIGIT.TTF", Font.TRUETYPE_FONT);
		
		renderMap = new HashMap<RenderingHints.Key, Object>();
		renderMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderMap.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		renderMap.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		renderMap.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		
		setPreferredSize(d);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point wp = new Point((int) (screenSize.getWidth() - d.getWidth()) / 2, (int) (screenSize.getHeight() - d.getHeight()) / 2);
		
		frame = new JFrame("Feuerwehr Planspiel Simulation " + VERSION);
		frame.addKeyListener(keyHandler);
		frame.addMouseListener(mouseHandler);
		frame.addMouseMotionListener(mouseHandler);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(640, 480));
		frame.add(this);
		frame.pack();
		frame.setLocation(wp);
		
		// GUI
		bottomBar = new BottomBar(0, 30);
	}
	
	public void run() {
		long lastRunned = System.currentTimeMillis();
		while(frame.isVisible()) {
			if(frame.isActive() && !isPaused()) {
				simTime += System.currentTimeMillis() - lastRunned;
			}
			
			repaint();
			lastRunned = System.currentTimeMillis();
			
			try {
				Thread.sleep(1000 / 15);
			} catch(InterruptedException e) {
				// Nothing?
			}
		}
	}
	
	public void paintComponent(Graphics gra) {
		Graphics2D g = (Graphics2D) gra;
		g.setRenderingHints(renderMap);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		bottomBar.draw(g);
		
		if(!frame.isActive() || isPaused()) {
			GraphicsUtil.setAlpha(g, 0.4f);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			GraphicsUtil.setAlpha(g, 1);
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", Font.BOLD, 24));
			g.drawString("Pausiert", (getWidth() - StringUtil.getWidth("Pausiert", g)) / 2, (getHeight() - StringUtil.getHeight("Pausiert", g)) / 2);
		}
		
		// COPY
		g.setColor(Color.LIGHT_GRAY);
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
				key_control = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_P) {
				setPaused(!isPaused());
			} else if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
				key_control = false;
			}
			
			if(key_control) {
				if(e.getKeyChar() >= 48 && e.getKeyChar() <= 57) {
					int mode = e.getKeyChar() - 49;			
					setMode(mode == -1 ? 9 : mode);
				}
			}
		}
	}
	
	/*
	 * MouseHandler
	 */
	public class MouseHandler extends MouseAdapter {
		public Point mouse;
		
		@Override
		public void mouseMoved(MouseEvent e) {
			saveMouse(e);
        	
			for(Button b : buttons) {
	        	if(b.contains(mouse)) {
	        		b.setHover(true);
	        	} else if(b.isHover()) {
	        		b.setHover(false);
	        	}
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			saveMouse(e);
			
			for(Button b : buttons) {
				if(b.contains(mouse)) {
					b.press(e.getButton());
				}
			}
		}
		
		public void saveMouse(MouseEvent e) {
			mouse = e.getPoint();
			mouse.x -= 8;
			mouse.y -= 30;
		}
	}
	
	/*
	 * Getter & Setter
	 */
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
		return paused;
	}
	
	public static void setPaused(boolean paused) {
		Simulation.paused = paused;
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
