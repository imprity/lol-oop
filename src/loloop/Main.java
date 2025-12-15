package loloop;

import loloop.heroes.Garen;
import loloop.heroes.Ashe;
import loloop.heroes.Sanic;

import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // ===============
        // hero 생성
        // ===============
        List<Hero> redTeam = Arrays.asList(
            new Garen(Team.RED, "잘생긴 가렌"),
            new Sanic(Team.RED, "잘생긴 Sanic"),
            new Ashe(Team.RED, "잘생긴 애쉬1"),
            new Ashe(Team.RED, "잘생긴 애쉬2")
        );

        List<Hero> blueTeam = Arrays.asList(
            new Ashe(Team.BLUE, "귀요미 애쉬"),
            new Sanic(Team.BLUE, "귀요미 Sanic"),
            new Garen(Team.BLUE, "귀요미 가렌1"),
            new Garen(Team.BLUE, "귀요미 가렌2")
        );

        printTeamStat(Team.RED, redTeam);
        printTeamStat(Team.BLUE, blueTeam);

        // =====================
        // 배너 출력
        // =====================
        System.out.println();
        System.out.println("======================================================");
        System.out.println("======== 소환사의 협곡에 오신 것을 환영합니다 ========");
        System.out.println("====================== 전투시작 ======================");
        System.out.println("======================================================");
        System.out.println("==== (전투는 한팀이 전멸 할때 까지 진행 됩니다.)  ====");
        System.out.println("======================================================");
        System.out.println();

        Game game = new Game(redTeam, blueTeam);

        while (true) {
            System.out.println();
            System.out.println("====================== 턴 시작 =======================");
            System.out.println();
            game.doTurn();
            System.out.println();
            System.out.println("====================== 턴 종료 =======================");
            System.out.println();

            if (game.getLiveHeroesCount(Team.RED) == 0) {
                System.out.printf("%s 팀 승리\n", Team.BLUE);
                break;
            }

            if (game.getLiveHeroesCount(Team.BLUE) == 0) {
                System.out.printf("%s 팀 승리\n", Team.RED);
                break;
            }
        }

        System.out.println("[ 전투 종료 ]");
        System.out.println("[ 전투 결과 ]");

        printTeamStat(Team.RED, redTeam);
        printTeamStat(Team.BLUE, blueTeam);
    }

    private static void printTeamStat(Team team, List<Hero> teamHeroes) {
        System.out.printf("\n[ %s팀 ]\n\n", team);
        for (Hero hero : teamHeroes) {
            System.out.println(hero);
        }
    }
}
