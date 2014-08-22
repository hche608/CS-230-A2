package spaceinvaders.highscores;

import java.util.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;



public class HighScoreTableModel implements TableModel {

	private List<String> col1StringList = new ArrayList<>();
	private List<Integer> col2IntegerList = new ArrayList<>();
	private String col1Name, col2Name;

	private List<TableModelListener> listenerList = new ArrayList<>();

	public HighScoreTableModel(String col1Name, String col2Name) {
		this.col1Name = col1Name;
		this.col2Name = col2Name;
	}

	public int getRowCount() {
		return col1StringList.size();
	}

	public int getColumnCount() {
		return 2;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return col1Name;
		case 1:
			return col2Name;
		default:
			return null;
		}
	}

	public Class<?> getColumnClass(int columnIndex){
		switch (columnIndex){
			case 0:
				return String.class;
			case 1:
				return Integer.class;
			default:
				return null;			
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= getRowCount() || rowIndex < 0)
			return null;
		switch (columnIndex) {
		case 0:
			return col1StringList.get(rowIndex);
		case 1:
			return col2IntegerList.get(rowIndex);
		default:
			return null;
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void setValueAt(Object arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException("Not Supported.");
	}

	public void addTableModelListener(TableModelListener l) {
		listenerList.add(l);
	}

	public void removeTableModelListener(TableModelListener l) {
		listenerList.remove(l);
	}
	
	public void addRow(String name, Integer value){
		col1StringList.add(name);
		col2IntegerList.add(value);
		
		fireRowAddedEvent();
	}
	
	public void removeRow(){
		int lastRow = getRowCount() - 1;
		if(lastRow > 0){
			col1StringList.remove(lastRow);
			col2IntegerList.remove(lastRow);
		}
		
		fireRowRemovedEvent();
	}
	
	public void update(){
		fireContentsChangedEvent();
	}
	
	public void fireContentsChangedEvent(){
		TableModelEvent tme = new TableModelEvent(this);
		
		for (TableModelListener l : listenerList)
			l.tableChanged(tme);
	}
	
	private void fireRowAddedEvent(){
		TableModelEvent tme = new TableModelEvent(this,
				getRowCount()-1,
				getRowCount()-1,
				TableModelEvent.ALL_COLUMNS,
				TableModelEvent.INSERT);
		for (TableModelListener l : listenerList)
			l.tableChanged(tme);
	}
	
	private void fireRowRemovedEvent(){
		TableModelEvent tme = new TableModelEvent(this,
				getRowCount()-1,
				getRowCount()-1,
				TableModelEvent.ALL_COLUMNS,
				TableModelEvent.DELETE);
		for (TableModelListener l : listenerList)
			l.tableChanged(tme);
	}
	
	

}
