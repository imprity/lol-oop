package loloop.heroes;

import loloop.Game;
import loloop.GameConstants;
import loloop.Util;
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
        Hero.Stat baseStat = GameConstants.getBaseStat(this.getClass());

        return new Hero.Stat(
            baseStat.maxHealth + nextLevel * 10,
            baseStat.defense + Util.divideCeil(nextLevel, 2),
            baseStat.attackDamage + nextLevel * 5
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
