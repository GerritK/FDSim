package net.gerritk.fdsim.gui.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import net.gerritk.fdsim.Simulation;
import net.gerritk.fdsim.gui.*;
import net.gerritk.fdsim.resource.Images;
import net.gerritk.util.TimeUtil;

public class BottomBar extends Bar {
	private static final long serialVersionUID = -1344167944540858256L;
	
	private IconButton btnPause;
	private IconButton btnReset;
	
	public BottomBar(int x, int height) {
		super(x, 0, 0, height, null);
		
		btnPause = new IconButton(Images.BTN_PAUSE, 125, 1, 14, 14, this) {
			private static final long serialVersionUID = -390729799905267457L;

			@Override
			public void update(long delta) {
				if(Simulation.isPaused()) {
					setImage(Images.BTN_PLAY);
				} else {
					setImage(Images.BTN_PAUSE);
				}
			}
		};
		btnPause.setActionCommand("pause");
		btnPause.addActionListener(Simulation.getButtonHandler());
		Simulation.buttons.add(btnPause);
		
		btnReset = new IconButton(Images.BTN_RESET, 125, 16, 14, 14, this);
		btnReset.setActionCommand("reset");
		btnReset.addActionListener(Simulation.getButtonHandler());
		Simulation.buttons.add(btnReset);
	}

	@Override
	public void update(long delta) {
		btnPause.update(delta);
	}

	@Override
	public void draw(Graphics2D g) {
		if(!isVisible()) return;
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
		
		// LINES
		g.setColor(Color.RED);
		g.drawLine((int) getX(), (int) getY(), (int) getWidth(), (int) getY());
		g.drawLine((int) getX() + 124, (int) getY(), (int) getX() + 124, (int) (getY() + getHeight()));
		g.drawLine((int) (getX() + getWidth()) - 124, (int) getY(), (int) (getX() + getWidth()) - 124, (int) (getY() + getHeight()));
		
		// CLOCK
		g.setColor(Color.RED);
		g.setFont(Simulation.getClockFont().getFont(30));
		g.drawString(TimeUtil.formatMillis(Simulation.getSimulationTime()), 10, (int) getY() + 25);
		
		// MODE
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.PLAIN, 12));
		String m = "Modus: " + (Simulation.getMode() == Simulation.MODE_EXPERT ? "Experte" : "Einsteiger");
		g.drawString(m, (int) (getX() + getWidth()) - 120, (int) (getY() + getHeight()) - 2);
		
		// GUI
		btnPause.draw(g);
		btnReset.draw(g);
	}
	
	/*
	 * Getter & Setter
	 */
	@Override
	public double getY() {
		return Simulation.getInstance().getHeight() - getHeight();
	}
	
	@Override
	public double getWidth() {
		return Simulation.getInstance().getWidth();
	}
}
