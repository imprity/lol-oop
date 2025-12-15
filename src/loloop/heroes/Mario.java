package loloop.heroes;

import java.util.Optional;

import loloop.Util;

import loloop.Game;
import loloop.GameConstants;
import loloop.Hero;
import loloop.Team;

public class Mario extends Hero {
    public Mario(
        Team team,
        String name
    ) {
        super(
            name,
            team,
            "Mamma Mia!!"
        );
    }

    @Override
    public Stat onLevelUp(int prevLevel, int nextLevel, Stat prevStat) {
        Hero.Stat baseStat = GameConstants.getBaseStat(this.getClass());

        return new Hero.Stat(
            baseStat.maxHealth + nextLevel * 10,
            baseStat.defense + Util.divideCeil(nextLevel, 3),
            baseStat.attackDamage + nextLevel * 10
        );
    }

    @Override
    public void doQImpl(Game game) {
        System.out.printf("%s(이)가 100M 상공으로 점프를 했습니다!!\n", this.teamAndName());

        Optional<Hero> enemy = game.pickRandomLiveHero(this.team.getOtherTeam());
        if (enemy.isPresent()) {
            System.out.printf("%s(이)가 %s를(을) 향해 낙하합니다!!\n",
                this.teamAndName(), enemy.get().teamAndName());
            enemy.get().takeDamage(Util.divideCeil(enemy.get().getHealth(), 4));
        }
    }
}
