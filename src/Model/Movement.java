/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 12/2/19
 * Time: 11:36 AM
 *
 * Project: CSCI205FinalProject
 * Package: Network
 * Class: Movement
 *
 * Description:
 * A movement object to pass back and forth between a server and client,
 * holds an initial square and a final square.
 * ****************************************
 */
package Model;

import java.io.Serializable;

public class Movement implements Serializable {
    //The square that a piece is at
    private Square initialSquare;
    //The square that we want to move it to
    private Square finalSquare;

    /**
     * Constructs a Movement object, used to send movements over a network
     *
     * @param initialSquare the Square that a piece is being moved from
     * @param finalSquare the Square that the piece is being moved to
     */
    public Movement(Square initialSquare, Square finalSquare) {
        this.initialSquare = initialSquare;
        this.finalSquare = finalSquare;
    }

    public Square getInitialSquare() {
        return initialSquare;
    }

    public Square getFinalSquare() {
        return finalSquare;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "initialSquare=" + initialSquare +
                ", finalSquare=" + finalSquare +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return this.initialSquare.equals(movement.initialSquare) &&
                this.finalSquare.equals(movement.finalSquare);
    }
}