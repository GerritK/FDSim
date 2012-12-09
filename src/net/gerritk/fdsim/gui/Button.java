package net.gerritk.fdsim.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import net.gerritk.util.GraphicsUtil;

public abstract class Button extends InterfaceObject {
	private static final long serialVersionUID = -2273998620740218304L;
	
	private boolean hover;
	private Color cText, cNormal, cHover;
	
	public Button(int x, int y, int width, int height, Color cText, Color cNormal, Color cHover, InterfaceObject ref) {
		super(x, y, width, height, ref);
		setColorText(cText);
		setColorNormal(cNormal);
		setColorHover(cHover);
	}
	
	public Button(int x, int y, int width, int height, InterfaceObject ref) {
		this(x, y, width, height, Color.BLACK, Color.GRAY, Color.LIGHT_GRAY, ref);
	}
	
	@Override
	public void update() {
		// Nothing?
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(isHover()) {
			g.setColor(getColorHover());
		} else {
			g.setColor(getColorNormal());		
		}
		
		g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
		
		g.setColor(Color.BLACK);
		GraphicsUtil.setAlpha(g, 0.7f);
		g.drawRect((int) getX(), (int) getY(), (int) getWidth() - 1, (int) getHeight() - 1);
		GraphicsUtil.setAlpha(g, 1);
	}
	
	public abstract void press(int btn);
	
	/*
	 * Getter & Setter	
	 */
	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public Color getColorText() {
		return cText;
	}

	public void setColorText(Color cText) {
		this.cText = cText;
	}

	public Color getColorHover() {
		return cHover;
	}

	public void setColorHover(Color cHover) {
		this.cHover = cHover;
	}

	public Color getColorNormal() {
		return cNormal;
	}

	public void setColorNormal(Color cNormal) {
		this.cNormal = cNormal;
	}
}
