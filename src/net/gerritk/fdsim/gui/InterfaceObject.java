package net.gerritk.fdsim.gui;

import java.awt.Point;
import java.awt.Rectangle;

import net.gerritk.fdsim.interfaces.*;

public abstract class InterfaceObject extends Rectangle implements Drawable, Updateable {	
	private static final long serialVersionUID = -4343835306838898044L;
	
	private InterfaceObject ref;
	private boolean visible = true;
	
	public InterfaceObject(int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height);
		setReference(ref);
	}
	
	@Override
	public boolean contains(Point p) {
		if(p.getX() >= getX() && p.getX() <= getX() + getWidth() && p.getY() >= getY() && p.getY() <= getY() + getHeight()) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Getter & Setter
	 */
	public InterfaceObject getReference() {
		return ref;
	}

	public void setReference(InterfaceObject ref) {
		this.ref = ref;
	}
	
	public double getX() {
		if(getReference() != null) {
			return super.getX() + getReference().getX();
		}
		
		return super.getX();
	}
	
	public double getY() {
		if(getReference() != null) {
			return super.getY() + getReference().getY();
		}
		
		return super.getY();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
