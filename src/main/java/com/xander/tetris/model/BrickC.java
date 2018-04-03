package com.xander.tetris.model;

public class BrickC extends Brick {
/**
 *        **
 *        **
 * 
 */
	public BrickC(Wall wall) {
		super(wall);
		this.changeDirection(direction);
	}
	public BrickC() {
		super();
	}

	@Override
	protected void changeDirection(int direction) {
		up();
	}

	private void up() {
		this.atoms[0].setOffset(0,0);
		this.atoms[1].setOffset(0,-1);
		this.atoms[2].setOffset(1,0);
		this.atoms[3].setOffset(1,-1);
	}
}
