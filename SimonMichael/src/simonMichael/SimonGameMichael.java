package simonMichael;

import gui.GUIApplication;

public class SimonGameMichael extends GUIApplication {

	public SimonGameMichael(int width, int height) {
		super(width, height);
	}

	@Override
	public void initScreen() {
		SimonScreenMichael sgm = new SimonScreenMichael(getWidth(),getHeight());
		setScreen(sgm);
	}
	
	public static void main(){
		SimonGameMichael sgm= new SimonGameMichael(800,600);
		Thread game = new Thread(sgm);
		game.start();
	}
}
