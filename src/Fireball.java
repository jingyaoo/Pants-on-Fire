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

/**
 * This class represents a Fireball that is ejected from a burning fire. 
 * When a Fireball hits the Hero, they lose the game. 
 * When a Fireball hits a Pant, those Pants are replaced by a new Fire. 
 * @author Jingyao
 *
 */
public class Fireball {
	private Graphic graphic;
	private float speed;
	private boolean isAlive;

	/**
	 * This constructor initializes a new instance of Fireball at the specified 
	 * location and facing a specific movement direction. 
	 * This Fireball should move with a speed of 0.2 pixels per millisecond.
	 * @param x
	 *         the x-coordinate of this new Fireball's position
	 * @param y
	 *         the y-coordinate of this new Fireball's position
	 * @param directionAngle
	 *                      the angle (in radians) from 0 to 2pi 
	 *                      that this new Fireball should be both oriented 
	 *                      and moving according to.
	 */
	public Fireball(float x, float y, float directionAngle) {
		graphic = new Graphic("FIREBALL"); //initialize the graphic with "FIREBALL"
		speed = 0.2f; //initialize the speed
		graphic.setPosition(x, y); //update the position of the graphic
		graphic.setDirection(directionAngle); // update the direction of the graphic
		isAlive = true;
	}

	/**
	 * This method is called repeatedly by the Level to draw and move 
	 * the current Fireball. When a Fireball moves more than 100 pixels 
	 * beyond any edge of the screen, it should be destroyed and its 
	 * shouldRemove() method should begin to return true instead of false.
	 * @param time
	 *            is the amount of time in milliseconds that has elapsed since 
	 *            the last time this update was called.
	 */
	public void update(int time) {
		if (isAlive == true) {
			graphic.draw();
			graphic.setX(graphic.getX() + ((speed * time) * graphic.getDirectionX()));
			graphic.setY(graphic.getY() + ((speed * time) * graphic.getDirectionY()));
			float distanceTraveled = 0;
			distanceTraveled = distanceTraveled + speed * time;
		}
		if (graphic.getX() - GameEngine.getWidth() > 100 || graphic.getY() - GameEngine.getHeight() > 100) {
			isAlive = false;
		}
	}

	/**
	 * This is a simple accessor for this object's Graphic, 
	 * which may be used by other objects to check for collisions.
	 * @return a reference to this Fireball's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}

	/**
	 * This method detects and handles collisions between any active (!= null) 
	 * Water objects, and the current Fireball. When a collision is found, 
	 * the colliding water should be removed (array reference set to null), 
	 * and this Fireball should also be removed from the game (its shouldRemove() 
	 * should begin to return true when called). When this Fireball's shouldRemove 
	 * method is already returning true, this method should not do anything.
	 * @param water 
	 *             is the Array of Water objects that have been launched 
	 *             by the Hero (ignore any null references within this array).
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null) {
				if (this.graphic.isCollidingWith(water[i].getGraphic())) {
					isAlive = false;
					water[i] = null;
				}
			}
		}
	}

	/**
	 * This helper method allows other classes (like Pant) to destroy a Fireball 
	 * upon collision. This method should ensure that the shouldRemove() methods 
	 * only returns true after this method (destroy) has been called.
	 */
	public void destroy() {
		isAlive = false;
	}

	/**
	 * This method communicates to the Level whether this Fireball is still in 
	 * use versus ready to be removed from the Levels's ArrayList of Fireballs.
	 * @return true when this Fireball has either gone off the screen or collided
	 *          with a Water or Pant object, and false otherwise.
	 */
	public boolean shouldRemove() {
		if (isAlive == false) {
			return true;
		}
		return false;
	}
}
