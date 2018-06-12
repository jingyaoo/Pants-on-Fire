//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            (Pants on Fire)
// Files:            (Level.java; Hero.java; Water.java; Pant.java; Fireball.java; Fire.java)
// Semester:         (course) Fall 2016
//
// Author:           (Jingyao Wei)
// Email:            (jwei44@wisc.edu)
// CS Login:         (jwei)
// Lecturer's Name:  (Williams, James S.)
// Lab Section:      (321)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     (name of your pair programming partner)
// Partner Email:    (email address of your programming partner)
// Partner CS Login: (your partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//    ___ Write-up states that Pair Programming is allowed for this assignment.
//    ___ We have both read the CS302 Pair Programming policy.
//    ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.
//
// Persons:          (identify each person and describe their help in detail)
// Online Sources:   (identify each URL and describe its assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * The Level class is responsible for managing all of the objects in your game.
 * The GameEngine creates a new Level object for each level, and then calls that
 * Level object's update() method repeatedly until it returns either "ADVANCE"
 * (to go to the next level), or "QUIT" (to end the entire game). <br/>
 * <br/>
 * This class should contain and use at least the following private fields:
 * <tt><ul>
 * <li>private Random randGen;</li>
 * <li>private Hero hero;</li>
 * <li>private Water[] water;</li>
 * <li>private ArrayList&lt;Pant&gt; pants;</li>
 * <li>private ArrayList&lt;Fireball&gt; fireballs;</li>
 * <li>private ArrayList&lt;Fire&gt; fires;</li>
 * </ul></tt>
 */
public class Level {
	private Random randGen;
	private Hero hero;
	private Water[] water;
	private ArrayList<Pant> pants;
	private ArrayList<Fireball> fireballs;
	private ArrayList<Fire> fires;

	/**
	 * This constructor initializes a new Level object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play. In the
	 * process of this initialization, all of the objects in the current level
	 * should be instantiated and initialized to their beginning states.
	 * 
	 * @param randGen
	 *            is the only Random number generator that should be used
	 *            throughout this level, by the Level itself and all of the
	 *            Objects within.
	 * @param level
	 *            is a string that either contains the word "RANDOM", or the
	 *            contents of a level file that should be loaded and played.
	 */
	public Level(Random randGen, String level) {
		this.randGen = randGen;
		water = new Water[8]; //initialize water array
		pants = new ArrayList<Pant>(); //initialize pants ArrayList
		fireballs = new ArrayList<Fireball>(); //initialize fireballs ArrayList
		fires = new ArrayList<Fire>(); //initialize fires ArrayList
		if (level.equals("RANDOM")) {
			createRandomLevel();
		} else {
			loadLevel(level);
		}
	}

	/**
	 * The GameEngine calls this method repeatedly to update all of the objects
	 * within your game, and to enforce all of the rules of your game.
	 * 
	 * @param time
	 *            is the time in milliseconds that have elapsed since the last
	 *            time this method was called. This can be used to control the
	 *            speed that objects are moving within your game.
	 * @return When this method returns "QUIT" the game will end after a short 3
	 *         second pause and a message indicating that the player has lost.
	 *         When this method returns "ADVANCE", a short pause and win message
	 *         will be followed by the creation of a new level which replaces
	 *         this one. When this method returns anything else (including
	 *         "CONTINUE"), the GameEngine will simply continue to call this
	 *         update() method as usual.
	 */
	public String update(int time) {
		hero.update(time, water); // hero update
		// water update
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null) {
				water[i] = water[i].update(time);
			}
		}
		//fire update
		for (int i = 0; i < pants.size(); i++) {
			Fire fire = pants.get(i).handleFireballCollisions(fireballs);
			if (fire != null) {
				fires.add(fire);
			}
			pants.get(i).update(time); // pants update
			if (pants.get(i).shouldRemove() == true) {
				pants.remove(i);
			}
		}
		// fireball update
		for (int i = 0; i < fires.size(); i++) {
			Fireball fireball = fires.get(i).update(time);
			if (fireball != null) {
				fireballs.add(fireball);
			}
			
			//when a fire hits water, remove the dead fire
			fires.get(i).handleWaterCollisions(water);
			if (fires.get(i).shouldRemove() == true) {
				fires.remove(i);
			}
		}
		for (int i = 0; i < fireballs.size(); i++) {
			//when a fireball hits water, remove the dead fireball
			fireballs.get(i).handleWaterCollisions(water);
			fireballs.get(i).update(time);
			if (fireballs.get(i).shouldRemove() == true) {
				fireballs.remove(i);
			}
		}
		//when fireball hits hero, QUIT
		if (hero.handleFireballCollisions(fireballs) == true) {
			return "QUIT";
		}
		
		//when fires is empty, return ADVANCE
		if (fires.size() == 0) {
			return "ADVANCE";
		}
		//when pants is empty, reuturn QUIT
		if (pants.size() == 0) {
			return "QUIT";
		}
		return "CONTINUE";
	}

	/**
	 * This method returns a string of text that will be displayed in the upper
	 * left hand corner of the game window. Ultimately this text should convey
	 * the number of unburned pants and fires remaining in the level. However,
	 * this may also be useful for temporarily displaying messages that help you
	 * to debug your game.
	 * 
	 * @return a string of text to be displayed in the upper-left hand corner of
	 *         the screen by the GameEngine.
	 */
	public String getHUDMessage() {
		return "Pants Left: " + pants.size() + "\nFires Left: " + fires.size();
	}

	/**
	 * This method creates a random level consisting of a single Hero centered
	 * in the middle of the screen, along with 6 randomly positioned Fires, and
	 * 20 randomly positioned Pants.
	 */
	public void createRandomLevel() {
		//initialize hero
		hero = new Hero(GameEngine.getHeight() / 2, GameEngine.getWidth() / 2, randGen.nextInt(3) + 1);
		for (int i = 0; i < 20; i++) {
			//initialize pants
			pants.add(new Pant(randGen.nextFloat() * GameEngine.getWidth(),
					randGen.nextFloat() * GameEngine.getHeight(), randGen));
		}
		for (int i = 0; i < 6; i++) {
			//initialize fires
			fires.add(new Fire(randGen.nextFloat() * GameEngine.getWidth(),
					randGen.nextFloat() * GameEngine.getHeight(), randGen));
		}
	}

	/**
	 * This method initializes the current game according to the Object location
	 * descriptions within the level parameter.
	 * 
	 * @param level
	 *            is a string containing the contents of a custom level file
	 *            that is read in by the GameEngine. The contents of this file
	 *            are then passed to Level through its Constructor, and then
	 *            passed from there to here when a custom level is loaded. You
	 *            can see the text within these level files by dragging them
	 *            onto the code editing view in Eclipse, or by printing out the
	 *            contents of this level parameter. Try looking through a few of
	 *            the provided level files to see how they are formatted. The
	 *            first line is always the "ControlType: #" where # is either 1,
	 *            2, or 3. Subsequent lines describe an object TYPE, along with
	 *            an X and Y position, formatted as: "TYPE @ X, Y". This method
	 *            should instantiate and initialize a new object of the correct
	 *            type and at the correct position for each such line in the
	 *            level String.
	 */
	public void loadLevel(String level) {
		Scanner input = new Scanner(level);
		input.next(); // scan "ControlType".
		int controlType = input.nextInt();
		input.nextLine();
		while (input.hasNext()) {
			String nameOfObject = input.next();
			input.next(); //scan "@".
			if (nameOfObject.equals("FIRE")) {
				String position = input.next();
				float xPosition = Float.parseFloat(position.substring(0, position.length() - 1));
				float yPosition = input.nextFloat();
				fires.add(new Fire(xPosition, yPosition, randGen));
			} else if (nameOfObject.equals("PANT")) {
				String position = input.next();
				float xPosition = Float.parseFloat(position.substring(0, position.length() - 1));
				float yPosition = input.nextFloat();
				pants.add(new Pant(xPosition, yPosition, randGen));
			} else if (nameOfObject.equals("HERO")) {
				String position = input.next();
				float xPosition = Float.parseFloat(position.substring(0, position.length() - 1));
				float yPosition = input.nextFloat();
				hero = new Hero(xPosition, yPosition, controlType);
			}
			input.nextLine();
		}
		input.close(); //close the scanner
	}

	/**
	 * This method creates and runs a new GameEngine with its first Level. Any
	 * command line arguments passed into this program are treated as a list of
	 * custom level filenames that should be played in a particular order.
	 * 
	 * @param args
	 *            is the sequence of custom level files to play through.
	 */
	public static void main(String[] args) {
		GameEngine.start(null, args);
	}
}
