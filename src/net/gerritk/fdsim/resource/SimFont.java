package net.gerritk.fdsim.resource;

import java.awt.Font;

import net.gerritk.util.OwnFont;

public class SimFont {
	public static final Font TEXT = new Font("Verdana", Font.PLAIN, 12),
			COPY = new Font("Verdana", Font.BOLD, 8),
			TOOLTIP = new Font("Verdana", Font.PLAIN, 12),
			PAUSED_BIG = new Font("Verdana", Font.BOLD, 24),
			PAUSED_SMALL = new Font("Verdana", Font.ITALIC, 14),
			TEXT_CREATE = new Font("Verdana", Font.PLAIN, 8),
			TEXT_CREATE_COUNT = new Font("Verdana", Font.BOLD, 18);
	
	// Own Fonts
	public static final OwnFont CLOCK = new OwnFont("DS-DIGIT.TTF", Font.TRUETYPE_FONT);
}
