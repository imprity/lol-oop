package loloop;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

public class Game {
    private final Random rng = new Random();

    private final List<Hero> redTeam;
    private final List<Hero> blueTeam;

    public Game(
        List<Hero> redTeam,
        List<Hero> blueTeam
    ) {
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
    }

    public List<Hero> getLiveHeroes(Team team) { 
        List<Hero> heroes = redTeam;
        if (team == Team.BLUE) {
            heroes = this.blueTeam;
        }

        return heroes.stream().filter(x -> !x.isDead()).collect(Collectors.toList());
    }

    public Optional<Hero> pickRandomLiveHero(Team team) {
        List<Hero> heroes = redTeam;
        if (team == Team.BLUE) {
            heroes = this.blueTeam;
        }

        int liveHeroes = 0;

        for (final Hero hero : heroes) {
            if (!hero.isDead()) {
                liveHeroes += 1;
            }
        }

        if (liveHeroes <= 0) {
            return Optional.empty();
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

    public int getLiveHeroesCount(Team team) {
        List<Hero> heroes = redTeam;
        if (team == Team.BLUE) {
            heroes = this.blueTeam;
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

        heroes.addAll(redTeam);
        heroes.addAll(blueTeam);

        Collections.shuffle(heroes);

        for (Hero hero : heroes) {
            if (hero.isDead()) {
                continue;
            }

            if (rng.nextInt(100) < 70) {
                // 기본 공격 실행
                Team otherTeam = Team.RED;
                if (otherTeam == hero.team) {
                    otherTeam = Team.BLUE;
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
