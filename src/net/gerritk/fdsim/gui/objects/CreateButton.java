package net.gerritk.fdsim.gui.objects;

import java.awt.image.BufferedImage;

import net.gerritk.fdsim.gui.*;
import net.gerritk.fdsim.resource.SimColor;
import net.gerritk.fdsim.resource.SimFont;
import net.gerritk.util.ExGraphics;
import net.gerritk.util.StringUtil;

public class CreateButton extends Button {
	private static final long serialVersionUID = 7612481530075637163L;
	
	private int count;
	private BufferedImage img;
	private String name;
	
	public CreateButton(BufferedImage img, String name, int count, int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height, SimColor.GUI_BUTTON_TEXT, SimColor.GUI_BUTTON, SimColor.GUI_BUTTON_HOVER, SimColor.GUI_BUTTON_CHECKED, SimColor.GUI_BUTTON_BORDER, 0.8f, ref);
		
		setImage(img);
		setCount(count);
		setName(name);
	}
	
	@Override
	public void draw(ExGraphics g) {
		if(!isVisible()) return;
		
		super.draw(g);
		
		g.setFont(SimFont.TEXT_CREATE);
		int sh = StringUtil.getHeight(getName(), g);
		
		g.drawImage(getImage(), (int) (getX() + getWidth() - getImage().getWidth() - 2), (int) (getY() + ((getHeight() + sh + 2) - getImage().getHeight()) / 2 - 1), null);
		
		g.drawLine((int) getX(), (int) getY() + sh, (int) (getX() + getWidth()) - 1, (int) getY() + sh);
		g.drawLine((int) getX() + 30, (int) getY() + sh, (int) getX() + 30, (int) (getY() + getHeight()) - 1);
		
		g.drawString(getName(), (int) getX() + 2, (int) getY() + sh - 1);
		
		g.setFont(SimFont.TEXT_CREATE_COUNT);
		g.drawString("" + getCount(), (int) getX() + (30 - StringUtil.getWidth("" + getCount(), g)) / 2, (int) getY() + sh + StringUtil.getHeight("" + getCount(), g) - 1);
	}
	
	@Override
	public void press(int btn) {
		if(getCount() <= 0) return;
		
		super.press(btn);
	}
	
	/*
	 * Getter & Setter	
	 */
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BufferedImage getImage() {
		return img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
