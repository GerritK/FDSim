package net.gerritk.fdsim.gui;

import java.awt.Color;
import java.awt.Font;

import net.gerritk.util.ExGraphics;
import net.gerritk.util.StringUtil;

public class ToolTip extends InterfaceObject {
	private static final long serialVersionUID = -8785046203601800495L;
	
	private String tip;
	
	public ToolTip(int x, int y, String tip, InterfaceObject ref) {
		super(x, y, 0, 0, ref);
		
		setTip(tip);
	}
	
	@Override
	public void drawGUI(ExGraphics g) {
		g.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		setWidth(StringUtil.getWidth(getTip(), g));
		setHeight(StringUtil.getHeight(getTip(), g));
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect((int) getX(), (int) getY(), (int) getWidth() + 4, (int) getHeight());
		g.setColor(Color.BLACK);
		g.drawString(getTip(), (int) getX() + 2, (int) (getY() + getHeight()) - 2);
		g.setColor(Color.GRAY);
		g.drawRect((int) getX(), (int) getY(), (int) getWidth() + 3, (int) getHeight());
	}
	
	@Override
	public void update(long delta) {
		// TODO Auto-generated method stub
		
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
}
