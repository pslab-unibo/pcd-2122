package pcd.ass_single.puzzle;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class TileButton extends JButton{

	public TileButton(final Tile tile) {
		super(new ImageIcon(tile.getImage()));
		
		addMouseListener(new MouseAdapter() {            
            @Override
            public void mouseClicked(MouseEvent e) {
            	setBorder(BorderFactory.createLineBorder(Color.red));
            }
        });
	}
}
