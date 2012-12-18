package net.gerritk.fdsim.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import net.gerritk.fdsim.Simulation;
import net.gerritk.util.GraphicsUtil;

public class Button extends InterfaceObject {
	private static final long serialVersionUID = -2273998620740218304L;
	
	private ToolTip tip;
	private boolean hover;
	private Color cText, cNormal, cHover;
	private String actionCmd[] = new String[3];
	private ArrayList<ActionListener> al = new ArrayList<ActionListener>();
	
	public Button(int x, int y, int width, int height, Color cText, Color cNormal, Color cHover, InterfaceObject ref) {
		super(x, y, width, height, ref);
		setColorText(cText);
		setColorNormal(cNormal);
		setColorHover(cHover);
	}
	
	public Button(int x, int y, int width, int height, InterfaceObject ref) {
		this(x, y, width, height, Color.BLACK, Color.GRAY, Color.LIGHT_GRAY, ref);
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
		
		g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
		
		g.setColor(Color.BLACK);
		GraphicsUtil.setAlpha(g, 0.7f);
		g.drawRect((int) getX(), (int) getY(), (int) getWidth() - 1, (int) getHeight() - 1);
		GraphicsUtil.setAlpha(g, 1);
		
		if(hover && tip != null && Simulation.getMouseHandler().lastMoved > 5000) {
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
			this.tip = new ToolTip((int) getWidth() + 2, (int) - getHeight() - 2, tip, this);
		}
	}
}
