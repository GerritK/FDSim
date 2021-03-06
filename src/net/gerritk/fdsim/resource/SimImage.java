package net.gerritk.fdsim.resource;

import java.awt.image.BufferedImage;

import net.gerritk.util.ImageUtil;

public abstract class SimImage {
	/*
	 * GUI Images
	 */
	public static final BufferedImage BTN_PLAY = ImageUtil.loadImage("images/gui/btn/play.png"),
			BTN_PAUSE = ImageUtil.loadImage("images/gui/btn/pause.png"),
			BTN_RESET = ImageUtil.loadImage("images/gui/btn/reset.png"),
			BTN_MARKER = ImageUtil.loadImage("images/gui/btn/marker.png");
	
	public static final BufferedImage CREATE_MTF = ImageUtil.loadImage("images/gui/vehicles/mtf.png"),
			CREATE_TSFW = ImageUtil.loadImage("images/gui/vehicles/tsfw.png");
	
	/*
	 * Vehicles Images
	 */
	public static final BufferedImage VEH_MTF = ImageUtil.loadImage("images/entities/vehicles/mtf.png"),
			VEH_TSFW = ImageUtil.loadImage("images/entities/vehicles/tsfw.png");
	
	/*
	 * Light Images
	 */
	public static final BufferedImage LIGHT_BLUE_1 = ImageUtil.loadImage("images/lights/blue_1.png"),
			LIGHT_1 = ImageUtil.loadImage("images/lights/light_1.png");
}
