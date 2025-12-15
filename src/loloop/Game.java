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

    private Optional<Hero> pickRandomLiveOrDeadHero(Team team, boolean pickAlive) {
        List<Hero> heroes = redTeam;
        if (team == Team.BLUE) {
            heroes = this.blueTeam;
        }

        int pickedHeroes = 0;

        for (final Hero hero : heroes) {
            boolean pickable = hero.isDead();
            if (pickAlive) {
                pickable = !pickable;
            }

            if (pickable) {
                pickedHeroes += 1;
            }
        }

        if (pickedHeroes <= 0) {
            return Optional.empty();
        }

        int pickIndex = rng.nextInt(pickedHeroes);

        for (final Hero hero : heroes) {
            boolean pickable = hero.isDead();
            if (pickAlive) {
                pickable = !pickable;
            }

            if (pickIndex == 0 && pickable) {
                return Optional.of(hero);
            }

            if (pickable) {
                pickIndex--;
            }
        }

        assert false; // SHOULD NEVER HAPPEN

        return Optional.ofNullable(null);
    }

    public Optional<Hero> pickRandomLiveHero(Team team) {
        return pickRandomLiveOrDeadHero(team, true);
    }

    public Optional<Hero> pickRandomDeadHero(Team team) {
        return pickRandomLiveOrDeadHero(team, false);
    }

    public int getLiveHeroesCount(Team team) {
        List<Hero> heroes = redTeam;
        if (team == Team.BLUE) {
            heroes = this.blueTeam;
        }

        int count = 0;

        for (Hero hero : heroes) {
            if (!hero.isDead()) {
                count++;
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

            hero.increaseBattleCounter();

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
