package pcd.ass02.ex2;

public class AnalyserView {

	private AnalyserGUI gui;
	
	public AnalyserView(Controller contr){	
		gui = new AnalyserGUI(contr);
	}
	
	public void updateStatistics(Statistics.StatSnapshot stat) {
		gui.updateStats(stat);
	}
	
	public void resetState() {
		gui.resetState();
	}

	public void display() {
		gui.display();
    }
	
}
