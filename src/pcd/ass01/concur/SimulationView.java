package pcd.ass01.concur;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Simulation view
 *
 * @author aricci
 *
 */
public class SimulationView {
        
	private VisualiserFrame frame;
	
    public SimulationView(int w, int h,  Controller contr){
    	frame = new VisualiserFrame(w, h, contr);
    }
        
    public void display(SimulationModel.SimulationSnapshot snap){
 	   frame.display(snap); 
    }
    
    public static class VisualiserFrame extends JFrame implements ActionListener {

        private VisualiserPanel bodyPanel;
        private Controller controller;
        private JButton start, stop;
        
        public VisualiserFrame(int w, int h, Controller contr){
            setTitle("Bodies Simulation");
            setSize(w,h);
            setResizable(false);

            start = new JButton("start");
    		stop = new JButton("stop");
    		stop.setEnabled(false);

    		controller = contr;
   		
    		JPanel controlPanel = new JPanel();
    		controlPanel.add(start);
    		controlPanel.add(stop);

            bodyPanel = new VisualiserPanel(w,h);
    		bodyPanel.setSize(w,h);

    		JPanel cp = new JPanel();
    		LayoutManager layout = new BorderLayout();
    		cp.setLayout(layout);
    		cp.add(BorderLayout.NORTH,controlPanel);
    		cp.add(BorderLayout.CENTER,bodyPanel);
    		setContentPane(cp);		
    		            
            addWindowListener(new WindowAdapter(){
    			public void windowClosing(WindowEvent ev){
    				System.exit(-1);
    			}
    			public void windowClosed(WindowEvent ev){
    				System.exit(-1);
    			}
    		});

            this.setVisible(true);
    		start.addActionListener(this);
    		stop.addActionListener(this);	
    	}
    	
    	public void actionPerformed(ActionEvent ev){
    		Object src = ev.getSource();
    		if (src == start){	
    			controller.notifyStarted();
    			stop.setEnabled(true);
       			start.setEnabled(false);
    			bodyPanel.requestFocusInWindow();
       		} else if (src == stop){
    			controller.notifyStopped();
    			start.setEnabled(true);
    			stop.setEnabled(false);
    			bodyPanel.requestFocusInWindow();
    		}
    	}
        
        public void display(SimulationModel.SimulationSnapshot snap){
        	try {
	        	SwingUtilities.invokeLater(() -> {
	        		bodyPanel.display(snap);
	            	repaint();
	        	});
        	} catch (Exception ex) {}
        };
        
        public void updateScale(double k) {
        	bodyPanel.updateScale(k);
        }    	
    }

    public static class VisualiserPanel extends JPanel implements KeyListener {
        
    	SimulationModel.SimulationSnapshot snap;
    	private Boundary bounds;
    	
    	private double scale = 1;
    	
        private long dx;
        private long dy;
        
        public VisualiserPanel(int w, int h){
            setSize(w,h);
            dx = w/2 - 20;
            dy = h/2 - 20;
			this.addKeyListener(this);
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
			requestFocusInWindow(); 
        }

        public void paint(Graphics g){    		    		
    		if (snap != null) {
        		Graphics2D g2 = (Graphics2D) g;
        		
        		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        		          RenderingHints.VALUE_ANTIALIAS_ON);
        		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
        		          RenderingHints.VALUE_RENDER_QUALITY);
        		g2.clearRect(0,0,this.getWidth(),this.getHeight());

                Boundary bounds = snap.getBounds();

        		int x0 = getXcoord(bounds.getX0());
        		int y0 = getYcoord(bounds.getY0());
        		
        		int wd = getXcoord(bounds.getX1()) - x0;
        		int ht = y0 - getYcoord(bounds.getY1());
        		
    			g2.drawRect(x0, y0 - ht, wd, ht);
    			
	    		snap.getBodies().forEach( b -> {
	    			P2d p = b.getPos();
			        int radius = (int) (10*scale);
			        if (radius < 1) {
			        	radius = 1;
			        }
			        g2.drawOval(getXcoord(p.getX()),getYcoord(p.getY()), radius, radius); 
			    });		    
	    		String time = String.format("%.2f", snap.getVT());
	    		g2.drawString("Bodies: " + snap.getBodies().size() + " - vt: " + time + " (UP for zoom in, DOWN for zoom out)", 2, 20);
    		}
        }
        
        private int getXcoord(double x) {
        	return (int)(dx + x*dx*scale);
        }

        private int getYcoord(double y) {
        	return (int)(dy - y*dy*scale);
        }
        
        public void display(SimulationModel.SimulationSnapshot snap){
            this.snap = snap;
        }
        
        public void updateScale(double k) {
        	scale *= k;
        }

		@Override
		public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 38){  		/* KEY UP */
					scale *= 1.1;
				} else if (e.getKeyCode() == 40){  	/* KEY DOWN */
					scale *= 0.9;  
				} 
		}

		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
    }
}
