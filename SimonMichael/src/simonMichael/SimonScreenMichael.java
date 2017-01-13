package simonMichael;

import java.awt.Color;
import java.util.ArrayList;

import gui.components.Action;
import gui.components.TextLabel;
import gui.components.Visible;
import gui.screens.ClickableScreen;
import simonMichael.ButtonInterfaceMichael;
import simonMichael.MoveInterfaceMichael;
import simonMichael.ProgressInterfaceMichael;
import simonMichael.SimonScreenMichael;

public class SimonScreenMichael extends ClickableScreen implements Runnable{

	private TextLabel label;
	private ButtonInterfaceMichael[] buttons;
	private ProgressInterfaceMichael progress;
	private ArrayList<MoveInterfaceMichael> moveList; 
	private int roundNumber;
	private boolean acceptingInput;
	private int sequenceIndex;
	private int lastButtonSelect;

	public SimonScreenMichael(int width, int height) {
		super(width, height);
		Thread screen = new Thread(this);
		screen.start();
		roundNumber = 0;
	}

	@Override
	public void initAllObjects(ArrayList<Visible> viewObjects) {
		Color[] colors = {Color.red, Color.blue, Color.orange, Color.green, Color.yellow, new Color(180,90,190)};
		String[] names = {"RED", "BLUE", "ORANGE", "GREEN", "YELLOW", "PURPLE"};
		int[] xCoors = {325,475,500,475,325,300};
		int[] yCoors = {225,225,300,375,375,300};
		int buttonCount = 6;
		buttons = new ButtonInterfaceMichael[buttonCount];
		
		for(int i = 0; i < buttonCount; i++ ){
			buttons[i] = getAButton();
			buttons[i].setName(names[i]);
			buttons[i].setColor(colors[i]);
			buttons[i].setX(xCoors[i]);
			buttons[i].setY(yCoors[i]);
			final ButtonInterfaceMichael b = buttons[i];
			b.dim();
			buttons[i].setAction(new Action() {
				public void act() {
						Thread blink = new Thread(new Runnable() {
							public void run() {
								b.highlight();
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								b.dim();
							}
						});
						blink.start();
						
						if(acceptingInput && moveList.get(sequenceIndex).getButton() == b){
							//if correct, next round
							sequenceIndex++;
						}else if(acceptingInput){
							gameOver();
							return;
						}
						if(sequenceIndex == moveList.size()){
							Thread next = new Thread(SimonScreenMichael.this);
							next.start();
						}
					}

			});
			viewObjects.add(buttons[i]);
		}
		progress = getProgress();
		viewObjects.add(progress);
		label = new TextLabel(130,150,300,40,"Lessa go!");
		viewObjects.add(label);
		moveList = new ArrayList<MoveInterfaceMichael>();
		lastButtonSelect = -1;
		moveList.add(randomMove());
		moveList.add(randomMove());
	}

	public void gameOver() {
		progress.gameOver();
	}

	public void nextRound() {
		acceptingInput = false;
		roundNumber ++;
		progress.setRound(roundNumber);
		moveList.add(randomMove());
		progress.setSequenceLength(moveList.size());
		changeText("It's my turn. Watch carefully >:)!");
		label.setText("");
		showSequence();
		changeText("Press them buttons!");
		label.setText("");
		acceptingInput = true;
		sequenceIndex = 0;
	}
	
	private void showSequence() {
		ButtonInterfaceMichael b = null;
		for(MoveInterfaceMichael m: moveList){
			if(b!=null){
				b.dim();
			}
			b = m.getButton();
			b.highlight();
			try {
				Thread.sleep((long)(2000*(2.0/(roundNumber+2))));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		b.dim();
	}

	private MoveInterfaceMichael randomMove() {
		int rand = (int) (Math.random()*buttons.length);
		while(rand == lastButtonSelect){
			rand = (int) (Math.random()*buttons.length);
		}
		lastButtonSelect = rand;
		return new Move(buttons[rand]);
	}

	private ProgressInterfaceMichael getProgress() {
		return new Progress();
	}

	private ButtonInterfaceMichael getAButton() {
		return new Button();
	}

	private void changeText(String string) {
		try{
			label.setText(string);
			Thread.sleep(1000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run() {
		changeText("");
		nextRound();
	}
}