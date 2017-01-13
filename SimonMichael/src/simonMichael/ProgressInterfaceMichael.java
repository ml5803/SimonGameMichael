package simonMichael;

import gui.components.Visible;

public interface ProgressInterfaceMichael extends Visible{

	void setRound(int roundNumber);

	void setSequenceLength(int size);

	/**
	 * changes display for when game has ended
	 */
	void gameOver();

}