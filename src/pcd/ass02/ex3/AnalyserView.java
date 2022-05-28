package pcd.ass02.ex3;

public class AnalyserView {

	private AnalyserGUI gui;
	
	public AnalyserView(Controller contr){	
		gui = new AnalyserGUI(contr);
	}
	
	public synchronized void updateStatistics(Statistics.StatSnapshot stat) {
		gui.updateStats(stat);
	}
	
	public synchronized void display() {
		gui.display();
    }
	
}
