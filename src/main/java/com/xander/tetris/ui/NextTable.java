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
import com.xander.tetris.model.NextBackgroud;

public class NextTable extends JTable {
	private static final int scale = 15;
	private NextTableModel model;

	public void setWall(NextBackgroud wall) {
		this.model.setWall(wall);
	}

	public NextTable(NextBackgroud wall) {
		super();
		TableColumnModel columnModel = this.getColumnModel();
		columnModel.setColumnSelectionAllowed(false);
		this.getTableHeader().setVisible(false);
		this.setDefaultRenderer(Object.class, new NextCellRenderer());
		for(int i = 0; i < columnModel.getColumnCount(); i++ ){
			TableColumn column = columnModel.getColumn(i);
			column.setMaxWidth(scale);
			column.setMinWidth(scale);
			column.setPreferredWidth(scale);
			column.setResizable(false);
		}
		this.setFocusable(false);
		this.setRowHeight(scale);
		this.setCellSelectionEnabled(false);
		this.model = new NextTableModel(wall);
		this.setModel(model);
	}

	public void setBrick(Brick brick) {
		this.model.setBrick(brick);
		this.model.fireTableChanged(new TableModelEvent(model));
	}

	class NextCellRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			NextTableModel model = (NextTableModel)table.getModel();
			if(model.isAtom(row, column)){
				comp.setBackground(Color.black);
			}else{
				comp.setBackground(Color.white);
			}
			return comp;
		}
	}

	class NextTableModel extends AbstractTableModel {
		private Brick brick;
		private NextBackgroud wall;

		public void setBrick(Brick brick) {
			this.brick = brick;
		}

		public Brick getBrick() {
			return brick;
		}

		@Override
		public int getRowCount() {
			return this.wall.getHeight();
		}

		@Override
		public int getColumnCount() {
			return this.wall.getWidth();
		}

		public NextBackgroud getWall() {
			return wall;
		}

		public void setWall(NextBackgroud wall) {
			this.wall = wall;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return "";
		}

		public boolean isAtom(int rowIndex, int columnIndex) {
			if(this.brick != null){
				for(Atom a : this.brick.getAtoms()){
					if(a != null && 1 + a.getOffsetX() == columnIndex && 2 + a.getOffsetY() == rowIndex){
						return true;
					}
				}
			}
			return false;
		}

		public NextTableModel(NextBackgroud wall) {
			super();
			this.wall = wall;
		}
	}
}
