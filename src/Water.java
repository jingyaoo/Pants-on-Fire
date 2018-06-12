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
 * This Water class represents a splash of Water that is sprayed by the Hero 
 * to extinguish Fireballs and Fires, as they attempt to save the Pants. 
 * @author Jingyao
 *
 */
public class Water {
	private Graphic graphic;
	private float speed;
	private float distanceTraveled;

	/**
	 * This constructor initializes a new instance of Water at the specified 
	 * location and facing a specific movement direction. This Water should 
	 * move with a speed of 0.7 pixels per millisecond.
	 * @param x
	 *         the x-coordinate of this new Water's position
	 * @param y
	 *         the y-coordinate of this new Water's position
	 * @param direction
	 *                 the angle (in radians) from 0 to 2pi that this new Water 
	 *                 should be both oriented and moving according to.
	 */
	public Water(float x, float y, float direction) {
		graphic = new Graphic("WATER"); //initialize the graphic with "WATER"
		speed = 0.7f; //initialize speed
		graphic.setPosition(x, y); //update the position of graphic
		graphic.setDirection(direction); //update the direction of the graphic
	}

	/**
	 * This method is called repeatedly by the Game to draw and move the current Water. 
	 * After this Water has moved a total of 200 pixels or further, 
	 * it should stop displaying itself and this method should return null 
	 * instead of a reference to the current instance of a Water object.
	 * @param time
	 *            is the amount of time in milliseconds that has elapsed since 
	 *            the last time this update was called.
	 * @return a reference to this Water object until this water has traveled 200 
	 *         or more pixels. It should then return null after traveling this far.
	 */
	public Water update(int time) {
		if (distanceTraveled > 200) {
			return null;
		}
		graphic.setX(graphic.getX() + ((speed * time) * graphic.getDirectionX()));
		graphic.setY(graphic.getY() + ((speed * time) * graphic.getDirectionY()));
		distanceTraveled = distanceTraveled + speed * time;
		graphic.draw();
		return this;
	}

	/**
	 * This is a simple accessor for this object's Graphic, 
	 * which may be used by other objects to check for collisions.
	 * @return a reference to this Water's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
}
