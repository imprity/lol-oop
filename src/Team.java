public enum Team {
    RED("빨강"),
    BLUE("파랑");

    public final String name;

    Team(String name) {
        this.name = name;
    }

    public Team getOtherTeam() {
        if (this == RED) {
            return BLUE;
        }
        return RED;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
