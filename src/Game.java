import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

class Game {
    private Random rng = new Random();

    private List<Hero> team0;
    private List<Hero> team1;

    public Game(
        List<Hero> team0,
        List<Hero> team1
    ) {
        this.team0 = team0;
        this.team1 = team1;
    }

    public List<Hero> getLiveHeroes(int team) { 
        List<Hero> heroes = team0;
        if (team == 1) {
            heroes = this.team1;
        }

        return heroes.stream().filter(x -> !x.isDead()).collect(Collectors.toList());
    }

    public Optional<Hero> pickRandomLiveHero(int team) {
        List<Hero> heroes = team0;
        if (team == 1) {
            heroes = this.team1;
        }

        int liveHeroes = 0;

        for (final Hero hero : heroes) {
            if (!hero.isDead()) {
                liveHeroes += 1;
            }
        }

        if (liveHeroes <= 0) {
            return Optional.ofNullable(null);
        }

        int liveIndex = rng.nextInt(liveHeroes);

        for (final Hero hero : heroes) {
            if (liveIndex == 0 && !hero.isDead()) {
                return Optional.of(hero);
            }

            if (!hero.isDead()) {
                liveIndex--;
            }
        }

        assert false; // SHOULD NEVER HAPPEN

        return Optional.ofNullable(null);
    }

    public int getLiveHeroesCount(int team) {
        List<Hero> heroes = team0;
        if (team == 1) {
            heroes = this.team1;
        }

        int count = 0;

        for (Hero hero : heroes) {
            if (!hero.isDead()) {
                count ++;
            }
        }
        
        return count;
    }

    public void doTurn() {
        List<Hero> heroes = new ArrayList<>();

        for (final Hero hero : team0) {
            heroes.add(hero);
        }
        for (final Hero hero : team1) {
            heroes.add(hero);
        }

        Collections.shuffle(heroes);

        for (Hero hero : heroes) {
            if (hero.isDead()) {
                continue;
            }

            if (rng.nextInt(100) < 70) {
                // 기본 공격 실행
                int otherTeam = 0;
                if (otherTeam == hero.team) {
                    otherTeam = 1;
                }

                Optional<Hero> otherHero = pickRandomLiveHero(otherTeam);
                if (otherHero.isPresent()) {
                    hero.doAttack(otherHero.get());
                }
            } else {
                hero.doQ(this);
            }

            if (rng.nextInt(100) < 30) {
                hero.levelUp();
            }
        }
    }
}
