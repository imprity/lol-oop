package loloop.heroes;

import loloop.Game;
import loloop.Hero;
import loloop.Team;

import java.util.List;

public class Garen extends Hero{
    public Garen(
        Team team,
        String name
    ) {
        super(
            name,
            team,
            new Hero.BaseStat(100, 2, 5),
            "으악 죽었다"
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
        System.out.println("소용돌이 빔!!");
        Team otherTeam = this.team.getOtherTeam();

        List<Hero> otherTeamHeroes = game.getLiveHeroes(otherTeam);

        for (Hero hero : otherTeamHeroes) {
            hero.takeDamage(20);
        }
    }
}
