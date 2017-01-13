package simonMichael;

import gui.GUIApplication;

public class SimonGameMichael extends GUIApplication {
	
	public static SimonGameMichael simonGame;// only ONE exists
	public static SimonScreenMichael screen;
	
	public SimonGameMichael(int width, int height) {
		super(width, height);
	}

	@Override
	public void initScreen() {
		screen = new SimonScreenMichael(getWidth(),getHeight());
		setScreen(screen);
	}
	
	public static void main(String[] stuff){
		simonGame = new SimonGameMichael(800,600);
		Thread game = new Thread(simonGame);
		game.start();
	}
}
