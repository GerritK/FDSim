package net.gerritk.fdsim.gui.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import net.gerritk.fdsim.Simulation;
import net.gerritk.fdsim.gui.*;
import net.gerritk.fdsim.resource.SimColor;
import net.gerritk.fdsim.resource.SimImage;
import net.gerritk.util.TimeUtil;

public class BottomBar extends Bar {
	private static final long serialVersionUID = -1344167944540858256L;
	
	private IconButton btnPause;
	private IconButton btnReset;
	
	public BottomBar(int x, int height) {
		super(x, 0, 0, height, null);
		
		btnPause = new IconButton(SimImage.BTN_PAUSE, 126, 2, 26, 26, this) {
			private static final long serialVersionUID = -390729799905267457L;

			@Override
			public void update(long delta) {
				if(Simulation.isPaused()) {
					setImage(SimImage.BTN_PLAY);
				} else {
					setImage(SimImage.BTN_PAUSE);
				}
			}
		};
		btnPause.setActionCommand("pause");
		btnPause.setToolTip("Simulation pausieren");
		btnPause.addActionListener(Simulation.getButtonHandler());
		btnPause.setStyle(Button.ROUND_RECT);
		Simulation.buttons.add(btnPause);
		
		btnReset = new IconButton(SimImage.BTN_RESET, 153, 2, 26, 26, this);
		btnReset.setActionCommand("reset");
		btnReset.setToolTip("Szenario zurücksetzen");
		btnReset.addActionListener(Simulation.getButtonHandler());
		btnReset.setStyle(Button.ROUND_RECT);
		Simulation.buttons.add(btnReset);
	}

	@Override
	public void update(long delta) {
		btnPause.update(delta);
	}

	@Override
	public void draw(Graphics2D g) {
		if(!isVisible()) return;
		
		g.setColor(SimColor.GUI_BG);
		g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
		
		// LINES
		g.setColor(SimColor.GUI_BORDER);
		g.drawLine((int) getX(), (int) getY(), (int) getWidth(), (int) getY());
		g.drawLine((int) getX() + 124, (int) getY(), (int) getX() + 124, (int) (getY() + getHeight()));
		g.drawLine((int) (getX() + getWidth()) - 124, (int) getY(), (int) (getX() + getWidth()) - 124, (int) (getY() + getHeight()));
		g.drawLine((int) getX(), (int) (getY() + getHeight()) - 1, (int) getWidth(), (int) (getY() + getHeight()) - 1);
		
		// CLOCK
		g.setColor(SimColor.GUI_BORDER);
		g.setFont(Simulation.getClockFont().getFont(30));
		g.drawString(TimeUtil.formatMillis(Simulation.getSimulationTime()), 10, (int) getY() + 25);
		
		// MODE
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.PLAIN, 12));
		String m = "Modus: " + (Simulation.getMode() == Simulation.MODE_HOST ? "Host" : "Benutzer");
		g.drawString(m, (int) (getX() + getWidth()) - 120, (int) (getY() + getHeight()) - 3);
		
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
