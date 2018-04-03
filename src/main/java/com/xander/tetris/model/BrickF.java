package com.xander.tetris.model;

public class BrickF extends Brick {
/**
 *       * 
 *       ***
 *        
 * 
 */
	public BrickF(Wall wall) {
		super(wall);
		this.changeDirection(direction);
	}
	public BrickF() {
		super();
	}

	@Override
	protected void changeDirection(int direction) {
		switch (direction % 4) {
		case 0:
			up();
			break;
		case 1:
			right();
			break;
		case 2:
			down();
			break;
		case 3:
			left();
			break;
		}
	}

	private void up() {
		this.atoms[0].setOffset(0, 0);
		this.atoms[1].setOffset(-1, 0);
		this.atoms[2].setOffset(1, 0);
		this.atoms[3].setOffset(-1, -1);
	}

	private void left() {
		this.atoms[0].setOffset(0, 0);
		this.atoms[1].setOffset(0, -1);
		this.atoms[2].setOffset(-1, 1);
		this.atoms[3].setOffset(0, 1);
	}

	private void down() {
		this.atoms[0].setOffset(0, 0);
		this.atoms[1].setOffset(1, 0);
		this.atoms[2].setOffset(-1, 0);
		this.atoms[3].setOffset(1, 1);
	}

	private void right() {
		this.atoms[0].setOffset(0, 0);
		this.atoms[1].setOffset(0, -1);
		this.atoms[2].setOffset(1, -1);
		this.atoms[3].setOffset(0, 1);
	}

}
