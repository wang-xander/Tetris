package com.xander.tetris.model;

public abstract class Brick {
	protected int positionX = 0;
	protected int positionY = 0;

	protected Wall wall;
	protected Atom atoms[];
	protected int direction = 0;

	public synchronized Atom[] getAtoms() {
		return atoms;
	}

	public synchronized int getPositionX() {
		return positionX;
	}

	public synchronized void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public synchronized int getPositionY() {
		return positionY;
	}

	public synchronized void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public synchronized static Brick getInstance(Wall wall) {
		int rdm = generateRandom();
		Brick b = null;
		switch(rdm % 7) {
			case 0 :
				b = new BrickA(wall);
				break;
			case 1 :
				b = new BrickB(wall);
				break;
			case 2 :
				b = new BrickC(wall);
				break;
			case 3 :
				b = new BrickD(wall);
				break;
			case 4 :
				b = new BrickE(wall);
				break;
			case 5 :
				b = new BrickF(wall);
				break;
			case 6 :
				b = new BrickG(wall);
				break;
		}
		return b;
	}

	public Brick() {

	}

	public Brick(Wall wall) {
		this.positionX = wall.getWidth() / 2;
		this.positionY = 0;
		this.wall = wall;
		atoms = new Atom[4];
		this.atoms[0] = new Atom();
		this.atoms[1] = new Atom();
		this.atoms[2] = new Atom();
		this.atoms[3] = new Atom();
		this.direction = generateRandom();
		this.changeDirection(direction);
	}

	public synchronized static Brick getInstance(Wall newWall, Brick brick) {
		Brick b = null;
		;
		try{
			b = (Brick)brick.getClass().newInstance();
			b.positionX = newWall.getWidth() / 2;
			b.positionY = 0;
			b.wall = newWall;
			b.atoms = new Atom[4];
			b.atoms[0] = new Atom(brick.getAtoms()[0].getOffsetX(), brick.getAtoms()[0].getOffsetY());
			b.atoms[1] = new Atom(brick.getAtoms()[1].getOffsetX(), brick.getAtoms()[1].getOffsetY());
			b.atoms[2] = new Atom(brick.getAtoms()[2].getOffsetX(), brick.getAtoms()[2].getOffsetY());
			b.atoms[3] = new Atom(brick.getAtoms()[3].getOffsetX(), brick.getAtoms()[3].getOffsetY());
		}catch(InstantiationException e){
			e.printStackTrace();

		}catch(IllegalAccessException e){
			e.printStackTrace();

		}
		return b;
	}

	private synchronized static int generateRandom() {
		double tmp = Math.random();
		return Double.valueOf(tmp * 10000).intValue();
	}

	public synchronized Wall getWall() {
		return wall;
	}

	public synchronized void moveLeft() {
		if(this.detectLeft() <= 0)
			return;
		this.positionX -= 1;
	}

	public synchronized void moveRight() {
		if(this.detectRight() <= 0)
			return;
		this.positionX += 1;
	}

	public synchronized void fall() {
		if(this.detectButtom() <= 0)
			return;
		this.positionY += 1;
	}

	public synchronized int detectButtom() {
		int result = 1;
		int min = 1;
		for(Atom a : atoms){
			if(this.positionY + a.getOffsetY() + 1 > wall.getHeight()){
				result = -1;
				if(result < min){
					min = result;
				}
			}else if(this.positionY + a.getOffsetY() + 1 == wall.getHeight()){
				result = 0;
				if(result < min){
					min = result;
				}
			}else if(wall.isBlocked(this.positionX + a.getOffsetX(), this.positionY + a.getOffsetY() + 1)){
				result = 0;
				if(result < min){
					min = result;
				}
			}
		}
		System.out.println("detectButtom result: " + result);
		return result;
	}

	public synchronized int detectLeft() {
		int result = 1;
		int min = 1;
		for(Atom a : atoms){
			if(this.positionX + a.getOffsetX() == 0){
				result = 0;
				if(result < min){
					min = result;
				}
			}else if(this.positionX + a.getOffsetX() < 0){
				result = -1;
				if(result < min){
					min = result;
				}
			}else if(wall.isBlocked(this.positionX + a.getOffsetX() - 1, this.positionY + a.getOffsetY())){
				result = 0;
				if(result < min){
					min = result;
				}
			}
		}
		System.out.println("detectLeft result: " + result);
		return min;
	}

	public synchronized int detectRight() {
		int result = 1;
		int min = 1;
		for(Atom a : atoms){
			if(this.positionX + a.getOffsetX() + 1 > wall.getWidth()){
				result = -1;
				if(result < min){
					min = result;
				}
			}else if(this.positionX + a.getOffsetX() + 1 == wall.getWidth()){
				result = 0;
				if(result < min){
					min = result;
				}
			}else if(wall.isBlocked(this.positionX + a.getOffsetX() + 1, this.positionY + a.getOffsetY())){
				result = 0;
				if(result < min){
					min = result;
				}
			}
		}
		System.out.println("detectRight result: " + result);
		return min;
	}

	public synchronized void rollRight() {
		this.direction++ ;
		this.changeDirection(direction);
	}

	public synchronized void rollLeft() {
		this.direction-- ;
		this.changeDirection(direction);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName()).append("\n");
		sb.append("[").append(this.atoms[0].getOffsetX()).append(", ").append(this.atoms[0].getOffsetY()).append("], ");
		sb.append("[").append(this.atoms[1].getOffsetX()).append(", ").append(this.atoms[1].getOffsetY()).append("], ");
		sb.append("[").append(this.atoms[2].getOffsetX()).append(", ").append(this.atoms[2].getOffsetY()).append("], ");
		sb.append("[").append(this.atoms[3].getOffsetX()).append(", ").append(this.atoms[3].getOffsetY()).append("]\n");
		return sb.toString();
	}

	abstract protected void changeDirection(int direction);

}
