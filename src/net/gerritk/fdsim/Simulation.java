package net.gerritk.fdsim;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import net.gerritk.fdsim.entities.Entity;
import net.gerritk.fdsim.gui.Button;
import net.gerritk.fdsim.gui.PopupMenu;
import net.gerritk.fdsim.gui.objects.*;
import net.gerritk.fdsim.resource.SimColor;
import net.gerritk.fdsim.resource.SimFont;
import net.gerritk.util.*;

public class Simulation extends JPanel implements Runnable {
	private static final long serialVersionUID = 3904671781449439735L;
	
	public static final String VERSION = "0.0.1 DEV", COPY = "(c) 2012 - K.Design - Gerrit Kaul - Feuerwehr Braunschweig";
	public static final int MODE_HOST = 0, MODE_USER = 1;
	
	private static Simulation instance;
	private static Playground playground;
	private static long simTime;
	private static long lastRunned;
	
	private static JFrame frame;
	private static Map<RenderingHints.Key, Object> renderMap;
	private static int mode;
	private static boolean paused;
	
	private static KeyHandler keyHandler;
	private static MouseHandler mouseHandler;
	private static ButtonHandler buttonHandler;
	private static FrameHandler frameHandler;
	
	// GUI
	public static ArrayList<Button> buttons = new ArrayList<Button>();
	private static BottomBar bottomBar;
	private static CreateBar createBar;
	private static PopupMenu popupMenu;
	
	public Simulation(Dimension d, int mode) {
		setMode(mode);
		simTime = -60 * 60 * 1000;
		
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		buttonHandler = new ButtonHandler();
		frameHandler = new FrameHandler();
		
		renderMap = new HashMap<RenderingHints.Key, Object>();
		renderMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderMap.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		renderMap.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		renderMap.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		renderMap.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		setPreferredSize(d);
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
		addMouseWheelListener(mouseHandler);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point wp = new Point((int) (screenSize.getWidth() - d.getWidth()) / 2, (int) (screenSize.getHeight() - d.getHeight()) / 2);
		
		// GUI
		bottomBar = new BottomBar(0, 30);
		createBar = new CreateBar(0, 100);
		
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
		simTime = 0;
		playground = new Playground(playground.getTitle(), playground.getSize(), playground.getStart(), playground.getStartTime());
	}
	
	public void update(long delta) {		
		if(!isPaused()) {
			if(playground != null) {
				playground.update(delta);
			}
			
			simTime += System.currentTimeMillis() - lastRunned;
			
			keyHandler.control();
		}
		
		// GUI
		bottomBar.update(delta);
		createBar.update(delta);
	}
	
	public void draw(ExGraphics g) {
		g.setColor(SimColor.VOID);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// PLAYGROUND
		if(playground != null) {
			playground.draw(g);
		}
		
		// GUI
		if(popupMenu != null) {
			popupMenu.draw(g);
		}
		
		bottomBar.draw(g);
		createBar.draw(g);
		
		if(isPaused()) {
			String paused = "Pausiert";
			String pausedInfo = "Drücken Sie 'P' oder den Pausieren-Knopf zum Fortfahren.";
			
			g.setAlpha(0.4f);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setAlpha(1);
			g.setColor(SimColor.GUI_BORDER);
			g.setFont(SimFont.PAUSED_BIG);
			g.drawString(paused, (getWidth() - StringUtil.getWidth(paused, g)) / 2, (getHeight() - StringUtil.getHeight(paused, g)) / 2);
			g.setColor(Color.WHITE);
			g.setFont(SimFont.PAUSED_SMALL);
			g.drawString(pausedInfo, (getWidth() - StringUtil.getWidth(pausedInfo, g)) / 2, (getHeight() + StringUtil.getHeight(pausedInfo, g)) / 2);
		}
		
		// COPY
		g.setColor(Color.GRAY);
		g.setFont(SimFont.COPY);
		g.drawString(COPY, getWidth() - StringUtil.getWidth(COPY, g) - 2, StringUtil.getHeight(COPY, g));
	}
	
	public void run() {
		lastRunned = System.currentTimeMillis();
		long delta = 0;
		
		// TODO PLAYGROUND
		playground = new Playground("Wohnhausbrand Siekgraben", new Dimension(800, 800), new Point(100, 200), TimeUtil.getTimeInMillis(13, 49, 32, 0));
		
		while(frame.isVisible()) {
			// Calculate Delta
			delta = System.currentTimeMillis() - lastRunned;
			
			update(delta);
			
			repaint();
			
			if(keyHandler.keyPrint) {
				saveScreenshot();
				keyHandler.keyPrint = false;
			}
			
			lastRunned = System.currentTimeMillis();
			
			try {
				Thread.sleep(1000 / 100);
			} catch(InterruptedException e) {
				// Nothing?
			}
		}
	}
	
	public void paintComponent(Graphics gra) {
		ExGraphics g = new ExGraphics((Graphics2D) gra);
		g.setRenderingHints(renderMap);
		draw(g);
	}
	
	/*
	 * Methods
	 */
	public void saveScreenshot() {
		BufferedImage shot = new BufferedImage(getWidth(), getHeight(), BufferedImage.TRANSLUCENT);
		ExGraphics g = new ExGraphics(shot.createGraphics());
		
		g.setRenderingHints(renderMap);
		draw(g);
		g.dispose();
		
		Calendar cal = Calendar.getInstance();
		String name = "shot_" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + " - " + cal.get(Calendar.DAY_OF_MONTH) + "_" +
				cal.get(Calendar.HOUR_OF_DAY) + "-" + cal.get(Calendar.MINUTE) + "-" + cal.get(Calendar.SECOND);
		
		ImageUtil.saveImage("screenshots/", name, shot);		
	}
	
	/*
	 * KeyHandler
	 */	
	public class KeyHandler extends KeyAdapter {
		protected boolean keyControl;
		protected boolean keyPrint;
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
				keyControl = true;
			} else if(e.getKeyCode() == KeyEvent.VK_F4) {
				keyPrint = true;
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
			} else if(e.getKeyCode() == KeyEvent.VK_F4) {
				keyPrint = false;
			}
			
			if(keyControl) {
				if(e.getKeyChar() >= 48 && e.getKeyChar() <= 57) {
					int mode = e.getKeyChar() - 49;			
					setMode(mode == -1 ? 9 : mode);
				}
			}
		}
		
		public void control() {
			// Nothing at this time
		}
	}
	
	/*
	 * MouseHandler
	 */
	public class MouseHandler extends MouseAdapter {
		public long lastMoved;
		public Point mouse;
		public Point lastDrag;
		public boolean mousebutton[] = new boolean[3];
		
		@Override
		public void mouseMoved(MouseEvent e) {
			lastMoved = System.currentTimeMillis();
			
			mouse = e.getPoint();
			lastDrag = mouse;
        	
			if(createBar.getToCreate() == null) {
				for(Button b : buttons) {
		        	if(b.contains(mouse)) {
		        		b.setHover(true);
		        		frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
		        	} else if(b.isHover()) {
		        		b.setHover(false);
		        		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		        	}
				}
			} else {
				frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			Point drag = e.getPoint();
			
			if(mousebutton[2] && !isPaused()) {
				playground.setOffsetX(playground.getOffsetX() + drag.x - lastDrag.x);
				playground.setOffsetY(playground.getOffsetY() + drag.y - lastDrag.y);
			} else if(mousebutton[0] && !isPaused()) {
				if(!keyHandler.keyControl) {
					Entity se = playground.getSelectedEntity();
					if(se != null) {
						se.setX(se.getX() + drag.x - lastDrag.x);
						se.setY(se.getY() + drag.y - lastDrag.y);
					}
				}
			}
			
			lastDrag = drag;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			mouse = e.getPoint();
			
			if(createBar.getToCreate() == null) {
				for(Button b : buttons) {
					if(b.contains(mouse)) {
						b.press(e.getButton());
					}
				}
			
				if(e.getButton() != MouseEvent.NOBUTTON && !createBar.contains(mouse) && !bottomBar.contains(mouse)) {
					mousebutton[e.getButton() - 1] = true;
					
					if(mousebutton[0] && !isPaused() && !keyHandler.keyControl) {
						playground.setSelectedEntity(playground.getEntity(mouse));
					} else if(mousebutton[2] && !isPaused() && (playground.getEntity(mouse) == null || playground.getEntity(mouse) != playground.getSelectedEntity())) {
						playground.setSelectedEntity(null);
					}
				}
			} else {
				Entity ec = createBar.getToCreate();
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					ec.setPlayground(playground);
					ec.setX(mouse.x - playground.getOffsetX() - ec.getImage().getWidth() / 2);
					ec.setY(mouse.y - playground.getOffsetY() - ec.getImage().getHeight() / 2);
					
					playground.addEntity(ec);
					
					createBar.getToCreateButton().setCount(createBar.getToCreateButton().getCount() - 1);
				}
				
				frame.setCursor(Cursor.getDefaultCursor());
				createBar.getToCreateButton().setHover(false);
				createBar.setToCreate(null);
				createBar.setToCreateButton(null);
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() != MouseEvent.NOBUTTON) {
				mousebutton[e.getButton() - 1] = false;
				popupMenu = null;
			}
			
			if(e.getButton() == MouseEvent.BUTTON3 && playground.getSelectedEntity() != null) {
				popupMenu = new PopupMenu(e.getX() + 5, e.getY(), 100, 64, null); // TODO
				popupMenu.setTitle(playground.getSelectedEntity().getName());
			}
		}
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(playground.getSelectedEntity() != null) {
				Entity se = playground.getSelectedEntity();
				
				if(keyHandler.keyControl) {					
					se.setRotation((int) se.getRotation() + 45 * e.getWheelRotation());
				} else {
					se.setRotation((int) se.getRotation() + e.getWheelRotation() * 4);
				}
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
			} else if(cmd.equals("reset")) {
				reset();
			} else if(cmd.equals("createBar")) {
				createBar.setExtended(!createBar.isExtended());
			}
		}
	}
	
	/*
	 * FrameHandler
	 */
	public class FrameHandler extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			if(playground != null) {
				playground.updateOffset();
			}
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
	
	public static long getSimulationTime(boolean play) {
		if(play && playground != null) {
			return simTime + playground.getStartTime();
		}
		
		return simTime;
	}
	
	public static int getMode() {
		return mode;
	}
	
	public static void setMode(int mode) {
		if(mode >= MODE_HOST && mode <= MODE_USER) {
			Simulation.mode = mode;
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
		int mode = MODE_HOST;
		int width = 640;
		int height = 480;
		
		for(String arg : args) {
			String value = arg.substring(2);
			
			if(arg.startsWith("m:")) {
				if(value.equalsIgnoreCase("host") || (NumberUtil.isNumber(value) && value.equalsIgnoreCase(MODE_HOST + ""))) {
					mode = MODE_HOST;
				} else if(value.equalsIgnoreCase("user") || (NumberUtil.isNumber(value) && value.equalsIgnoreCase(MODE_USER + ""))) {
					mode = MODE_USER;
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
