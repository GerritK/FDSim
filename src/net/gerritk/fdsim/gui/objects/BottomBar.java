package net.gerritk.fdsim.gui.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import net.gerritk.fdsim.Simulation;
import net.gerritk.fdsim.gui.*;
import net.gerritk.fdsim.resource.Images;
import net.gerritk.util.TimeUtil;

public class BottomBar extends Bar {
	private static final long serialVersionUID = -1344167944540858256L;
	
	private ImageButton btnPause;
	
	public BottomBar(int x, int height) {
		super(x, 0, 0, height, null);
		
		btnPause = new ImageButton(Images.BTN_PAUSE, 125, 1, 14, 14, this) {
			private static final long serialVersionUID = -6830300456624511872L;

			@Override
			public void press(int btn) {
				if(btn == MouseEvent.BUTTON1) {
					Simulation.setPaused(!Simulation.isPaused());
				}
			}
			
			@Override
			public void update() {
				if(Simulation.isPaused()) {
					setImage(Images.BTN_PLAY);
				} else {
					setImage(Images.BTN_PAUSE);
				}
			}
		};
		Simulation.buttons.add(btnPause);
	}

	@Override
	public void update() {
		btnPause.update();
	}

	@Override
	public void draw(Graphics2D g) {		
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
		
		// LINES
		g.setColor(Color.RED);
		g.drawLine((int) getX(), (int) getY(), (int) getWidth(), (int) getY());
		g.drawLine((int) getX() + 124, (int) getY(), (int) getX() + 124, (int) (getY() + getHeight()));
		g.drawLine((int) (getX() + getWidth()) - 124, (int) getY(), (int) (getX() + getWidth()) - 124, (int) (getY() + getHeight()));
		
		// CLOCK
		g.setColor(Color.RED);
		g.setFont(Simulation.clockFont.getFont(30));
		g.drawString(TimeUtil.formatMillis(Simulation.simTime), 10, (int) getY() + 25);
		
		// MODE
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.PLAIN, 12));
		String m = "Modus: " + (Simulation.getMode() == Simulation.MODE_EXPERT ? "Experte" : "Einsteiger");
		g.drawString(m, (int) (getX() + getWidth()) - 120, (int) (getY() + getHeight()) - 2);
		
		// GUI
		btnPause.draw(g);
	}
	
	@Override
	public double getY() {
		return Simulation.instance.getHeight() - getHeight();
	}
	
	@Override
	public double getWidth() {
		return Simulation.instance.getWidth();
	}
}
