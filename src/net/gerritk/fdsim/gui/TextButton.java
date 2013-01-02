package net.gerritk.fdsim.gui;

import java.awt.Color;
import java.awt.Font;

import net.gerritk.fdsim.resource.SimFont;
import net.gerritk.util.ExGraphics;
import net.gerritk.util.StringUtil;

public class TextButton extends Button {
	private static final long serialVersionUID = 6212322680296307952L;
	
	private String text;
	private Font font = SimFont.TEXT;
	
	public TextButton(String text, int x, int y, int width, int height, Color cText, Color cNormal, Color cHover, Color cChecked, Color cBorder, Font font, float alpha, InterfaceObject ref) {
		super(x, y, width, height, cText, cNormal, cHover, cChecked, cBorder, alpha, ref);
		setText(text);
		setFont(font);
	}	
	
	public TextButton(String text, int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height, ref);
		setText(text);
	}

	@Override
	public void drawGUI(ExGraphics g) {
		if(!isVisible()) return;
		
		super.drawGUI(g);
		
		g.setFont(getFont());		
		g.setColor(getColorText());
		g.drawString(getText(), (int) (getX() + (getWidth() - StringUtil.getWidth(getText(), g)) / 2), (int) (getY() + (getHeight() + StringUtil.getHeight(getText(), g)) / 2 - 3));
	}
	
	/*
	 * Getter & Setter
	 */
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
}
