package loloop;

import java.util.HashMap;

import loloop.heroes.Garen;
import loloop.heroes.Ashe;
import loloop.heroes.Mario;
import loloop.heroes.Sanic;

public class GameConstants {
    public static final Hero.BaseStat DEFAULT_STAT = new Hero.BaseStat(100, 10, 20);

    private static final HashMap<Class<? extends Hero>, Hero.BaseStat> heroBaseStat = new HashMap<>();

    static {
        heroBaseStat.put(Garen.class, new Hero.BaseStat(100, 2, 5));
        heroBaseStat.put(Ashe.class, new Hero.BaseStat(200, 2, 5));
        heroBaseStat.put(Mario.class, new Hero.BaseStat(150, 7, 10));
        heroBaseStat.put(Sanic.class, new Hero.BaseStat(60, 10, 1));
    }

    public static Hero.BaseStat getBaseStat(Class<? extends Hero> heroClass) {
        Hero.BaseStat stat = heroBaseStat.get(heroClass);
        if (stat == null) {
            System.err.printf("ERROR: %s의 BaseStat이 없습니다. DEFAULT_STAT반환\n",
                heroClass.getName());

            return DEFAULT_STAT;
        }

        return stat;
    }
}
