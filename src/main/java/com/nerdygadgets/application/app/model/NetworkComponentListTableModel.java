package com.nerdygadgets.application.app.model;

import javax.swing.table.AbstractTableModel;

public class NetworkComponentListTableModel extends AbstractTableModel {

    private final String[] columnNames = { "ID", "Name", "Availability (%)", "Price (€)", "Score" };
    private final Object[][] data;

    public NetworkComponentListTableModel() {
        this.data = new Object[][] { { "", "", "Loading", "", "" } };
    }

    public NetworkComponentListTableModel(final Object[][] data) {
        this.data = data;
    }

    /**
     * Sets the value at the given row and column.
     *
     * @param row The index of the row.
     * @param column The index of the column.
     * @param value The value to set at the given location.
     */
    public void setValueAt(final int row, final int column, final Object value) {
        data[row][column] = value;
        fireTableCellUpdated(row, column);
    }

    @Override
    public String getColumnName(final int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(final int row, final int column) {
        return data[row][column];
    }
}
