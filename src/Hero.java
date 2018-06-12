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

/**
 * This class represents the player's character which is a fire fighter who is 
 * able to spray water that extinguishes Fires and Fireballs. 
 * They must save as many Pants from burning as possible, 
 * and without colliding into any Fireballs in the process. 
 * @author Jingyao
 *
 */
public class Hero {
	private Graphic graphic;
	private float speed;
	private int controlType;
	
	/**
	 * This constructor initializes a new instance of Hero at the appropriate 
	 * location and using the appropriate controlType. 
	 * This Hero should move with a speed of 0.12 pixels per millisecond.
	 * @param x
	 *         the x-coordinate of this new Hero's position.
	 * @param y
	 *         the y-coordinate of this new Hero's position.
	 * @param controyType
	 *                   controlType - specifies which control scheme should be 
	 *                   used by the player to move this hero around: 1, 2, or 3.
	 */
	public Hero(float x, float y, int controyType) {
		speed = 0.12f; //initialize speed
		graphic = new Graphic("HERO"); //initialize graphic with "HERO"
		graphic.setPosition(x, y); //update the position of a Graphic
		this.controlType = controyType;
	}

	/**
	 * This method is called repeated by the Level to draw and move 
	 * (based on the current controlType) the Hero, as well as to spray 
	 * new Water in the direction that this Hero is currently facing.
	 * @param time
	 *            is the amount of time in milliseconds that has elapsed since 
	 *            the last time this update was called.
	 * @param waterArray
	 *                  the array of Water that the Hero has sprayed in the past, 
	 *                  and if there is an empty (null) element in this array, 
	 *                  they can can add a new Water object to this array by 
	 *                  pressing the appropriate controls.
	 */
	public void update(int time, Water[] waterArray) {
		graphic.draw();
		float displacementOfX = speed * time;
		float displacementOfY = speed * time;
		float pi = (float) Math.PI;
		if (controlType == 1) {
			if (GameEngine.isKeyHeld("A")) {
				graphic.setDirection(pi);
				graphic.setPosition(graphic.getX() - displacementOfX, graphic.getY());
			} else if (GameEngine.isKeyHeld("W")) {
				graphic.setDirection(3 * pi / 2);
				graphic.setPosition(graphic.getX(), graphic.getY() - displacementOfY);
			} else if (GameEngine.isKeyHeld("S")) {
				graphic.setDirection(pi / 2);
				graphic.setPosition(graphic.getX(), graphic.getY() + displacementOfY);
			} else if (GameEngine.isKeyHeld("D")) {
				graphic.setDirection(0);
				graphic.setPosition(graphic.getX() + displacementOfX, graphic.getY());
			}
		} else if (controlType == 2) {
			if (GameEngine.isKeyHeld("A")) {
				graphic.setPosition(graphic.getX() - displacementOfX, graphic.getY());
			} else if (GameEngine.isKeyHeld("W")) {
				graphic.setPosition(graphic.getX(), graphic.getY() - displacementOfY);
			} else if (GameEngine.isKeyHeld("S")) {
				graphic.setPosition(graphic.getX(), graphic.getY() + displacementOfY);
			} else if (GameEngine.isKeyHeld("D")) {
				graphic.setPosition(graphic.getX() + displacementOfX, graphic.getY());
			}
			graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
		} else if (controlType == 3) {
			graphic.setDirection(GameEngine.getMouseX(), GameEngine.getMouseY());
			float xDisplacement = speed * time * graphic.getDirectionX();
			float yDisplacement = speed * time * graphic.getDirectionY();
			float distance = (float) Math.hypot((GameEngine.getMouseX() - graphic.getX()),
					(GameEngine.getMouseY() - graphic.getY()));
			if (distance >= 20) {
				graphic.setPosition(graphic.getX() + xDisplacement, graphic.getY() + yDisplacement);
			}
		}
		if (GameEngine.isKeyPressed("MOUSE") || GameEngine.isKeyPressed("SPACE")) {
			Water water = new Water(graphic.getX(), graphic.getY(), graphic.getDirection());
			for (int i = 0; i < waterArray.length; i++) {
				if (waterArray[i] == null) {
					waterArray[i] = water;
					break;
				}
			}
		}
		for (int i = 0; i < waterArray.length; i++) {
			if (waterArray[i] != null) {
				waterArray[i] = waterArray[i].update(time); //update water location
			}
		}
	}

	/**
	 * This is a simple accessor for this object's Graphic, which may be used 
	 * by other objects to check for collisions.
	 * @return a reference to this Hero's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}

	/**
	 * This method detects an handles collisions between any active Fireball objects, 
	 * and the current Hero. When a collision is found, this method returns true 
	 * to indicate that the player has lost the Game.
	 * @param fireballs
	 *                 the ArrayList of Fireballs that should be checked against 
	 *                 the current Hero's position for collisions.
	 * @return true when a Fireball collision is detected, otherwise false.
	 */
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for (int i = 0; i < fireballs.size(); i++) {
			if (this.graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				return true;
			}
		}
		return false;
	}
}
