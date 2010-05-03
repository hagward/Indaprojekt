/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-03
 */
public interface Movable {
	void move(int delta);

	float getXSpeed();

	float getYSpeed();

	void setXSpeed(float newXSpeed);

	void setYSpeed(float newYSpeed);

	void incrementXSpeed(float increment);

	void incrementYSpeed(float increment);
}
