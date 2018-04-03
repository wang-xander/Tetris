package com.xander.tetris.model;

public class Atom {
	private int x;
	private int y;

	public Atom() {
	}
	public Atom(int x, int y) {
		this();
		this.setOffset(x, y);
	}

	public int getOffsetX() {
		return x;
	}

	public int getOffsetY() {
		return y;
	}

	public void setOffset(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
