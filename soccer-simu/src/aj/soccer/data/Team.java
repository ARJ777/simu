package aj.soccer.data;

import java.util.List;

/**
 * Describes a single team.
 */
public interface Team {

    /**
     * Obtains the name of the team.
     *
     * @return The team name.
     */
    public String getName();

    /**
     * Obtains the list of all potential players in a team.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers();

    /**
     * Obtains the initial team formation.
     *
     * @return The overall formation.
     */
    public Formation getFormation();

    /**
     * Sets the initial team formation.
     *
     * @param formation - The overall formation.
     */
    public void setFormation(Formation formation);

    /**
     * Obtains the currently active players.
     *
     * @return The list of active players.
     */
    public List<Player> getActivePlayers();

}
