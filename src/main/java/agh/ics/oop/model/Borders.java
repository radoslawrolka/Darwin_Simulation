package agh.ics.oop.model;

public interface Borders<P> {
    /**
     * Return a position after moving in a given direction.
     *
     * @param position
     *            The initial position.
     * @param unitVector
     *            The direction of the move.
     * @return The final position after moving in the given direction.
     */
    P getPosition(P position, P unitVector);
}
