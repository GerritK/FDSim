package net.gerritk.fdsim.lights;

import java.awt.image.BufferedImage;

import net.gerritk.fdsim.entities.Entity;
import net.gerritk.fdsim.interfaces.*;
import net.gerritk.util.ExGraphics;

public class Light implements Drawable, Updateable {
	public static final int TYPE_BLUELIGHT = 0, UNDEFINED = -1, HEADLIGHT = 1;
	
	private int x;
	private int y;
	private BufferedImage img;
	private Entity reference;
	private int type;
	private boolean enabled;
	
	public Light(int x, int y, BufferedImage img, int type, Entity reference) {
		setX(x);
		setY(y);
		setImage(img);
		setType(type);
		setReference(reference);
	}
	
	@Override
	public void update(long delta) {
		// Nothing?
	}
	
	@Override
	public void draw(ExGraphics g) {
		if(isEnabled()) {
			g.drawImage(getImage(), getScreenX() - getImage().getWidth() / 2, getScreenY() - getImage().getHeight() / 2, null);
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public Entity getReference() {
		return reference;
	}
	
	public void setReference(Entity reference) {
		this.reference = reference;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public void setImage(BufferedImage img) {
		this.img = img;
	}

	public int getX() {
		return x + getReference().getX();
	}
	
	public int getScreenX() {
		return x + getReference().getScreenX();
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y + getReference().getY();
	}
	
	public int getScreenY() {
		return y + getReference().getScreenY();
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
