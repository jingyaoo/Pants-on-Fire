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

/**
 * This class represents a pair of Pants that the Hero must protect from burning. 
 * Whenever a Pant collides with a Fireball, that Pant will be replaced by a Fire. 
 * @author Jingyao
 *
 */
public class Pant {
	private Graphic graphic;
	private Random randGen;
	private boolean isAlive;

	/**
	 * This constructor initializes a new instance of Pant at the appropriate location. 
	 * The Random number is only used to create a new Fire, after this pant is hit by a Fireball.
	 * @param x
	 *         the x-coordinate of this new Pant's position
	 * @param y
	 *         the y-coordinate of this new Pant's position
	 * @param randGen
	 *               a Random number generator to pass onto any Fire that is 
	 *               created as a result of this Pant being hit by a Fireball.
	 */
	public Pant(float x, float y, Random randGen) {
		graphic = new Graphic("PANT"); //initialize the graphic with "PANT"
		this.randGen = randGen;
		graphic.setPosition(x, y); //update the position of graphic
		isAlive = true;
	}

	/**
	 * This method is simply responsible for draing the current Pant to the screen.
	 * @param time
	 *            is the amount of time in milliseconds that has elapsed 
	 *            since the last time this update was called.
	 */
	public void update(int time) {
		if (isAlive == true) {
			graphic.draw();
		}
	}

	/**
	 * This is a simple accessor for this object's Graphic, 
	 * which may be used by other objects to check for collisions.
	 * @return a reference to this Pant's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}

	/**
	 * This method detects an handles collisions between any active Fireball, 
	 * and the current Pant. When a collision is found, the colliding Fireball 
	 * should be removed from the game (by calling its destroy() method), 
	 * and the current Pant should also be removed from the game (by ensuring 
	 * that its shouldRemove() method returns true). A new Fire should be created 
	 * in the position of the old Pant object and then returned.
	 * @param fireball
	 *                the ArrayList of Fireballs that should be checked against 
	 *                the current Pant object's Graphic for collisions.
	 * @return a reference to the newly created Fire when a collision is found, 
	 *         and null otherwise.
	 */
	public Fire handleFireballCollisions(ArrayList<Fireball> fireball) {
		for (int i = 0; i < fireball.size(); i++) {
			if (this.graphic.isCollidingWith(fireball.get(i).getGraphic())) {
				isAlive = false;
				fireball.get(i).destroy();
				Fire fire = new Fire(graphic.getX(), graphic.getY(), randGen);
				return fire;
			}
		}
		return null;
	}

	/**
	 * This method communicates to the Game whether this Pant is still in use 
	 * versus ready to be removed from the Game's ArrayList of Pants.
	 * @return true when this Pant has been hit by a Fireball, otherwise false.
	 */
	public boolean shouldRemove() {
		if (isAlive == false) {
			return true;
		}
		return false;
	}
}
