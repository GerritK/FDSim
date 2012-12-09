package net.gerritk.fdsim.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import net.gerritk.fdsim.Playground;
import net.gerritk.util.GraphicsUtil;

public abstract class Entity {
	private String name;
	private int x, y;
	private BufferedImage img;
	private Playground playground;
	
	public Entity(String name, int x, int y, BufferedImage img, Playground playground) {
		setName(name);
		setImage(img);
		setPlayground(playground);
		setX(x);
		setY(y);
	}
	
	public abstract void update();
	
	public void draw(Graphics2D g) {
		if(isSelected()) {
			GraphicsUtil.setThickness(g, 3);
			g.setColor(Color.GREEN);
			g.drawRoundRect(getScreenX() - 3, getScreenY() - 3, img.getWidth() + 5, img.getHeight() + 5, 3, 3);
			GraphicsUtil.setThickness(g, 1);
		}
		
		g.drawImage(getImage(), getScreenX(), getScreenY(), null);
	}
	
	public boolean containsScreen(Point p) {
		if(p.x >= getScreenX() && p.x <= getScreenX() + img.getWidth() && p.y >= getScreenY() && p.y <= getScreenY() + img.getHeight()) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Getter & Setter
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getScreenX() {
		return x + playground.getOffsetX();
	}
	
	public void setX(int x) {
		if(x < 0) {
			this.x = 0;
		} else if(x > playground.getSize().width - img.getWidth()) {
			this.x = playground.getSize().width - img.getWidth();
		} else {
			this.x = x;
		}
	}

	public int getY() {
		return y;
	}
	
	public int getScreenY() {
		return y + playground.getOffsetY();
	}
	
	public void setY(int y) {
		if(y < 0) {
			this.y = 0;
		} else if(y > playground.getSize().height - img.getHeight()) {
			this.y = playground.getSize().height - img.getHeight();
		} else {
			this.y = y;
		}
	}
	
	public BufferedImage getImage() {
		return img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

	public Playground getPlayground() {
		return playground;
	}

	public void setPlayground(Playground playground) {
		this.playground = playground;
	}
	
	public boolean isSelected() {
		return playground.getSelectedEntity() == this;
	}
}
