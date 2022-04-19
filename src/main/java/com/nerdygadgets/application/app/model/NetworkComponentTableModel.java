package com.nerdygadgets.application.app.model;

import javax.swing.table.AbstractTableModel;

public class NetworkComponentTableModel extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Name", "Price (€)", "Availability (%)"};
    private final Object[][] data;

    public NetworkComponentTableModel(Object[][] data) {
        if (data == null) {
            this.data = new Object[][]{{"-", "-", "-", "-"}};
        } else {
            this.data = data;
        }
    }

    @Override
    public int getRowCount() {
        return columnNames.length;
    }

    @Override
    public int getColumnCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /**
     * Sets the value at the given row and column.
     *
     * @param value the value.
     * @param row the row index.
     * @param col the column index.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
