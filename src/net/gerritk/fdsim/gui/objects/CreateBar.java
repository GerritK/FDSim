package net.gerritk.fdsim.gui.objects;

import java.awt.Color;

import net.gerritk.fdsim.Simulation;
import net.gerritk.fdsim.entities.Entity;
import net.gerritk.fdsim.gui.*;
import net.gerritk.fdsim.input.objects.CreateHandler;
import net.gerritk.fdsim.resource.SimColor;
import net.gerritk.fdsim.resource.SimImage;
import net.gerritk.util.ExGraphics;

public class CreateBar extends Bar {
	private static final long serialVersionUID = 4391913123355821132L;
	
	private boolean extended;
	private Button btnToggle;
	
	private CreateButton create[];
	private Entity toCreate;
	private CreateButton toCreateButton;
	
	public CreateBar(int x, int width) {
		super(x, 50, width, 0, null);
		
		setButtonHandler(new CreateHandler(this));
		
		btnToggle = new Button(width - 8, 20, 8, (int) (getHeight() - getY()) - 40, Color.BLACK, SimColor.GUI_BG, SimColor.GUI_BORDER, SimColor.GUI_BORDER, SimColor.GUI_BORDER, 0.2f, this);
		btnToggle.setActionCommand("createBar");
		btnToggle.setToolTip("Menü ein-/ausblenden");
		btnToggle.addActionListener(Simulation.getButtonHandler());
		Simulation.buttons.add(btnToggle);
		
		// TODO change it later
		create = new CreateButton[2];
		
		create[0] = new CreateButton(SimImage.CREATE_MTF, "MTF", -1, 2, 3, width - 12, 35, this);
		create[0].setActionCommand("mtf");
		create[0].addActionListener(getButtonHandler());
		Simulation.buttons.add(create[0]);
		
		create[1] = new CreateButton(SimImage.CREATE_TSFW, "TSF-W", -1, 2, 40, width - 12, 35, this);
		create[1].setActionCommand("tsfw");
		create[1].addActionListener(getButtonHandler());
		Simulation.buttons.add(create[1]);
		
		setExtended(false);
	}
	
	@Override
	public void draw(ExGraphics g) {
		g.setColor(SimColor.GUI_BG);
		g.setAlpha(0.6f);
		g.fillRoundRect((int) getX() - 4, (int) getY(), (int) getWidth() + 4, Simulation.getInstance().getHeight() - (int) getY() * 2, 8, 8);
		g.setAlpha(1);
		g.setColor(SimColor.GUI_BORDER);
		g.drawRoundRect((int) getX() - 4, (int) getY(), (int) getWidth() + 3, Simulation.getInstance().getHeight() - (int) getY() * 2 - 1, 8, 8);
		g.drawLine((int) (getX() + getWidth()) - 8, (int) getY(), (int) (getX() + getWidth()) - 8, (int) (getHeight()) - 1);
		
		btnToggle.draw(g);
		
		for(CreateButton b : create) {
			b.draw(g);
		}
	}
	
	@Override
	public void update(long delta) {
		setHeight(Simulation.getInstance().getHeight() - (int) getY());
		btnToggle.setHeight((int) (getHeight() - getY()) - 40);
		
		btnToggle.update(delta);
		
		for(CreateButton b : create) {
			b.update(delta);
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
		
		if(extended) {
			setX(0);
		} else {
			setX((int) - getWidth() + 8);
		}
	}

	public Entity getToCreate() {
		return toCreate;
	}

	public void setToCreate(Entity toCreate) {
		this.toCreate = toCreate;
	}

	public CreateButton getToCreateButton() {
		return toCreateButton;
	}

	public void setToCreateButton(CreateButton toCreateButton) {
		this.toCreateButton = toCreateButton;
	}
}
