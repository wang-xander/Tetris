package com.xander.tetris.model;

public class BrickE extends Brick {
/**
 *        *
 *        *
 *        *
 *        *
 */
	public BrickE(Wall wall) {
		super(wall);
		this.changeDirection(direction);
	}
	public BrickE() {
		super();
	}

	@Override
	protected void changeDirection(int direction) {
		switch (direction % 2) {
		case 0:
			up();
			break;
		case 1:
			right();
			break;
		}
	}

	private void up() {
		this.atoms[0].setOffset(0, -2);
		this.atoms[1].setOffset(0, -1);
		this.atoms[2].setOffset(0, 0);
		this.atoms[3].setOffset(0, 1);
	}

	private void right() {
		this.atoms[0].setOffset(-1, 0);
		this.atoms[1].setOffset(0, 0);
		this.atoms[2].setOffset(1, 0);
		this.atoms[3].setOffset(2, 0);
	}
}
