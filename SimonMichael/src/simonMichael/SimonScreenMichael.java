package simonMichael;

import java.awt.Color;
import java.util.ArrayList;

import gui.components.Action;
import gui.components.TextLabel;
import gui.components.Visible;
import gui.screens.ClickableScreen;

public class SimonScreenMichael extends ClickableScreen implements Runnable {

	private TextLabel text;
	private ButtonInterfaceMichael[] btnList;
	private ProgressInterfaceMichael progress;
	private ArrayList<MoveInterfaceMichael> moveList;

	private int roundNumber;
	private boolean acceptingInput;
	private int sequenceIndex;
	private int lastSelectedButton;

	public SimonScreenMichael(int width, int height) {
		super(width, height);
		Thread app = new Thread(this);
		app.start();
	}

	@Override
	public void run() {
		text.setText("");
	    nextRound();
	    progress.setRoundInt(roundNumber);
	    progress.setSequenceSize(moveList.size());
	    changeText("Simon's turn");
	    text.setText("");
	    playSequence();
	    changeText("Your turn");
	    sequenceIndex = 0;
	    acceptingInput=true;
	}

	private void playSequence() {
		ButtonInterfaceMichael b = null;
		for(int i = 0; i< moveList.size(); i++){
			if(b!=null){
				b.dim();
				b.getButton();
				b.highlight();
				
				try {
					Thread.sleep(1500/(200*roundNumber+1500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		b.dim();
	}

	private void changeText(String string) {
		text.setText(string);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void nextRound() {
		acceptingInput = false;
		roundNumber++;
		moveList.add(randomMove());
	}

	@Override
	public void initAllObjects(ArrayList<Visible> list) {
		addButtons();
		progress = getProgress();
		text = new TextLabel(130,230,300,40,"Let's play Simon!");
		moveList = new ArrayList<MoveInterfaceMichael>();
		//add 2 moves to start
		lastSelectedButton = -1;
		moveList.add(randomMove());
		moveList.add(randomMove());
		roundNumber = 0;
		viewObjects.add(progress);
		viewObjects.add(text);

	}

	private MoveInterfaceMichael randomMove() {
		ButtonInterfaceMichael b;
		int rand = -1;
		while (rand == lastSelectedButton || rand == -1){
			rand = (int) (Math.random() * btnList.length);
		}
		b = btnList[rand];

		return getMove(b);
	}


	/**
	Placeholder until partner finishes implementation of MoveInterface
	 */
	private MoveInterfaceMichael getMove(ButtonInterfaceMichael b) {
		return null;
	}

	/**
	Placeholder until partner finishes implementation of ProgressInterface
	 */
	private ProgressInterfaceMichael getProgress() {
		return null;
	}

	private void addButtons() {
		int numberOfButtons = 6;
		Color[] buttonColors = {Color.blue, Color.red, Color.yellow, Color.orange, Color.green, Color.pink};
		int[] xCoors = {350,365,435,450,435,365};
		int[] yCoors = {300,335,335,300,265,265};
		for(int i = 0; i < numberOfButtons; i++){
			final ButtonInterfaceMichael b = getButton();
			b.setColor(buttonColors[i]);
			b.setX(xCoors[i]);
			b.setY(yCoors[i]);
			b.setAction(new Action(){

				public void act(){
					if(acceptingInput){
						Thread blink = new Thread(new Runnable(){
							public void run(){
								b.highlight();
								try {
									Thread.sleep(800);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								b.dim();
							}

						});
						blink.start();

						if(b == progress.get(sequenceIndex).getButton()){
							sequenceIndex++;
						}else{
							progress.gameOver();
						}
					
						Thread nextRound = new Thread(SimonScreenMichael.this);
						nextRound.start();
						viewObjects.add(b);
					}
				}
			});
		}
	}

}
