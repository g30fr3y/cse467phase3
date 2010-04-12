import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class ResultsModel {
	
	private String[] columnNames;
	private Vector<String[]> dataRows;
	
	public ResultsModel() {
		columnNames = new String[0];
		dataRows = new Vector<String[]>();
	}
	
	public void setResultSet(ResultSet results) {
		if (results == null) {
			columnNames = new String[0];
			dataRows.clear();
			return;
		}
		
		try {
			ResultSetMetaData metadata = results.getMetaData();
			int columns = metadata.getColumnCount();
			columnNames = new String[columns];
			for (int i = 0; i < columns; i++) {
				columnNames[i] = metadata.getColumnLabel(i+1);
			}
			
			dataRows.clear();
			String[] rowData;
			while (results.next()) {
				rowData = new String[columns];
				for (int i = 0; i < columns; i++) {
					rowData[i] = results.getString(i+1);
				}
				dataRows.addElement(rowData);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return dataRows == null ? 0 : dataRows.size();
	}

	public String getValueAt(int row, int column) {
		return dataRows.elementAt(row)[column];
	}
	
	public String getColumnName(int column) {
		return columnNames[column] == null ? "No Name" : columnNames[column];
	}
	
	public String[] getData() {
		String output = "";
		for (int i = 0; i < dataRows.size(); i++) {
			for(int j = 0; j < dataRows.get(i).length; j++) {
				output += dataRows.get(i)[j] + ",";
			}
		}
		if (output.equals("")) {
			return null;
		} else {
			return output.split(",");
		}
	}
	
	public String[] getFormattedData() {
		String output = "";
		
		for(int i = 0; i < columnNames.length; i++) {
//			output += columnNames[i] + "\t";
			output += String.format("%-12s", columnNames[i]);
		}
		
		output += ",";
		
		for (int i = 0; i < dataRows.size(); i++) {
			for(int j = 0; j < dataRows.get(i).length; j++) {
//				output += dataRows.get(i)[j] + "\t";
				output += String.format("%-12s", dataRows.get(i)[j]);
			}
			output += ",";
		}
		return output.split(",");
	}

}
