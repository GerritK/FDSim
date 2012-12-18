package net.gerritk.fdsim.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import net.gerritk.fdsim.Simulation;

public class Button extends InterfaceObject {
	private static final long serialVersionUID = -2273998620740218304L;
	
	public static final int DEFAULT = 0, ROUND_RECT = 1;
	
	private ToolTip tip;
	private boolean hover;
	private Color cText, cNormal, cHover, cBorder;
	private int style;
	private String actionCmd[] = new String[3];
	private ArrayList<ActionListener> al = new ArrayList<ActionListener>();
	
	public Button(int x, int y, int width, int height, Color cText, Color cNormal, Color cHover, Color cBorder, InterfaceObject ref) {
		super(x, y, width, height, ref);
		setColorText(cText);
		setColorNormal(cNormal);
		setColorHover(cHover);
		setColorBorder(cBorder);
	}
	
	public Button(int x, int y, int width, int height, InterfaceObject ref) {
		this(x, y, width, height, Color.BLACK, Color.GRAY, Color.LIGHT_GRAY, Color.DARK_GRAY, ref);
	}
	
	@Override
	public void update(long delta) {
		// Nothing?
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(!isVisible()) return;
		
		if(isHover()) {
			g.setColor(getColorHover());
		} else {
			g.setColor(getColorNormal());		
		}
		
		switch(style) {
			default:
			case DEFAULT:
				g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
				g.setColor(getColorBorder());
				g.drawRect((int) getX(), (int) getY(), (int) getWidth() - 1, (int) getHeight() - 1);
				break;
			
			case ROUND_RECT:
				g.fillRoundRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), 8, 8);
				g.setColor(getColorBorder());
				g.drawRoundRect((int) getX(), (int) getY(), (int) getWidth() - 1, (int) getHeight() - 1, 8, 8);
				break;
		}
		
		if(hover && tip != null && System.currentTimeMillis() - Simulation.getMouseHandler().lastMoved > 1000) {
			tip.draw(g);
		}
	}
	
	public void press(int btn) {
		if(al != null) {
			if(btn - 1 < actionCmd.length) {
				for(ActionListener a : al) {
					a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCmd[btn - 1]));
				}
			}
		}
	}
	
	/*
	 * Getter & Setter	
	 */
	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public Color getColorText() {
		return cText;
	}

	public void setColorText(Color cText) {
		this.cText = cText;
	}

	public Color getColorHover() {
		return cHover;
	}

	public void setColorHover(Color cHover) {
		this.cHover = cHover;
	}

	public Color getColorNormal() {
		return cNormal;
	}

	public void setColorNormal(Color cNormal) {
		this.cNormal = cNormal;
	}
	
	public Color getColorBorder() {
		return cBorder;
	}

	public void setColorBorder(Color cBorder) {
		this.cBorder = cBorder;
	}
	
	public void addActionListener(ActionListener al) {
		this.al.add(al);
	}
	
	public void removeActionListener(ActionListener al) {
		this.al.remove(al);
	}

	public String getActionCommand(int btn) {
		if(btn < actionCmd.length) {
			return actionCmd[btn - 1];
		}
		
		return null;
	}

	public void setActionCommand(String cmd, int btn) {
		if(btn < actionCmd.length) {
			actionCmd[btn - 1] = cmd;
		}
	}
	
	public void setActionCommand(String cmd) {
		for(int i = 0; i < actionCmd.length; i++) {
			actionCmd[i] = cmd;
		}
	}

	public String getToolTip() {
		return tip.getTip();
	}

	public void setToolTip(String tip) {
		if(tip == null) {
			this.tip = null;
		} else {
			this.tip = new ToolTip((int) getWidth() - 4, (int) - 16, tip, this);
		}
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}
}
