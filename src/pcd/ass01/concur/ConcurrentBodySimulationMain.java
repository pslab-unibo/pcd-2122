package pcd.ass01.concur;

/**
 * Bodies simulation - legacy code: sequential, unstructured
 * 
 * @author aricci
 */
public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
                
    	SimulationModel model = new SimulationModel();
		Controller controller = new Controller(model);
    	SimulationView view = new SimulationView(620, 620, controller);
        controller.setView(view);
    	
    }
}
