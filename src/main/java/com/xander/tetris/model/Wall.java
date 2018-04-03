package com.xander.tetris.model;

public interface Wall {
		public int getWidth();
		public int getHeight();
		public boolean isBlocked(int x, int y);
}
