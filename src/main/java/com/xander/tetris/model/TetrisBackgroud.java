package com.xander.tetris.model;

public class TetrisBackgroud implements Wall {
	private final int WIDTH = 15;
	private final int HEIGHT = 25;

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	private Cell[][] cells = new Cell[WIDTH][HEIGHT];

	public TetrisBackgroud() {
		for(int i = 0; i < HEIGHT; i++ ){
			for(int j = 0; j < WIDTH; j++ ){
				cells[j][i] = new Cell();
			}
		}
	}

	public boolean isBlocked(int x, int y) {
		if(y < 0){
			return false;
		}
		if(x < 0 || x >= WIDTH || y >= HEIGHT){
			return true;
		}
//		System.out.println("isBlocked:X:" + x + ",Y:" + y);
		return this.cells[x][y].isBlocked();
	}

	public synchronized boolean heapUp(Brick brick) {
		Atom[] atoms = brick.getAtoms();
		for(Atom a : atoms){
			if(brick.getPositionY() + a.getOffsetY() <= 0){
				return false;
			}
			System.out.println("heapup:X=" + brick.getPositionX() + " " + a.getOffsetX() + ",Y=" + brick.getPositionY() + " " + a.getOffsetY());
			this.cells[brick.getPositionX() + a.getOffsetX()][brick.getPositionY() + a.getOffsetY()].setBlocked(true);
		}
		return true;
	}

	public synchronized int distroyRows() {
		int mark = 0;
		for(int i = 0; i < HEIGHT; i++ ){
			int count = 0;
			for(int j = 0; j < WIDTH; j++ ){
				if(cells[j][i].isBlocked()){
					count++ ;
				}else{
					break;
				}
			}
			if(count == WIDTH){
				mark++ ;
				for(int t = i; t > 0; t-- ){
					for(int j = 0; j < WIDTH; j++ ){
						cells[j][t].setBlocked(cells[j][t - 1].isBlocked());
					}
				}
			}
		}
		return mark;
	}

	//
	// public static void main(String[] a) {
	// String s[][] = new String[10][5];
	// for(int i = 0; i < 5; i++ ){
	// for(int j = 0; j < 10; j++ ){
	// s[j][i] = i + "" + j;
	// }
	// }
	// }

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < HEIGHT; i++ ){
			for(int j = 0; j < WIDTH; j++ ){
				if(this.cells[j][i].isBlocked()){
					sb.append("-*");
				}else{
					sb.append("--");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	class Cell {
		private boolean blocked = false;

		public boolean isBlocked() {
			return blocked;
		}

		public void setBlocked(boolean blocked) {
			this.blocked = blocked;
		}
	}
}
