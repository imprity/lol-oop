package loloop.heroes;

import loloop.Game;
import loloop.Hero;
import loloop.Team;

import java.util.Optional;

public class Ashe extends Hero{
    public Ashe(
        Team team,
        String name
    ) {
        super(
            name,
            team,
            new Hero.BaseStat(200, 2, 5),
            "어머, 죽었넹"
        );
    }

    @Override
    public BaseStat onLevelUp(int prevLevel, int nextLevel, BaseStat prevStat) {
        int diff = nextLevel - prevLevel;
        return new Hero.BaseStat(
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
