package pcd.ass_single.puzzle;

public class SelectionManager {

	private boolean selectionActive = false;
	private Tile selectedTile;

	public void selectTile(final Tile tile, final Listener listener) {
		
		if(selectionActive) {
			selectionActive = false;
			
			swap(selectedTile, tile);
			
			listener.onSwapPerformed();
		} else {
			selectionActive = true;
			selectedTile = tile;
		}
	}

	private void swap(final Tile t1, final Tile t2) {
		int pos = t1.getCurrentPosition();
		t1.setCurrentPosition(t2.getCurrentPosition());
		t2.setCurrentPosition(pos);
	}
	
	@FunctionalInterface
	interface Listener{
		void onSwapPerformed();
	}
}
