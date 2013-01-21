package net.gerritk.fdsim.lists;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.entities.Entity;

public class EntityList<T extends Entity> extends ArrayList<T> {
	private static final long serialVersionUID = 8769357991022820292L;
	
	
	/*
	 * Filter List
	 */	
	public EntityList<T> getByPlayground(Playground playground) {
		EntityList<T> el = new EntityList<T>();
		
		for(T e : this) {
			if(e.getPlayground() == playground) {
				el.add(e);
			}
		}
		
		return el;
	}
	
	public EntityList<T> getByName(String name) {
		EntityList<T> el = new EntityList<T>();
		
		for(T e : this) {
			if(e.getName().equals(name)) {
				el.add(e);
			}
		}
		
		return el;
	}
	
	public EntityList<T> getByImage(BufferedImage img) {
		EntityList<T> el = new EntityList<T>();
		
		for(T e : this) {
			if(e.getImage() == img) {
				el.add(e);
			}
		}
		
		return el;
	}
	
	public EntityList<T> getByDistance(double distance, T src) {
		EntityList<T> el = new EntityList<T>();
		
		for(T e : this) {
			if(e.getDistance(src) <= distance) {
				el.add(e);
			}
		}
		
		return el;
	}
}
