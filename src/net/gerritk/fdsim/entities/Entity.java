package net.gerritk.fdsim.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.gerritk.fdsim.Playground;

public abstract class Entity {
	private String name;
	private int x, y;
	private BufferedImage img;
	private Playground playground;
	
	public Entity(String name, int x, int y, BufferedImage img, Playground playground) {
		setName(name);
		setX(x);
		setY(y);
		setImage(img);
		setPlayground(playground);
	}
	
	public abstract void update();
	
	public void draw(Graphics2D g) {
		g.drawImage(getImage(), getX() + playground.getOffsetX(), getY() + playground.getOffsetY(), null);
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

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
}
