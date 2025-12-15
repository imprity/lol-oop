package loloop.heroes;

import loloop.Game;
import loloop.Hero;
import loloop.Team;

import java.util.Random;

public class Sanic extends Hero{
    private final Random rng = new Random();

    public Sanic(
        Team team,
        String name
    ) {
        super(
            name,
            team,
            "GOTTA GO FAST!!"
        );
    }

    @Override
    public BaseStat onLevelUp(int prevLevel, int nextLevel, BaseStat prevStat) {
        int diff = nextLevel - prevLevel;
        return new Hero.BaseStat(
                prevStat.maxHealth + diff * 1,
                prevStat.defense + diff * 1,
                prevStat.attackDamage + diff * 1
        );
    }

    private final static String[] sanicActions = new String[] {
        "핫도그를 샀습니다!",
        "친구한테 돈을 빌릴려고 합니다!",
        "트위터를 열었습니다!",
    };

    private final static String[] catchPhrases = new String[] {
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
