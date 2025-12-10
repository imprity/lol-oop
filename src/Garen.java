import java.util.List;

class Garen extends Hero{
    public Garen(
        int team
    ) {
        super(
            "가렌",
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
        int otherTeam = 0;
        if (otherTeam == team) {
            otherTeam = 1;
        }

        List<Hero> otherTeamHeroes = game.getLiveHeroes(otherTeam);

        for (Hero hero : otherTeamHeroes) {
            hero.takeDamage(20);
        }
    }
}
