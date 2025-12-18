package loloop.heroes;

import loloop.Game;
import loloop.GameConstants;
import loloop.Hero;
import loloop.Team;

import java.util.Random;

public class Sanic extends Hero {
    private final Random rng = new Random();

    public Sanic(
        Team<Hero> team,
        String name
    ) {
        super(
            name,
            team,
            "GOTTA GO FAST!!"
        );
    }

    @Override
    public Stat onLevelUp(int prevLevel, int nextLevel, Stat prevStat) {
        Hero.Stat baseStat = GameConstants.getBaseStat(this.getClass());

        return new Hero.Stat(
            baseStat.maxHealth + nextLevel * 1,
            baseStat.defense + nextLevel,
            baseStat.attackDamage + nextLevel * 2
        );
    }

    private final static String[] sanicActions = new String[]{
        "핫도그를 샀습니다!",
        "친구한테 돈을 빌릴려고 합니다!",
        "트위터를 열었습니다!",
    };

    private final static String[] catchPhrases = new String[]{
        "CHILI DOG!!",
        "WAY PAST COOL!!",
        "LET’S JUICE!!!",
    };

    @Override
    public void doQImpl(Game game) {
        String action = sanicActions[rng.nextInt(sanicActions.length)];
        String catchPhrase = catchPhrases[rng.nextInt(catchPhrases.length)];

        System.out.printf("%s(이)가 %s\n", this.teamAndName(), action);
        System.out.println(catchPhrase);
    }
}
