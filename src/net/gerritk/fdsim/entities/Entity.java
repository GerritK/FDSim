package net.gerritk.fdsim.entities;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.Simulation;
import net.gerritk.fdsim.interfaces.*;
import net.gerritk.fdsim.resource.SimColor;
import net.gerritk.util.ExGraphics;
import net.gerritk.util.MathUtil;
import net.gerritk.util.StringUtil;

public abstract class Entity implements Drawable, DrawableGUI, Updateable {
	protected static final int BORDER = 2;
	
	private String name;
	private int x, y;
	private BufferedImage img;
	private Playground playground;
	private double rotation;
	
	public Entity(String name, int x, int y, BufferedImage img, Playground playground) {
		
		setName(name);
		setImage(img);
		setPlayground(playground);
		setX(x);
		setY(y);
	}
	
	@Override
	public void draw(ExGraphics g) {
		g.rotate(Math.toRadians(- getRotation()), getScreenX() + getImage().getWidth() / 2 + 1, getScreenY() + getImage().getHeight() / 2 + 1);
		
		if(isSelected()) {			
			if(collides(null)) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.GREEN);
			}
			
			g.setAlpha(0.6f);
			g.setThickness(3);
			g.drawRoundRect(getScreenX() - BORDER, getScreenY() - BORDER, img.getWidth() + BORDER * 2, img.getHeight() + BORDER * 2, 3, 3);
			g.setThickness(1);
			g.setAlpha(1);
		}
		
		g.drawImage(getImage(), getScreenX(), getScreenY(), null);
	}
	
	public void drawGUI(ExGraphics g) {
		if(Simulation.showNames()) {			
			int hy = MathUtil.getHeighestPoint(getCollision(), MathUtil.TOP).y + playground.getOffsetY();
			
			g.setColor(SimColor.GUI_POPUP_TEXT);
			g.drawString(getName(), getScreenX() + (getImage().getWidth() - StringUtil.getWidth(getName(), g)) / 2 + 1, hy - 19);
			
			int xp[] = new int[] { getScreenX() - 3,
					getScreenX() - 3,
					getScreenX() + getImage().getWidth() / 2,
					getScreenX() + getImage().getWidth() + 3,
					getScreenX() + getImage().getWidth() + 3,
					getScreenX() + getImage().getWidth() / 2 };
			int yp[] = new int[] { hy - 18,
					hy - 12,
					hy - 7,
					hy - 12,
					hy - 18,
					hy - 13 };
			
			g.setColor(SimColor.GUI_BG);
			g.setAlpha(0.6f);
			g.fillPolygon(xp, yp, xp.length);
			g.setAlpha(1);
			g.setColor(SimColor.GUI_BORDER);
			g.drawPolygon(xp, yp, xp.length);
		}
	}
	
	public boolean containsScreen(Point p) {
		Polygon poly = getCollision();
		poly.translate(getPlayground().getOffsetX(), getPlayground().getOffsetY());
		
		if(poly.contains(p)) {
			return true;
		}
		
		return false;
	}
	
	public boolean collides(Entity e) {
		Area a1 = new Area(this.getCollision());
		
		if(e != null) {
			Area a2 = new Area(e.getCollision());
			
			a1.intersect(a2);
			
			if(!a1.isEmpty()) {
				return true;
			}
		} else {
			return getPlayground().checkCollision(this);
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
		if(name.isEmpty()) name = "unnamed";
		
		this.name = name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getScreenX() {
		return x + playground.getOffsetX();
	}
	
	public void setX(int x) {
		int tmp = 0;
		
		if(x < (tmp = 0 - MathUtil.getHeighestPoint(getCollision(), MathUtil.LEFT).x + this.x + BORDER)) {
			this.x = tmp;
		} else if(x > (tmp = playground.getSize().width - MathUtil.getHeighestPoint(getCollision(), MathUtil.RIGHT).x + this.x - BORDER)) {
			this.x = tmp;
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
		int tmp = 0;
		
		if(y < (tmp = 0 - MathUtil.getHeighestPoint(getCollision(), MathUtil.TOP).y + this.y + BORDER)) {
			this.y = tmp;
		} else if(y > (tmp = playground.getSize().height - MathUtil.getHeighestPoint(getCollision(), MathUtil.BOTTOM).y + this.y - BORDER)) {
			this.y = tmp;
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
		setY(getY());
		setX(getX());
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
	
	public double getDistance(Entity e) {
		double xd = getX() - getImage().getWidth() / 2 - e.getX() + e.getImage().getWidth() / 2;
		double yd = getY() - getImage().getHeight() / 2 - e.getY() + e.getImage().getWidth() / 2;
		
		return Math.abs(Math.sqrt(Math.pow(xd, 2) + Math.pow(yd, 2)));
	}
	
	/*
	 * protected
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
