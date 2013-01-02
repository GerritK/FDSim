package net.gerritk.fdsim.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import net.gerritk.util.ExGraphics;

public class IconButton extends Button {
	private static final long serialVersionUID = -7479202913149739570L;
	
	private BufferedImage img;
	
	public IconButton(BufferedImage img, int x, int y, int width, int height, Color cText, Color cNormal, Color cHover, Color cChecked, Color cBorder, float alpha, InterfaceObject ref) {
		super(x, y, width, height, cText, cNormal, cHover, cChecked, cBorder, alpha, ref);
		setImage(img);
	}
	
	public IconButton(BufferedImage img, int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height, ref);
		setImage(img);
	}

	@Override
	public void drawGUI(ExGraphics g) {
		if(!isVisible()) return;
		
		super.drawGUI(g);
		
		g.drawImage(getImage(), (int) (getX() + (getWidth() - getImage().getWidth()) / 2), (int) (getY() + (getHeight() - getImage().getHeight()) / 2), null);
	}

	public BufferedImage getImage() {
		return img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}
}
