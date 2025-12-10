import java.util.List;
import java.util.Optional;

class Ashe extends Hero{
    public Ashe(
        int team
    ) {
        super(
            "애쉬",
            team,
            new Hero.BaseStat(200, 2, 5),
            "어머, 죽었넹"
        );
    }

    @Override
    public BaseStat onLevelUp(int prevLevel, int nextLevel, BaseStat prevStat) {
        int diff = nextLevel - prevLevel;
        return new Hero.BaseStat(
                prevStat.maxHealth + diff * 5,
                prevStat.defense + diff * 5,
                prevStat.attackDamage + diff * 5
        );
    }

    @Override
    public void doQImpl(Game game) {
        System.out.println("힐링 타임!!");
        int myTeam = 0;
        if (myTeam  != team) {
            myTeam = 1;
        }

        Optional<Hero> friend = game.pickRandomLiveHero(myTeam);

        if (friend.isPresent()) {
            friend.get().getHealed(10);
        }
    }
}
