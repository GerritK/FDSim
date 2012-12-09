package net.gerritk.fdsim.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class IconButton extends Button {
	private static final long serialVersionUID = -7479202913149739570L;
	
	private BufferedImage img;
	
	public IconButton(BufferedImage img, int x, int y, int width, int height, Color cText, Color cNormal, Color cHover, InterfaceObject ref) {
		super(x, y, width, height, cText, cNormal, cHover, ref);
		setImage(img);
	}
	
	public IconButton(BufferedImage img, int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height, ref);
		setImage(img);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		g.drawImage(getImage(), (int) getX(), (int) getY(), null);
	}

	public BufferedImage getImage() {
		return img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}
}
