package pcd.ass02.ex1;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
                
    	SimulationModel model = new SimulationModel();
		Controller controller = new Controller(model);
    	SimulationView view = new SimulationView(620, 620, controller);
        controller.setView(view);
    	
    }
}
