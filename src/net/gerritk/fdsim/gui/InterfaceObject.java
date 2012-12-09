package net.gerritk.fdsim.gui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class InterfaceObject extends Rectangle {	
	private static final long serialVersionUID = -4343835306838898044L;
	
	private InterfaceObject ref;
	
	public InterfaceObject(int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height);
		setReference(ref);
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g);
	
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
}
