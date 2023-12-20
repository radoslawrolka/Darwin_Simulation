package agh.ics.oop.model;

public interface MoveValidator<P> {

    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(P position);

    /**
     * Return a position after moving in a given direction.
     *
     * @param position
     *            The initial position.
     * @param UnitVector
     *            The direction of the move.
     * @return The final position after moving in the given direction.
     */
    P getPosition(P position, P UnitVector);
}
