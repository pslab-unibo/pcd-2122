package pcd.ass_single.puzzle;

import java.awt.Image;

class Tile implements Comparable<Tile>{
	private Image image;
	private int originalPosition;
	private int currentPosition;

    public Tile(final Image image, final int originalPosition, final int currentPosition) {
        this.image = image;
        this.originalPosition = originalPosition;
        this.currentPosition = currentPosition;
    }
    
    public Image getImage() {
    	return image;
    }
    
    public boolean isInRightPlace() {
    	return currentPosition == originalPosition;
    }
    
    public int getCurrentPosition() {
    	return currentPosition;
    }
    
    public void setCurrentPosition(final int newPosition) {
    	currentPosition = newPosition;
    }

	@Override
	public int compareTo(Tile other) {
		return this.currentPosition < other.currentPosition ? -1 
				: (this.currentPosition == other.currentPosition ? 0 : 1);
	}
}
