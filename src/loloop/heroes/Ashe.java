package loloop.heroes;

import loloop.Game;
import loloop.Hero;
import loloop.Team;

import java.util.Optional;

public class Ashe extends Hero {
    public Ashe(
        Team team,
        String name
    ) {
        super(
            name,
            team,
            "어머, 죽었넹"
        );
    }

    @Override
    public Stat onLevelUp(int prevLevel, int nextLevel, Stat prevStat) {
        int diff = nextLevel - prevLevel;
        return new Hero.Stat(
            prevStat.maxHealth + diff * 10,
            prevStat.defense + diff * 10,
            prevStat.attackDamage + diff * 3
        );
    }

    @Override
    public void doQImpl(Game game) {
        System.out.println("힐링 타임!!");
        Team myTeam = Team.RED;
        if (myTeam != team) {
            myTeam = Team.BLUE;
        }

        Optional<Hero> friend = game.pickRandomLiveHero(myTeam);

        if (friend.isPresent()) {
            friend.get().getHealed(10);
        }
    }
}
