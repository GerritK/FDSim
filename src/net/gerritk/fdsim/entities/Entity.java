package net.gerritk.fdsim.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.interfaces.*;
import net.gerritk.fdsim.lights.Light;
import net.gerritk.util.GraphicsUtil;

public abstract class Entity implements Drawable, Updateable {
	private String name;
	private int x, y;
	private BufferedImage img;
	private Playground playground;
	private double rotation;
	private ArrayList<Light> lights = new ArrayList<Light>();
	
	public Entity(String name, int x, int y, BufferedImage img, Playground playground) {
		setName(name);
		setImage(img);
		setPlayground(playground);
		setX(x);
		setY(y);
	}
	
	@Override
	public void update(long delta) {
		for(Light l : lights) {
			l.update(delta);
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		AffineTransform af = g.getTransform();
        g.rotate(Math.toRadians(- getRotation()), getScreenX() + getImage().getWidth() / 2 + 1, getScreenY() + getImage().getHeight() / 2 + 1);
		
		if(isSelected()) {
			if(collides(null)) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.GREEN);
			}
			
			GraphicsUtil.setThickness(g, 3);
			g.drawRoundRect(getScreenX() - 2, getScreenY() - 2, img.getWidth() + 4, img.getHeight() + 4, 3, 3);
			GraphicsUtil.setThickness(g, 1);
		}
		
        g.drawImage(getImage(), getScreenX(), getScreenY(), null);
        
        for(Light l : lights) {
        	l.draw(g);
        }
        
        g.setTransform(af);
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
	
	protected void addLight(Light light) {
		lights.add(light);
	}
	
	protected void addLights(Light lights[]) {
		for(Light l : lights) {
			this.lights.add(l);
		}
	}
}
