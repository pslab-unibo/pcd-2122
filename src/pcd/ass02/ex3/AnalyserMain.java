package pcd.ass02.ex3;

public class AnalyserMain {
	public static void main(String[] args) {
		Statistics stats = new Statistics();
		Controller controller = new Controller(stats);
        AnalyserView view = new AnalyserView(controller);
        controller.setView(view);
        view.display();
	}
}
