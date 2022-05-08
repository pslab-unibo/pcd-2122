package pcd.lab09.actors.basic;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import akka.actor.ActorRef;

public class ViewFrame extends JFrame {

	public ViewFrame(ActorRef actorView) {
		super(".:: Test Swing | Actors interaction ::.");
		setSize(400, 70);
		JButton button = new JButton("Press me");
		button.addActionListener((ActionEvent ev) -> {
			actorView.tell(new PressedMsg(), ActorRef.noSender());
		});
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(button);
		getContentPane().add(panel);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(-1);
			}
		});
	}
	
	public void display() {
		SwingUtilities.invokeLater(() -> {
			this.setVisible(true);
		});
	}
}