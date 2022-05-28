package pcd.ass02.ex2;

/**
 * Assignment #02 - Ex 2 (point B) 
 * 
 * @author aricci
 *
 */
public class AnalyserMain {
	public static void main(String[] args) {
		Statistics stats = new Statistics();
		Controller controller = new Controller(stats);
        AnalyserView view = new AnalyserView(controller);
        controller.setView(view);
        view.display();
	}

}
