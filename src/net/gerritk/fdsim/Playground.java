package net.gerritk.fdsim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import net.gerritk.fdsim.entities.*;
import net.gerritk.fdsim.entities.vehicles.*;

public class Playground {
	private static int lastID;
	
	private int id;
	private String title;
	private ArrayList<Entity> entities;
	
	private Dimension size;
	private int offsetX, offsetY;
	private Point start;
	
	private Entity selectedEntity;
	
	public Playground(String title, Dimension size, Point start) {
		this.id = lastID++;
		this.entities = new ArrayList<Entity>();
		
		setTitle(title);
		setSize(size);
		setStart(start);
		
		goStart();
		
		// TEST
		entities.add(new MTF("test", 100, 100, this));
	}
	
	public void update() {
		for(Entity e : entities) {
			e.update();
		}
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(offsetX, offsetY, size.width, size.height);
		
		for(Entity e : entities) {
			e.draw(g);
		}
		
		g.setColor(Color.GRAY);
		g.drawRect(offsetX, offsetY, size.width - 1, size.height - 1);
	}
	
	public void goStart() {
		setOffsetX(getStart().x);
		setOffsetY(getStart().y);
	}
	
	/*
	 * Getter & Setter
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getID() {
		return id;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		int biggerSpace = Simulation.getInstance().getWidth() - getSize().width;
		biggerSpace = (biggerSpace - 100 < 0 ? 50 : biggerSpace / 2);
		
		if(offsetX < Simulation.getInstance().getWidth() - getSize().width - biggerSpace) {
			this.offsetX = Simulation.getInstance().getWidth() - getSize().width - biggerSpace;
		} else if(offsetX > biggerSpace) {
			this.offsetX = biggerSpace;
		} else {
			this.offsetX = offsetX;
		}
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		int biggerSpace = Simulation.getInstance().getHeight() - getSize().height;
		biggerSpace = (biggerSpace - 100 < 0 ? 50 : biggerSpace / 2);
		
		if(offsetY < Simulation.getInstance().getHeight() - getSize().height - biggerSpace) {
			this.offsetY = Simulation.getInstance().getHeight() - getSize().height - biggerSpace;
		} else if(offsetY > biggerSpace) {
			this.offsetY = biggerSpace;
		} else {
			this.offsetY = offsetY;
		}
	}
	
	public void updateOffset() {
		setOffsetY(getOffsetY());
		setOffsetX(getOffsetX());
	}
	
	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public Entity getSelectedEntity() {
		return selectedEntity;
	}

	public void setSelectedEntity(Entity selectedEntity) {
		this.selectedEntity = selectedEntity;
	}

	public Entity getEntity(Point p) {
		for(Entity e : entities) {
			if(e.containsScreen(p)) {
				return e;
			}
		}
		
		return null;
	}
}
