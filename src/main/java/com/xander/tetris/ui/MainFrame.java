package com.xander.tetris.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Thread.State;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.xander.tetris.model.Brick;
import com.xander.tetris.model.NextBackgroud;
import com.xander.tetris.model.TetrisBackgroud;

public class MainFrame extends JFrame {
	private WallTable wallTable;
	private TetrisBackgroud currentWall;
	private NextBackgroud nextWall;
	private Brick currentBrick;
	private Brick nextBrick;
	private int level = 1;
	private int score = 0;
	private MainFrame thisFrame;
	private JLabel scoreLabel = new JLabel("Score:");
	private JLabel scoreField = new JLabel("");
	private JLabel nextLabel = new JLabel("Next:");
	private NextTable nextTable;
	private JPanel rightPanel = new JPanel();
	private JLabel keyAssistLabel = new JLabel("<HTML>Key assist:<br/>←: Move left<br/>→: Move right<br/>Space: Speed down<br/>  A: Roll left<br/>  S: Roll right</HTML>");
	private JButton startPauseButton = new JButton("start/pause");

	private boolean isSuspended = false;
	private BrickFallingThread brickFallingThread;
	private KeyListener tetrisKeyListener = new TetrisKeyListener();

	public MainFrame() {
		this.setSize(400, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.scoreField.setText(this.score + "");
		this.currentWall = new TetrisBackgroud();
		this.wallTable = new WallTable(currentWall);
		this.nextWall = new NextBackgroud();
		this.nextTable = new NextTable(nextWall);

		this.setLayout(new GridBagLayout());
		this.add(this.wallTable, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		rightPanel.setLayout(new GridBagLayout());
		startPauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(brickFallingThread == null || brickFallingThread.getState() == State.TERMINATED){
					brickFallingThread = new BrickFallingThread();
				}
				if(brickFallingThread.getState() == State.NEW){
					currentBrick = Brick.getInstance(currentWall);
					wallTable.setBrick(currentBrick);

					nextBrick = Brick.getInstance(nextWall);
					nextTable.setBrick(nextBrick);

					thisFrame.addKeyListener(tetrisKeyListener);
					brickFallingThread.start();
					isSuspended = false;
				}else if(isSuspended){
					thisFrame.addKeyListener(tetrisKeyListener);
					brickFallingThread.resume();
					isSuspended = false;
				}else{
					thisFrame.removeKeyListener(tetrisKeyListener);
					brickFallingThread.suspend();
					isSuspended = true;
				}
			}
		});
		startPauseButton.setFocusable(false);
		rightPanel.add(this.scoreLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		rightPanel.add(this.scoreField, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		rightPanel.add(this.nextLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		rightPanel.add(this.nextTable, new GridBagConstraints(0, 3, 1, 1, 1, 3, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		rightPanel.add(this.keyAssistLabel, new GridBagConstraints(0, 4, 1, 1, 1, 5, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		rightPanel.add(this.startPauseButton, new GridBagConstraints(0, 5, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		this.add(this.rightPanel, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		this.setVisible(true);
		this.thisFrame = this;
	}
	class NullKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
		
	}
	class TetrisKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				currentBrick.moveLeft();
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				currentBrick.moveRight();
			}else if(e.getKeyCode() == KeyEvent.VK_A){
				System.out.println("rollLeft");
				currentBrick.rollLeft();
				if(currentBrick.detectRight() < 0 || currentBrick.detectLeft() < 0 || currentBrick.detectButtom() < 0){
					currentBrick.rollRight();
				}
			}else if(e.getKeyCode() == KeyEvent.VK_S){
				System.out.println("rollRight");
				currentBrick.rollRight();
				if(currentBrick.detectRight() < 0 || currentBrick.detectLeft() < 0 || currentBrick.detectButtom() < 0){
					currentBrick.rollLeft();
				}
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				currentBrick.fall();
				currentBrick.fall();
			}
			wallTable.refresh();
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}
	}
	class BrickFallingThread extends Thread {
		@Override
		public void run() {
			while(score < 100){
				// System.out.println(currentWall);
				if(currentBrick.detectButtom() <= 0){
					boolean successful = currentWall.heapUp(currentBrick);
					if(!successful){
						break;
					}
					score += currentWall.distroyRows();
					scoreField.setText(score + "");
					currentBrick = Brick.getInstance(currentWall, nextBrick);
					nextBrick = Brick.getInstance(nextWall);
					System.out.println(nextBrick);
					nextTable.setBrick(nextBrick);
					wallTable.setBrick(currentBrick);
				}else{
					currentBrick.fall();
				}
				wallTable.refresh();

				try{
					Thread.sleep(500 - level * 50);
				}catch(InterruptedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(score >= 100){
				JOptionPane.showMessageDialog(thisFrame, "You win!", "Congratulations", JOptionPane.PLAIN_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(thisFrame, "You lose!", "Failed", JOptionPane.PLAIN_MESSAGE);
			}
		}
	};

	public static void main(String[] a) {
		MainFrame mainFrame = new MainFrame();
	}
}
