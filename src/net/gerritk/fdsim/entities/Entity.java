package net.gerritk.fdsim.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import net.gerritk.fdsim.Playground;
import net.gerritk.util.GraphicsUtil;

public abstract class Entity {
	private String name;
	private int x, y;
	private BufferedImage img;
	private Playground playground;
	private double rotation;
	
	/*
	 * TODO Add Method to rotate Object!
	 */
	
	public Entity(String name, int x, int y, BufferedImage img, Playground playground) {
		setName(name);
		setImage(img);
		setPlayground(playground);
		setX(x);
		setY(y);
	}
	
	public abstract void update();
	
	public void draw(Graphics2D g) {
		AffineTransform af = g.getTransform();
        g.rotate(Math.toRadians(- getRotation()), getScreenX() + getImage().getWidth() / 2 + 1, getScreenY() + getImage().getHeight() / 2 + 1);
		
		if(isSelected()) {
			GraphicsUtil.setThickness(g, 3);
			g.setColor(Color.GREEN);
			g.drawRoundRect(getScreenX() - 3, getScreenY() - 3, img.getWidth() + 5, img.getHeight() + 5, 3, 3);
			GraphicsUtil.setThickness(g, 1);
		}
		
        g.drawImage(getImage(), getScreenX(), getScreenY(), null);
        
        g.setTransform(af);
        
        g.setColor(Color.PINK);
        g.fillPolygon(getCollision());
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
	
	public double getRotation() {
		return rotation;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation % 360;
	}
	
	public void setSelected(boolean selected) {
		if(selected) {
			playground.setSelectedEntity(this);
		} else if(isSelected()) {
			playground.setSelectedEntity(null);
		}
	}
	
	public boolean isSelected() {
		return playground.getSelectedEntity() == this;
	}
	
	
	/*
	 * 
	 */
	protected Polygon getCollision() {
		Polygon poly = new Polygon();
		
		double rot = Math.toRadians(getRotation());
		double alpha = Math.asin((double) getImage().getWidth() / getImage().getHeight()) - 0.1;
		double hypo = Math.sqrt(Math.pow(getImage().getWidth() / 2, 2) + Math.pow(getImage().getHeight() / 2, 2));
		
		poly.addPoint(getX() + getImage().getWidth() / 2 - (int) (Math.sin(rot + alpha) * hypo), getY() + getImage().getHeight() / 2 - (int) (Math.cos(rot + alpha) * hypo));
		poly.addPoint(getX() + getImage().getWidth() / 2 - (int) (Math.sin(rot - alpha) * hypo), getY() + getImage().getHeight() / 2 - (int) (Math.cos(rot - alpha) * hypo));
		
		poly.addPoint(getX() + getImage().getWidth() / 2 + (int) (Math.sin(rot + alpha) * hypo), getY() + getImage().getHeight() / 2 + (int) (Math.cos(rot + alpha) * hypo));
		poly.addPoint(getX() + getImage().getWidth() / 2 + (int) (Math.sin(rot - alpha) * hypo), getY() + getImage().getHeight() / 2 + (int) (Math.cos(rot - alpha) * hypo));
		
		return poly;
	}
}
