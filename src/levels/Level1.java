package levels;

import game.entities.BlinkingButton;
import game.entities.Button;
import game.entities.ClosingDoor;
import game.entities.Door;
import game.entities.PressurePlate;
import game.entities.VictoryPlate;

public class Level1 {
	
	private boolean init2Called = false;
	private Level level;
	
	private PressurePlate plate1;
	private PressurePlate plate2;
	private PressurePlate plate3;
	private PressurePlate plate4;
	private PressurePlate plate5;
	private PressurePlate plate6;
	private PressurePlate plate7;
	private PressurePlate plate8;
	private PressurePlate plate9;
	private PressurePlate plate10;
	private PressurePlate plate11;
	private PressurePlate plate12;
	private PressurePlate plate13;
	private PressurePlate plate14;
	private PressurePlate plate15;
	private PressurePlate plate16;
	private PressurePlate plate17;
	private PressurePlate plate18;
	private PressurePlate plate19;
	private PressurePlate plate20;
	private PressurePlate plate21;
	private PressurePlate plate22;
	private VictoryPlate vp1;
	private VictoryPlate vp2;
	
	private Door door1;
	private Door door2;
	private Door door3;
	private Door door4;
	private ClosingDoor door5;
	private ClosingDoor door6;
	private Door door7;
	private Door door8;
	private ClosingDoor door9;
	private ClosingDoor door10;
	private Door door11;
	private Door door12;
	private ClosingDoor door13;
	private ClosingDoor door14;
	private ClosingDoor door15;
	private ClosingDoor door16;
	private ClosingDoor door17;
	private ClosingDoor door18;
	private ClosingDoor door19;
	private ClosingDoor door20;
	private ClosingDoor door21;
	private ClosingDoor door22;
	private ClosingDoor door23;
	private Door door24;
	private Door door25;
	private Door door26;
	private Door door27;
	private ClosingDoor door28;
	private ClosingDoor door29;
	
	private Button button1;
	private BlinkingButton button2;
	private BlinkingButton button3;
	private BlinkingButton button4;
	private Button button5;
	private Button button6;
	private BlinkingButton button7;
	private BlinkingButton button8;
	
	public final String LEVEL_PATH = "/levels/level_1.png";

	public Level1() {
		
	}
	
	public void init(Level level) {
		this.level = level;
		
		plate1 = new PressurePlate(level, 45 << 3, 10 << 3, "plate1");
		level.getEntities().add(plate1);
		
		plate2 = new PressurePlate(level, 50 << 3, 10 << 3, "plate2");
		level.getEntities().add(plate2);
		
		door1 = new Door(level, 47 << 3, 14 << 3, "door1", (byte) 0x00);
		door1.addActivater(level.getEntityByName("plate1"));
		door1.addActivater(level.getEntityByName("plate2"));
		
		door2 = new Door(level, 48 << 3, 14 << 3, "door2", (byte) 0x01);
		door2.addActivater(level.getEntityByName("plate1"));
		door2.addActivater(level.getEntityByName("plate2"));
		
		door3 = new Door(level, 47 << 3, 18 << 3, "door3", (byte) 0x00);
		door3.addActivater(level.getEntityByName("plate1"));
		door3.addActivater(level.getEntityByName("plate2"));
		
		door4 = new Door(level, 48 << 3, 18 << 3, "door4", (byte) 0x01);
		door4.addActivater(level.getEntityByName("plate1"));
		door4.addActivater(level.getEntityByName("plate2"));
		
		button1 = new Button(level, 39 << 3, 40 << 3, "button1", 38, 40, 3, 36, 40, 1); // To activate this button, player needs to be facing east - 3 - see mob class movingDir
		
		vp1 = new VictoryPlate(level, 7 << 3, 56 << 3, "vp1");
		vp2 = new VictoryPlate(level, 8 << 3, 56 << 3, "vp2");
		
		level.getEntities().add(vp1);
		level.getEntities().add(vp2);
		level.getEntities().add(door1);
		level.getEntities().add(door2);
		level.getEntities().add(door3);
		level.getEntities().add(door4);
		level.getEntities().add(button1);
	}
	
	/*
	 * calls the init2 method when door4 is open.
	 */
	public void tick() {
		if(!init2Called) {
			if(door4.isOpen()) {
				init2(level);
			}
		}
	}
	
	
	/*
	 * certain timers and etc can be out of sync when 2 players join at different times, this init2 method will be called when
	 * the first doors at the beginning of the game open. this should put all timers into sync with both players.
	 * If door4 is opened then this method will be called in the tick function as door4 can only be opened with 2 players.
	 */
	public void init2(Level level){
		plate3 = new PressurePlate(level, 48 << 3, 40 << 3, "plate3");
		level.getEntities().add(plate3);
		
		door5 = new ClosingDoor(level, 60 << 3, 29 << 3, "door5", (byte) 0x00, 1);
		door5.addActivater(level.getEntityByName("plate3"));
		
		door6 = new ClosingDoor(level, 61 << 3, 29 << 3, "door6", (byte) 0x01, 1);
		door6.addActivater(level.getEntityByName("plate3"));
		
		button2 = new BlinkingButton(level, 59 << 3, 26 << 3, "button2", 500, 60, 26, 2, null);
		//comment out for single player
//		button3 = new BlinkingButton(level, 62 << 3, 26 << 3, "button3", 500, 61, 26, 3, null);
//		button2.addpair(button3);
//		button3.addpair(button2);
		
		door7 = new Door(level, 60 << 3, 60 << 3, "door7", (byte) 0x00);
		door7.addActivater(button2);
		//comment out
//		door7.addActivater(button3);
		
		door8 = new Door(level, 61 << 3, 60 << 3, "door8", (byte) 0x01);
		door8.addActivater(button2);
		//comment out
//		door8.addActivater(button3);
		
		button4 = new BlinkingButton(level, 62 << 3, 61 << 3, "button4", 500, 61, 61, 3, 5000);
		
		door9 = new ClosingDoor(level, 44 << 3, 61 << 3, "door9", (byte) 0x00, 1);
		door9.addActivater(button4);
		
		door10 = new ClosingDoor(level, 45 << 3, 61 << 3, "door10", (byte) 0x01, 1);
		door10.addActivater(button4);
		
		plate4 = new PressurePlate(level, 30 << 3, 17 << 3, "plate4");
		
		door11 = new Door(level, 33 << 3, 19 << 3, "door11", (byte) 0x00);
		door11.addActivater(plate4);
		
		door12 = new Door(level, 34 << 3, 19 << 3, "door12", (byte) 0x01);
		door12.addActivater(plate4);
		
		//Spirals plates and doors etc
		plate5 = new PressurePlate(level, 19 << 3, 4 << 3, "plate5");
		door13 = new ClosingDoor(level, 29 << 3, 43 << 3, "door13", (byte) 0x01, 1);
		door13.addActivater(plate5);
		plate6 = new PressurePlate(level, 28 << 3, 36 << 3, "plate6");
		door14 = new ClosingDoor(level, 27 << 3, 5 << 3, "door14", (byte) 0x01, 1);
		door14.addActivater(plate6);
		plate7 = new PressurePlate(level, 26 << 3, 12 << 3, "plate7");
		door15 = new ClosingDoor(level, 22 << 3, 37 << 3, "door15", (byte) 0x00, 1);
		door15.addActivater(plate7);
		plate8 = new PressurePlate(level, 23 << 3, 42 << 3, "plate8");
		door16 = new ClosingDoor(level, 20 << 3, 11 << 3, "door16", (byte) 0x00, 1);
		door16.addActivater(plate8);
		plate9 = new PressurePlate(level, 21 << 3, 6 << 3, "plate9");
		door17 = new ClosingDoor(level, 27 << 3, 41 << 3, "door17", (byte) 0x01, 1);
		door17.addActivater(plate9);
		plate10 = new PressurePlate(level, 26 << 3, 38 << 3, "plate10");
		door18 = new ClosingDoor(level, 25 << 3, 7 << 3, "door18", (byte) 0x01, 1);
		door18.addActivater(plate10);
		plate11 = new PressurePlate(level, 24 << 3, 10 << 3, "plate10");
		door19 = new ClosingDoor(level, 24 << 3, 39 << 3, "door19", (byte) 0x00, 1);
		door19.addActivater(plate11);
		button5 = new Button(level, 23 << 3,  8 << 3, "button5", 22, 8, 3, 25, 40, 1);
		//end spirals plates and doors etc
		
		plate12 = new PressurePlate(level, 1 << 3, 16 << 3, "plate12");
		plate13 = new PressurePlate(level, 1 << 3, 20 << 3, "plate13");
		plate14 = new PressurePlate(level, 1 << 3, 24 << 3, "plate14");
		plate15 = new PressurePlate(level, 1 << 3, 28 << 3, "plate15");
		
		door20 = new ClosingDoor(level, 2 << 3, 26 << 3, "door20", (byte) 0x00, 2);
		door20.addActivater(plate12);
		door20.addActivater(plate13);
		door20.addActivater(plate14);
		door20.addActivater(plate15);
		door21 = new ClosingDoor(level, 3 << 3, 26 << 3, "door21", (byte) 0x01, 2);
		door21.addActivater(plate12);
		door21.addActivater(plate13);
		door21.addActivater(plate14);
		door21.addActivater(plate15);
		door22 = new ClosingDoor(level, 2 << 3, 18 << 3, "door22", (byte) 0x00, 2);
		door22.addActivater(plate12);
		door22.addActivater(plate13);
		door22.addActivater(plate14);
		door22.addActivater(plate15);
		door23 = new ClosingDoor(level, 3 << 3, 18 << 3, "door23", (byte) 0x01, 2);
		door23.addActivater(plate12);
		door23.addActivater(plate13);
		door23.addActivater(plate14);
		door23.addActivater(plate15);
		
		plate16 = new PressurePlate(level, 7 << 3, 22 << 3, "plate16");
		plate17 = new PressurePlate(level, 8 << 3, 21 << 3, "plate17");
		plate18 = new PressurePlate(level, 9 << 3, 22 << 3, "plate18");
		plate19 = new PressurePlate(level, 8 << 3, 23 << 3, "plate19");
		
		door24 = new Door(level, 13 << 3, 21 << 3, "door24", (byte) 0x00);
		door24.addActivater(plate16);
		door24.addActivater(plate17);
		door24.addActivater(plate18);
		door24.addActivater(plate19);
		door25 = new Door(level, 14 << 3, 21 << 3, "door25", (byte) 0x01);
		door25.addActivater(plate16);
		door25.addActivater(plate17);
		door25.addActivater(plate18);
		door25.addActivater(plate19);
		
		button6 = new Button(level, 16 << 3, 22 << 3, "button6", 15, 22, 3, 8, 22, 1);
		
		button7 = new BlinkingButton(level, 1 << 3, 48 << 3, "button7", 500, 2, 48, 2, null);
		//comment out
//		button8 = new BlinkingButton(level, 13 << 3, 48 << 3, "button8", 500, 12, 48, 3, null);
//		button7.addpair(button8);
//		button8.addpair(button7);
		
		door26 = new Door(level, 6 << 3, 45 << 3, "door26", (byte) 0x01);
		door26.addActivater(button7);
		//comment out
//		door26.addActivater(button8);
		
		door27 = new Door(level, 8 << 3, 45 << 3, "door27", (byte) 0x00);
		door27.addActivater(button7);
		//comment out
//		door27.addActivater(button8);
		
		plate20 = new PressurePlate(level, 4 << 3, 43 << 3, "plate20");
		plate21 = new PressurePlate(level, 7 << 3, 43 << 3, "plate21");
		plate22 = new PressurePlate(level, 10 << 3, 43 << 3, "plate22");
		
		door28 = new ClosingDoor(level, 7 << 3, 58 << 3, "door28", (byte) 0x00, 1);
		door28.addActivater(plate20);
		door28.addActivater(plate21);
		door28.addActivater(plate22);
		door29 = new ClosingDoor(level, 8 << 3, 58 << 3, "door29", (byte) 0x01, 1);
		door29.addActivater(plate20);
		door29.addActivater(plate21);
		door29.addActivater(plate22);
		
		level.getEntities().add(door5);
		level.getEntities().add(door6);
		level.getEntities().add(button2);
		//comment out
//		level.getEntities().add(button3);
		
		level.getEntities().add(door7);
		level.getEntities().add(door8);
		level.getEntities().add(button4);
		level.getEntities().add(door9);
		level.getEntities().add(door10);
		level.getEntities().add(plate4);
		level.getEntities().add(door11);
		level.getEntities().add(door12);
		//spirals doors and plates etc
		level.getEntities().add(plate5);
		level.getEntities().add(door13);
		level.getEntities().add(plate6);
		level.getEntities().add(door14);
		level.getEntities().add(plate7);
		level.getEntities().add(door15);
		level.getEntities().add(plate8);
		level.getEntities().add(door16);
		level.getEntities().add(plate9);
		level.getEntities().add(door17);
		level.getEntities().add(plate10);
		level.getEntities().add(door18);
		level.getEntities().add(plate11);
		level.getEntities().add(door19);
		level.getEntities().add(button5);
		//end spirals doors and plates etc
		level.getEntities().add(plate12);
		level.getEntities().add(plate13);
		level.getEntities().add(plate14);
		level.getEntities().add(plate15);
		level.getEntities().add(plate16);
		level.getEntities().add(plate17);
		level.getEntities().add(plate18);
		level.getEntities().add(plate19);
		level.getEntities().add(door20);
		level.getEntities().add(door21);
		level.getEntities().add(door22);
		level.getEntities().add(door23);
		level.getEntities().add(door24);
		level.getEntities().add(door25);
		level.getEntities().add(button6);
		level.getEntities().add(button7);
		//comment out
//		level.getEntities().add(button8);
		
		level.getEntities().add(door26);
		level.getEntities().add(door27);
		level.getEntities().add(plate20);
		level.getEntities().add(plate21);
		level.getEntities().add(plate22);
		level.getEntities().add(door28);
		level.getEntities().add(door29);
		
		
		init2Called = true;
	}

}
