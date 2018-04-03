package com.xander.tetris.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.xander.tetris.model.Atom;
import com.xander.tetris.model.Brick;
import com.xander.tetris.model.TetrisBackgroud;

public class WallTable extends JTable {
	private static final int scale = 15;
	private WallTableModel model;

	public WallTable(TetrisBackgroud wall) {
		super();
		TableColumnModel columnModel = this.getColumnModel();
		columnModel.setColumnSelectionAllowed(false);
		this.getTableHeader().setVisible(false);
		this.setDefaultRenderer(Object.class, new WallCellRenderer());
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setMaxWidth(scale);
			column.setMinWidth(scale);
			column.setPreferredWidth(scale);
			column.setResizable(false);
		}
		this.setFocusable(false);
		this.setRowHeight(scale);
		this.setCellSelectionEnabled(false);
		this.model = new WallTableModel(wall);
		this.setModel(model);
	}

	public void refresh() {
		this.model.fireTableChanged(new TableModelEvent(model));
	}

	public void setBrick(Brick brick){
		this.model.setBrick(brick);
	}
	
	class WallCellRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component comp = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			WallTableModel model = (WallTableModel)table.getModel();
			if(model.isBlock(row, column)){
				comp.setBackground(Color.black);
			}else{
				comp.setBackground(Color.white);
			}
			return comp;
		}
	}
	
	class WallTableModel extends AbstractTableModel {
		private TetrisBackgroud wall;
		private Brick brick;
		
		public TetrisBackgroud getWall() {
			return wall;
		}

		public void setWall(TetrisBackgroud wall) {
			this.wall = wall;
		}

		public Brick getBrick() {
			return brick;
		}

		@Override
		public int getRowCount() {
			return wall.getHeight();
		}

		@Override
		public int getColumnCount() {
			return wall.getWidth();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return "";
		}
		
		public boolean isBlock(int rowIndex, int columnIndex) {
			return wall.isBlocked(columnIndex, rowIndex) | this.isBrick(rowIndex, columnIndex);
		}	
		
		private boolean isBrick(int rowIndex, int columnIndex) {
			if(this.brick != null){
				for(Atom a : this.brick.getAtoms()){
					if(a != null && this.brick.getPositionX()+a.getOffsetX() == columnIndex && this.brick.getPositionY()+a.getOffsetY() == rowIndex){
						return true;
					}
				}
			}
			return false;
		}
		
		public WallTableModel(TetrisBackgroud wall){
			super();
			this.wall = wall;
		}
		
		public void setBrick(Brick brick){
			this.brick = brick;
		}
	}
}
