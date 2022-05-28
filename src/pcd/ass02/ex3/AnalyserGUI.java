package pcd.ass02.ex3;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AnalyserGUI extends JFrame implements ActionListener {

	private JButton start;
	private JButton stop;
	private JButton chooseDir;
	private JTextField selectedDir;
	private String selectedDirPath;
	private JTextArea stats;
	
	private Controller controller;
	
	public AnalyserGUI(Controller contr){
		setTitle("Analyser GUI");
		setSize(300,240);		
		controller = contr;
		stats = new JTextArea(8, 20);
		stats.setEditable(false);		
		stats.setText("");
		selectedDir = new JTextField();
		selectedDir.setEditable(false);				
		chooseDir = new JButton("choose dir");
		start = new JButton("start");
		start.setEnabled(false);
		stop  = new JButton("stop");
		stop.setEnabled(false);
		
		Container cp = getContentPane();
		JPanel panel = new JPanel();
		
		Box p0 = new Box(BoxLayout.X_AXIS);
		p0.add(chooseDir);
		p0.add(start);
		p0.add(stop);
		Box p1a = new Box(BoxLayout.X_AXIS);
		p1a.add(selectedDir);
		Box p1 = new Box(BoxLayout.X_AXIS);
		p1.add(stats);
		Box p2 = new Box(BoxLayout.Y_AXIS);
		p2.add(p0);
		p2.add(Box.createVerticalStrut(10));
		p2.add(p1a);
		p2.add(Box.createVerticalStrut(10));
		p2.add(p1);
		panel.add(p2);
		cp.add(panel);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});

		chooseDir.addActionListener(this);
		start.addActionListener(this);
		stop.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ev){
		Object src = ev.getSource();
		if (src == chooseDir) {
			JFileChooser fileChooser = new JFileChooser();
			
			fileChooser.setCurrentDirectory(new java.io.File("."));
			fileChooser.setDialogTitle("Select the Project Dir");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setAcceptAllFileFilterUsed(false);

		    int code = fileChooser.showOpenDialog(this);
			if (code == JFileChooser.APPROVE_OPTION) {
				File f = fileChooser.getSelectedFile();
				selectedDirPath = f.getAbsolutePath();
				selectedDir.setText("..." + selectedDirPath.substring(selectedDirPath.length() - 20, selectedDirPath.length()));
				start.setEnabled(true);
			}
		} else if (src == start){	
			controller.notifyStarted(selectedDirPath);
		} else if (src == stop){
			controller.notifyStopped();
			start.setEnabled(true);
			stop.setEnabled(false);
		}
	}
	
	public void updateStats(Statistics.StatSnapshot stat) {
		SwingUtilities.invokeLater(()-> {
			String text = 
					"Statistics \n" +
				    "---------------------\n" +
					"Num packages: " + stat.getNumPackages() + "\n" +
					"Num classes: " + stat.getNumClasses() + "\n" +
					"Num interfaces: " + stat.getNumInterfaces() + "\n" +
					"Num methods: " + stat.getNumMethods() + "\n" +
					"Num fields: " + stat.getNumFields() + "\n" +
					"---------------------\n";
							
			stats.setText(text);
		});
	}

	public void display() {
        javax.swing.SwingUtilities.invokeLater(() -> {
        	this.setVisible(true);
        });
    }
	
}
