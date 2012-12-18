package net.gerritk.fdsim.gui;

import java.awt.Graphics2D;

public class PopupMenu extends InterfaceObject {
	private static final long serialVersionUID = 1220806512818700234L;
	
	public PopupMenu(int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height, ref);
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(isVisible()) {
			g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
		}
	}
	
	@Override
	public void update(long delta) {
		// TODO Auto-generated method stub

	}
}
