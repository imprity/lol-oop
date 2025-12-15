package loloop;

import java.util.HashMap;

import loloop.heroes.Garen;
import loloop.heroes.Ashe;
import loloop.heroes.Mario;
import loloop.heroes.Sanic;

public class GameConstants {
    public static final Hero.Stat DEFAULT_BASE_STAT = new Hero.Stat(100, 10, 20);

    private static final HashMap<Class<? extends Hero>, Hero.Stat> heroBaseStat = new HashMap<>();

    static {
        heroBaseStat.put(Garen.class, new Hero.Stat(100, 2, 5));
        heroBaseStat.put(Ashe.class, new Hero.Stat(200, 2, 5));
        heroBaseStat.put(Mario.class, new Hero.Stat(150, 7, 10));
        heroBaseStat.put(Sanic.class, new Hero.Stat(60, 10, 1));
    }

    public static Hero.Stat getBaseStat(Class<? extends Hero> heroClass) {
        Hero.Stat stat = heroBaseStat.get(heroClass);
        if (stat == null) {
            System.err.printf("ERROR: %s의 BaseStat이 없습니다. DEFAULT_BASE_STAT반환\n",
                heroClass.getName());

            return DEFAULT_BASE_STAT;
        }

        return stat;
    }
}
