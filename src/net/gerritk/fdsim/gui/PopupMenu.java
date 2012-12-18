package net.gerritk.fdsim.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import net.gerritk.util.StringUtil;

public class PopupMenu extends InterfaceObject {
	private static final long serialVersionUID = 1220806512818700234L;
	
	private String title;
	
	public PopupMenu(int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height, ref);
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(isVisible()) {
			if(getTitle() != null) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRoundRect((int) getX(), (int) getY() - StringUtil.getHeight(getTitle(), g), StringUtil.getWidth(getTitle(), g) + 10, StringUtil.getHeight(getTitle(), g) + 5, 8, 8);
				g.setColor(Color.GRAY);
				g.drawRoundRect((int) getX(), (int) getY() - StringUtil.getHeight(getTitle(), g), StringUtil.getWidth(getTitle(), g) + 9, StringUtil.getHeight(getTitle(), g) + 5, 8, 8);
				g.setColor(Color.BLACK);
				g.drawString(getTitle(), (int) getX() + 5, (int) getY() - 3);
			}
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
			g.setColor(Color.GRAY);
			g.drawRect((int) getX(), (int) getY(), (int) getWidth() - 1, (int) getHeight() - 1);
		}
	}
	
	@Override
	public void update(long delta) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * Getter & Setter
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
