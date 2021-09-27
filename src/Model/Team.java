/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11am
 * Date: 11/10/19
 * Time: 11:58 AM
 *
 * Project: csci205finalproject
 * Package: model
 * Class: Team
 *
 * Description:
 * A Team enum that is either black or white, representing how all chess pieces on the board,
 * are either on the black or white team.
 * ****************************************
 */
package Model;

/**
 * An enum used to differentiate players by team. White always goes first.
 */
public enum Team {
    BLACK("Black"),
    WHITE("White");

    private String stringRepresentation;
    Team(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    /**
     * Gets the other team than this instantiation of the team
     * @return if black then white, if white then black
     */
    public Team getOtherTeam(){
        if(this == BLACK){return WHITE;}
        else{return BLACK;}
    }
}
