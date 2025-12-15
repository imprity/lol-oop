package loloop.heroes;

import java.util.Optional;

import loloop.Util;

import loloop.Game;
import loloop.Hero;
import loloop.Team;

public class Mario extends Hero{
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
    public BaseStat onLevelUp(int prevLevel, int nextLevel, BaseStat prevStat) {
        int diff = nextLevel - prevLevel;
        return new Hero.BaseStat(
                prevStat.maxHealth + diff * 20,
                prevStat.defense + diff * 20,
                prevStat.attackDamage + diff * 20
        );
    }

    @Override
    public void doQImpl(Game game) {
        System.out.printf("%s(이)가 100M 상공으로 점프를 했습니다!!\n", this.teamAndName());

        Optional<Hero> enemy = game.pickRandomLiveHero(this.team.getOtherTeam());
        if (enemy.isPresent()) {
            System.out.printf("%s(이)가 %s를(을) 향해 낙하합니다!!\n", 
                    this.teamAndName(), enemy.get().teamAndName());
            enemy.get().takeDamage(Util.divideCeiling(enemy.get().getHealth(), 4));
        }
    }
}
