package net.gerritk.fdsim.resource;

import java.awt.Color;

public abstract class SimColor {
	// Colors
	public static final Color DARK_GRAY = new Color(33, 33, 33),
			FIRE_RED = new Color(204, 0, 0),
			BLUE = new Color(0, 72, 153),
			OCEAN_BLUE = new Color(3, 50, 108),
			LIGHT_GRAY = new Color(230, 230, 230);
	
	// Associated Colors
	public static final Color GUI_BG = OCEAN_BLUE,
			GUI_BORDER = Color.WHITE,
			
			GUI_BUTTON = Color.GRAY,
			GUI_BUTTON_HOVER = Color.LIGHT_GRAY,
			GUI_BUTTON_CHECKED = Color.LIGHT_GRAY,
			GUI_BUTTON_BORDER = Color.WHITE,
			GUI_BUTTON_TEXT = Color.BLACK,
			
			GUI_POPUP = Color.LIGHT_GRAY,
			GUI_POPUP_BORDER = Color.GRAY,
			GUI_POPUP_TEXT = Color.BLACK,
			
			VOID = DARK_GRAY,
			PLAYGROUND = LIGHT_GRAY;
}
