package net.gerritk.fdsim;

import java.awt.*;
import java.util.ArrayList;

import net.gerritk.fdsim.entities.*;
import net.gerritk.fdsim.entities.vehicles.*;
import net.gerritk.fdsim.interfaces.*;
import net.gerritk.fdsim.resource.SimFont;
import net.gerritk.util.*;

public class Playground implements Drawable, DrawableGUI, Updateable {
	private static int lastID;
	
	private int id;
	private String title;
	private ArrayList<Entity> entities;
	private long startTime;
	private Color timeColor;
	
	private Dimension size;
	private int offsetX, offsetY;
	private Point start;
	
	private Entity selectedEntity;
	
	public Playground(String title, Dimension size, Point start, long startTime) {
		this.id = lastID++;
		this.entities = new ArrayList<Entity>();
		
		setTitle(title);
		setSize(size);
		setStart(start);
		setStartTime(startTime);
		
		goStart();
		
		// TEST
		int base = 1;
		int t = 1;
		int m = 1;
		int s = 3;
		
		for(int i = 0; i < 10; i++) {
			int type = (int) (Math.random() * 2);
			int x = (int) (Math.random() * size.getWidth());
			int y = (int) (Math.random() * size.getHeight());
			int rot = (int) (Math.random() * 72);
			
			if(t == 1 && m == 1) {
				s = (int) (Math.random() * 5) + 1;
			}
			
			Entity e = null;
			
			if(type == 0) {
				e = new MTF(base + "-17-" + m++, x, y, this);
			} else if(type == 1) {
				e = new TSFW(base + "-41-" + t++, x, y, this);
			}
			
			if(m + t - 2 == s) {
				m = 1;
				t = 1;
				base++;
			}
			
			e.setRotation(rot * 5);
			entities.add(e);
		}
	}
	
	@Override
	public void update(long delta) {
		int c = (int) Math.abs(TimeUtil.getMillisInFullHours(Simulation.getSimulationTime(true)));
		if(c > 12) {
			c = 12 - (c - 12);
		}
		
		timeColor = new Color(87 + c * 14, 87 + c * 14, 87 + c * 14);
		
		for(Entity e : entities) {
			e.update(delta);
		}
	}
	
	@Override
	public void draw(ExGraphics g) {
		g.setFont(SimFont.HEADING);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect(offsetX + 1, offsetY - 15, StringUtil.getWidth(getTitle(), g) + 11, 20, 8, 8);
		g.setColor(Color.GRAY);
		g.drawRoundRect(offsetX, offsetY - 15, StringUtil.getWidth(getTitle(), g) + 12, 21, 8, 8);
		g.setColor(Color.BLACK);
		g.drawString(getTitle(), offsetX + 7, offsetY - 2);
		
		g.setColor(timeColor);
		g.fillRect(offsetX, offsetY, size.width, size.height);
		
		for(Entity e : entities) {
			if(e != getSelectedEntity()) {
				e.draw(g);
				g.resetTransform();
			}
		}
		
		// Selected always on top!
		if(getSelectedEntity() != null) {
			getSelectedEntity().draw(g);
			g.resetTransform();
		}
		
		g.setColor(Color.GRAY);
		g.drawRect(offsetX, offsetY, size.width - 1, size.height - 1);
	}
	
	@Override
	public void drawGUI(ExGraphics g) {
		for(Entity e : entities) {
			if(e != getSelectedEntity()) {
				e.drawGUI(g);
			}
		}
		
		if(getSelectedEntity() != null) {
			getSelectedEntity().drawGUI(g);
		}
	}
	
	public void goStart() {
		setOffsetX(getStart().x);
		setOffsetY(getStart().y);
	}
	
	public boolean checkCollision(Entity e) {
		for(Entity s : entities) {
			if(e != s && e.getDistance(s) < 250 && e.collides(s)) {
				return true;
			}
		}
		
		return false;
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
	
	public Entity getNextEntity(Entity e) {
		Entity tmp = null;
		
		for(Entity s : entities) {
			if(s != e && (tmp == null || e.getDistance(s) < e.getDistance(tmp))) {
				tmp = s;
			}
		}
		
		return tmp;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e);
	}
}
