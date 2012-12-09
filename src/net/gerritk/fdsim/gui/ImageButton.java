package net.gerritk.fdsim.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class ImageButton extends Button {
	private static final long serialVersionUID = -7479202913149739570L;

	public ImageButton(int x, int y, int width, int height, Color cText, Color cNormal, Color cHover, InterfaceObject ref) {
		super(x, y, width, height, cText, cNormal, cHover, ref);
	}
	
	public ImageButton(int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height, ref);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		
	}
}
