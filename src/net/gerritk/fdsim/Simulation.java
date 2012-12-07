package net.gerritk.fdsim;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import net.gerritk.util.*;

public class Simulation extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 3904671781449439735L;
	
	public static final String VERSION = "0.0.1 DEV", COPY = "(c) K.Design 2012 - Gerrit Kaul - Feuerwehr Braunschweig";
	public static final int MODE_EXPERT = 0, MODE_SIMPLE = 1;
	
	private JFrame frame;
	private Map<RenderingHints.Key, Object> renderMap;
	private int mode;
	private boolean paused;
	private OwnFont clockFont;
	private long simTime;
	
	private boolean key_control;
	
	public Simulation(Dimension d, int mode) {
		this.mode = mode;
		simTime = -60 * 60 * 1000;
		
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
		frame.addKeyListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(this);
		frame.pack();
		frame.setLocation(wp);
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
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, getHeight() - 30, getWidth(), 30);
		
		g.setColor(Color.RED);
		g.drawLine(0, getHeight() - 30, getWidth(), getHeight() - 30);
		g.drawLine(124, getHeight() - 30, 124, getHeight());
		g.drawLine(getWidth() - 124, getHeight() - 30, getWidth() - 124, getHeight());
		
		// CLOCK
		g.setColor(Color.RED);
		g.setFont(clockFont.getFont(30));
		g.drawString(TimeUtil.formatMillis(simTime), 10, getHeight() - 5);
		
		// COPY
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Verdana", Font.PLAIN, 8));
		g.drawString(COPY, getWidth() - StringUtil.getWidth(COPY, g) - 2, StringUtil.getHeight(COPY, g));
		
		// MODE
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.PLAIN, 12));
		String m = "Modus: " + (mode == MODE_EXPERT ? "Experte" : "Einsteiger");
		g.drawString(m, getWidth() - 120, getHeight() - 2);
		
		if(!frame.isActive() || isPaused()) {
			GraphicsUtil.setAlpha(g, 0.4f);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			GraphicsUtil.setAlpha(g, 1);
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", Font.BOLD, 24));
			g.drawString("Pausiert", (getWidth() - StringUtil.getWidth("Pausiert", g)) / 2, (getHeight() - StringUtil.getHeight("Pausiert", g)) / 2);
		}
	}
	
	/*
	 * KeyListener
	 */
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

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	/*
	 * Getter & Setter
	 */
	public int getMode() {
		return mode;
	}
	
	public void setMode(int mode) {
		if(mode >= MODE_EXPERT && mode <= MODE_SIMPLE) {
			this.mode = mode;
			// TODO Repaint & Update!
		}
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
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
		
		Simulation instance = new Simulation(new Dimension(width, height), mode);
		
		instance.run();
	}
}
