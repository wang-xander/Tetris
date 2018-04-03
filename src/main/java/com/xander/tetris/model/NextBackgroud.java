package com.xander.tetris.model;

public class NextBackgroud implements Wall {
	public int getWidth() {
		return 4;
	}

	public int getHeight() {
		return 4;
	}

	public boolean isBlocked(int x, int y) {
		return false;
	}
	
	
}
