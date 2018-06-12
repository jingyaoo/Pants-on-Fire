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

import java.util.Random;

/**
 * This class represents a fire that is burning, which ejects a Fireball in 
 * a random direction every 3-6 seconds. This fire can slowly be extinguished 
 * through repeated collisions with water. 
 * @author Jingyao
 *
 */
public class Fire {
	private Graphic graphic;
	private Random randGen;
	private int fireballCountdown;
	private int heat;

	/**
	 * This constructor initializes a new instance of Fire at the appropriate 
	 * location and with the appropriate amount of heat. The Random number 
	 * generator should be used both to determine how much time remains 
	 * before the next Fireball is propelled, and the random direction it is shot in.
	 * @param x 
	 *         the x-coordinate of this new Fire's position
	 * @param y
	 *         the y-coordinate of this new Fire's position
	 * @param randGen
	 *               a Random number generator to determine when and in which 
	 *               direction new Fireballs are created and launched.
	 */
	public Fire(float x, float y, Random randGen) {
		graphic = new Graphic("FIRE"); //initialize the graphic with "FIRE"
		graphic.setPosition(x, y); // update the position of the graphic
		this.randGen = randGen;
	
		//represents the number of milliseconds before the next new Fireball object 
		//is hurled from this Fire.
		//initialize between 3000 to 6000
		fireballCountdown = randGen.nextInt(3001) + 3000; 
		heat = 4; //initialize the heat
	}

	/**
	 * This method is called repeatedly by the Level to draw and occasionally 
	 * launch a new Fireball in a random direction.
	 * @param time
	 *            is the amount of time in milliseconds that has elapsed since the last time this update was called.
	 * @return null unless a new Fireball was just created and launched. 
	 *         In that case, a reference to that new Fireball is returned instead.
	 */
	public Fireball update(int time) {
		if (heat >= 1) {
			graphic.draw();
			fireballCountdown = fireballCountdown - time;
			if (fireballCountdown <= 0) {
				fireballCountdown = randGen.nextInt(3001) + 3000;
				Fireball fireball = new Fireball(graphic.getX(), graphic.getY(),
						randGen.nextFloat() * (float) (Math.PI * 2));
				return fireball;
			}
		}
		return null;
	}

	/**
	 * This is a simple accessor for this object's Graphic, 
	 * which may be used by other objects to check for collisions.
	 * @return a reference to this Fire's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}

	/**
	 * This method detects and handles collisions between any active (!= null) 
	 * Water objects, and the current Fire. When a collision is found, 
	 * the colliding water should be removed, and this Fire's heat should be 
	 * decremented by 1. If this Fire's heat dips below one, 
	 * then it should no longer be drawn to the screen, eject new Fireballs, 
	 * or collide with Water and its shouldRemove() method should start returning true.
	 * @param water
	 *             is the Array of water objects that have been launched 
	 *             by the Hero (ignore any null references within this array).
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null) {
				if (this.graphic.isCollidingWith(water[i].getGraphic())) {
					heat = heat - 1;
					water[i] = null;
				}
			}
		}
	}

	/**
	 * This method should return false until this Fire's heat drops down to 0 or less. 
	 * After that it should begin to return true instead.
	 * @return false when this Fire's heat is greater than zero, otherwise true.
	 */
	public boolean shouldRemove() {
		if (heat < 1) {
			return true;
		}
		return false;
	}
}
