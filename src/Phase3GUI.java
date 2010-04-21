import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class Phase3GUI extends JFrame implements ActionListener {
	public Phase3GUI() {
		setTitle("Phase 3 Demo");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				deleteDatabasePrompt();
				dispose();
				System.exit(0);
			}
		});
		setupDatabasePrompt();
		setup();
	}
	

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == repeatButton) {
			newScenarioPrompt();
		}
		
	}
	
	public void newScenarioPrompt() {		
		JLabel idLabel = new JLabel("Select Quasi-Identifiers:");
		JCheckBox[] idCheckBoxes = new JCheckBox[QuasiId.values().length];
		for (QuasiId id : QuasiId.values()) {
			idCheckBoxes[id.getPosition()] = new JCheckBox(id.toString());
		}
		// need k anon input
		JLabel setKLabel = new JLabel("Set K Anonymity:");
		JTextField kInputField = new JTextField();
		// need suppression input
		JLabel setMaxSupLabel = new JLabel("Set Maximum Suppression:");
		JTextField maxSupInputField = new JTextField();
		
		Object[] inputFields = { idLabel, idCheckBoxes,
								 setKLabel, kInputField,
								 setMaxSupLabel, maxSupInputField};
		
		int choice = JOptionPane.showOptionDialog(this, inputFields, 
												  "Start New Scenario", 
												  JOptionPane.OK_CANCEL_OPTION,
												  JOptionPane.PLAIN_MESSAGE, 
												  null, null, null);
		switch(choice) {
			case JOptionPane.OK_OPTION:
				
				int numIds = 0;
				for (JCheckBox box : idCheckBoxes) {
					if (box.isSelected()) {
						numIds++;
					}
				}
				
				QuasiId[] enabledIds = new QuasiId[numIds];
				int nextIndex = 0;
				
				for (QuasiId id : QuasiId.values()) {
					if (idCheckBoxes[id.getPosition()].isSelected()) {
						enabledIds[nextIndex] = id;
						nextIndex++;
					}
				}
				
			try {
				int kanon = Integer.parseInt(kInputField.getText());
				int maxSup = Integer.parseInt(maxSupInputField.getText());
				launch(kanon, maxSup, enabledIds);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Number input not valid.");
			}
				break;
			case JOptionPane.CANCEL_OPTION:
				// TODO: do nothing, maybe exit?
				break;
			default:
				break;
		}
	}
	
	private void launch(int kanon, int maxSup, QuasiId ... ids) {
		// get start time...
		try {
			long startTime = System.currentTimeMillis();
			
			String[][] data = Samarati.kanon(kanon, maxSup, ids);
			
			// get end time...
			long endTime = System.currentTimeMillis();
			double totalTime = (endTime-startTime)/1000.0;
			this.timeLabel.setText("ELAPSED TIME: " + totalTime);
			
			// output the data to the table
			setTableData(data, ids);
		} catch (Exception e) {
			e.printStackTrace();  // TODO: remove this before submitting
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private void setTableData(String[][] data, QuasiId ... list) {		
		String quasiIds = "";
        for ( QuasiId id : list )
        {
            quasiIds += "," + id.toString();
        } 
        quasiIds = quasiIds.substring( 1 );
		String[] columnNames = quasiIds.split(",");

		dataTable = new JTable(data, columnNames);
		dataTable.setAutoCreateRowSorter(true);
		dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane = new JScrollPane(dataTable);
		this.add(scrollPane);
	}

	public static void main(String[] args) {
		
	    try {
		    // Set System L&F
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Exception e) {

	    }
	    
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						createGUI();
					}
				} );
	}
		
	public static void createGUI() {
		Phase3GUI window = new Phase3GUI();
		Toolkit theKit = window.getToolkit();
		Dimension wndSize = theKit.getScreenSize();

		window.setBounds(wndSize.width/4, wndSize.height/4, 
						 wndSize.width/2, wndSize.height/2);
		window.setVisible(true);
		window.newScenarioPrompt();
	}
	
	private void deleteDatabasePrompt() {	
		JLabel idLabel = new JLabel("Delete Data in Database?");
		
		Object[] inputFields = { idLabel };
		
		int choice = JOptionPane.showOptionDialog(this, inputFields, 
												  "Delete Data?", 
												  JOptionPane.YES_NO_OPTION,
												  JOptionPane.PLAIN_MESSAGE, 
												  null, null, null);
		switch(choice) {
			case JOptionPane.YES_OPTION:
				dbManager.closeConnection(true);
				break;
			case JOptionPane.NO_OPTION:
				dbManager.closeConnection(false);
				break;
			default:
				break;
		}		
	}
	
	private void setupDatabasePrompt() {	
		JFileChooser fc = new JFileChooser();
		int choice = fc.showOpenDialog(this);
		File file = fc.getSelectedFile();
		switch(choice) {
			case JFileChooser.APPROVE_OPTION:
				dbManager = new DBManager(file.toString());
				break;
			case JFileChooser.CANCEL_OPTION:
				dbManager = new DBManager();
				break;
			default:
				break;
		}		
	}

	private void setup() {			
		JPanel buttonPanel = new JPanel(new FlowLayout());
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		repeatButton = new JButton("Repeat");
		buttonPanel.add(repeatButton);
		repeatButton.setPreferredSize(new Dimension(100, 35));
		repeatButton.addActionListener(this);
		
		timeLabel = new JLabel("ELAPSED TIME: ");
		timeLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.getContentPane().add(this.timeLabel, BorderLayout.NORTH);
		
		pack();
		
		dbManager = new DBManager();
	}

	private JTable dataTable;
	private JScrollPane scrollPane;
	private DBManager dbManager;
	private JLabel timeLabel;
	private JButton repeatButton;
}