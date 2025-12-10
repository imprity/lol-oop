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

        System.out.println("==== 소환사의 협곡에 오신 것을 환영합니다.       ====");
        System.out.println("==== 전투 시작                                   ====");
        System.out.println("==== (전투는 한팀이 전멸 할때 까지 진행 됩니다.) ====");
        System.out.println();

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

        System.out.println("=== 전투 종료 ===");
        System.out.println("=== 전투 결과 ===");

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
