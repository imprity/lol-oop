import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        List<Hero> team0 = Arrays.asList(
            new Garen(0),
            new Ashe(0),
            new Ashe(0),
            new Ashe(0)
        );

        List<Hero> team1 = Arrays.asList(
            new Ashe(1),
            new Garen(1),
            new Garen(1),
            new Garen(1)
        );

        Game game = new Game(team0, team1);

        while (true) {
            game.doTurn();

            if (game.getLiveHeroesCount(0) == 0) {
                System.out.println("팀 1 승리");
                break;
            }

            if (game.getLiveHeroesCount(1) == 0) {
                System.out.println("팀 0 승리");
                break;
            }
        }

        System.out.println("TEAM0 ===========");
        for (Hero hero : team0) {
            System.out.println(hero);
        }

        System.out.println("TEAM1 ===========");
        for (Hero hero : team1) {
            System.out.println(hero);
        }
    }
}
