package net.gerritk.fdsim.gui;

import net.gerritk.fdsim.resource.SimColor;
import net.gerritk.util.ExGraphics;
import net.gerritk.util.StringUtil;

public class PopupMenu extends InterfaceObject {
	private static final long serialVersionUID = 1220806512818700234L;
	
	private String title;
	
	public PopupMenu(int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height, ref);
	}
	
	@Override
	public void drawGUI(ExGraphics g) {
		if(isVisible()) {
			if(getTitle() != null) {
				g.setColor(SimColor.GUI_POPUP);
				g.fillRoundRect((int) getX(), (int) getY() - StringUtil.getHeight(getTitle(), g), StringUtil.getWidth(getTitle(), g) + 10, StringUtil.getHeight(getTitle(), g) + 5, 8, 8);
				g.setColor(SimColor.GUI_POPUP_BORDER);
				g.drawRoundRect((int) getX(), (int) getY() - StringUtil.getHeight(getTitle(), g), StringUtil.getWidth(getTitle(), g) + 9, StringUtil.getHeight(getTitle(), g) + 5, 8, 8);
				g.setColor(SimColor.GUI_POPUP_TEXT);
				g.drawString(getTitle(), (int) getX() + 5, (int) getY() - 3);
			}
			
			g.setColor(SimColor.GUI_POPUP);
			g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
			g.setColor(SimColor.GUI_POPUP_BORDER);
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
