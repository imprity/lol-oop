package loloop.heroes;

import loloop.Game;
import loloop.GameConstants;
import loloop.Hero;
import loloop.Team;
import loloop.Util;

import java.util.List;

public class Garen extends Hero {
    public Garen(
        Team<Hero> team,
        String name
    ) {
        super(
            name,
            team,
            "으악 죽었다"
        );
    }

    @Override
    public Stat onLevelUp(int prevLevel, int nextLevel, Stat prevStat) {
        Hero.Stat baseStat = GameConstants.getBaseStat(this.getClass());

        return new Hero.Stat(
            baseStat.maxHealth + nextLevel * 5,
            baseStat.defense + Util.divideCeil(nextLevel, 3),
            baseStat.attackDamage + nextLevel * 10
        );
    }

    @Override
    public void doQImpl(Game game) {
        System.out.println("소용돌이 빔!!");
        Team<Hero> otherTeam = game.getOtherTeam(this.team);

        List<Hero> otherTeamHeroes = game.getLiveHeroes(otherTeam);

        for (Hero hero : otherTeamHeroes) {
            hero.takeDamage(20);
        }
    }
}
