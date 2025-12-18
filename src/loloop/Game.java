package loloop;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

public class Game {
    private final Random rng = new Random();

    private final Team<Hero> redTeam;
    private final Team<Hero> blueTeam;

    public Game(
        Team<Hero> redTeam,
        Team<Hero> blueTeam
    ) {
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
    }

    public Team<Hero> getOtherTeam(Team<Hero> team) {
        Team<Hero> heroes = redTeam;
        if (team.getId() == redTeam.getId()) {
            heroes = this.blueTeam;
        }
        return heroes;
    }

    public List<Hero> getLiveHeroes(Team<Hero> team) {
        return team.view().stream().filter(x -> !x.isDead()).collect(Collectors.toList());
    }

    private Optional<Hero> pickRandomLiveOrDeadHero(Team<Hero> team, boolean pickAlive) {
        int pickedHeroes = 0;

        for (final Hero hero : team.view()) {
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

        for (final Hero hero : team.view()) {
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

        return Optional.empty();
    }

    public Optional<Hero> pickRandomLiveHero(Team<Hero> team) {
        return pickRandomLiveOrDeadHero(team, true);
    }

    public Optional<Hero> pickRandomDeadHero(Team<Hero> team) {
        return pickRandomLiveOrDeadHero(team, false);
    }

    public int getLiveHeroesCount(Team<Hero> team) {
        return (int) team.view().stream().filter(hero -> !hero.isDead()).count();
    }

    public void doTurn() {
        List<Hero> heroes = new ArrayList<>();

        heroes.addAll(redTeam.view());
        heroes.addAll(blueTeam.view());

        Collections.shuffle(heroes);

        for (Hero hero : heroes) {
            if (hero.isDead()) {
                continue;
            }

            hero.increaseBattleCounter();

            if (rng.nextInt(100) < 70) {
                // 기본 공격 실행
                Team<Hero> otherTeam = getOtherTeam(hero.team);

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
