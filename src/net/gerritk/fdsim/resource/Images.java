package net.gerritk.fdsim.resource;

import java.awt.image.BufferedImage;

import net.gerritk.util.ImageUtil;

public abstract class Images {
	public static final BufferedImage BTN_PLAY = ImageUtil.loadImage("images/gui/btn/play.png"),
			BTN_PAUSE = ImageUtil.loadImage("images/gui/btn/pause.png"),
			BTN_RESET = ImageUtil.loadImage("images/gui/btn/reset.png");
	
	public static final BufferedImage VEH_MTF = ImageUtil.loadImage("images/entities/vehicles/mtf.png");
}
