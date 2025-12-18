package loloop;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Team<T extends Hero> {
    private final List<T> teamMembers;

    private static int idCounter = 1;

    private final int id;

    private final String name;

    public Team(String name) {
        this.teamMembers = new ArrayList<>();
        this.id = Team.idCounter;
        this.name = name;

        Team.idCounter++;
    }

    public Team(String name, List<T> teamMembers) {
        this.teamMembers = teamMembers;
        this.id = Team.idCounter;
        this.name = name;

        Team.idCounter++;
    }

    public void addTeamMembers(List<T> teamMembers) {
        this.teamMembers.addAll(teamMembers);
    }

    List<T> view() {
        return Collections.unmodifiableList(this.teamMembers);
    }

    int getId() {
        return this.id;
    }

    String getName() {
        return this.name;
    }
}
