package simonMichael;

import java.awt.Color;

import gui.components.Action;
import gui.components.Clickable;

public interface ButtonInterfaceMichael extends Clickable {

	void setColor(Color color);

	void setX(int xCoors);

	void setY(int yCoors);

	void setAction(Action action);
	
	void highlight();

	void dim();

	void getButton();
	
	

}
