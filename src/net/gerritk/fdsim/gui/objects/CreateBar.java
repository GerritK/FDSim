package net.gerritk.fdsim.gui.objects;

import java.awt.Color;

import net.gerritk.fdsim.Simulation;
import net.gerritk.fdsim.gui.*;
import net.gerritk.fdsim.resource.SimColor;
import net.gerritk.util.ExGraphics;

public class CreateBar extends Bar {
	private static final long serialVersionUID = 4391913123355821132L;
	
	private boolean extended;
	private Button btnToggle;
	
	public CreateBar(int x, int width) {
		super(x, 50, width, 0, null);
		
		btnToggle = new Button(width - 8, 20, 8, (int) (getHeight() - getY()) - 40, Color.BLACK, SimColor.GUI_BG, SimColor.BLUE, SimColor.GUI_BORDER, 0.6f, this);
		btnToggle.setActionCommand("createBar");
		btnToggle.setToolTip("Menü ein-/ausblenden");
		btnToggle.addActionListener(Simulation.getButtonHandler());
		Simulation.buttons.add(btnToggle);
		
		setExtended(false);
	}
	
	@Override
	public void draw(ExGraphics g) {
		g.setColor(SimColor.GUI_BG);
		g.setAlpha(0.6f);
		g.fillRoundRect((int) getX() - 4, (int) getY(), (int) getWidth() + 4, Simulation.getInstance().getHeight() - (int) getY() * 2, 8, 8);
		g.setAlpha(1);
		g.setColor(SimColor.GUI_BORDER);
		g.drawRoundRect((int) getX() - 4, (int) getY(), (int) getWidth() + 3, Simulation.getInstance().getHeight() - (int) getY() * 2 - 1, 8, 8);
		g.drawLine((int) (getX() + getWidth()) - 8, (int) getY(), (int) (getX() + getWidth()) - 8, (int) (getHeight()) - 1);
		
		btnToggle.draw(g);
	}
	
	@Override
	public void update(long delta) {
		setHeight(Simulation.getInstance().getHeight() - (int) getY());
		btnToggle.setHeight((int) (getHeight() - getY()) - 40);
		
		btnToggle.update(delta);
	}
	
	/*
	 * Getter & Setter
	 */
	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
		
		if(extended) {
			setX(0);
		} else {
			setX((int) - getWidth() + 8);
		}
	}
}
