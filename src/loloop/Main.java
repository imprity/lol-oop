package loloop;

import loloop.heroes.Garen;
import loloop.heroes.Ashe;
import loloop.heroes.Sanic;
import loloop.heroes.Mario;

import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // ===============
        // hero 생성
        // ===============
        Team<Hero> redTeam = new Team<>("빨강");
        Team<Hero> blueTeam = new Team<>("파랑");

        {
            List<Hero> redTeamList = Arrays.asList(
                new Garen(redTeam, "잘생긴 가렌"),
                new Sanic(redTeam, "잘생긴 Sanic"),
                new Mario(redTeam, "잘생긴 마리오"),
                new Ashe(redTeam, "잘생긴 애쉬")
            );

            List<Hero> blueTeamList = Arrays.asList(
                new Ashe(blueTeam, "귀요미 애쉬"),
                new Sanic(blueTeam, "귀요미 Sanic"),
                new Garen(blueTeam, "귀요미 가렌1"),
                new Garen(blueTeam, "귀요미 가렌2")
            );

            redTeam.addTeamMembers(redTeamList);
            blueTeam.addTeamMembers(blueTeamList);
        }

        printTeamStat(redTeam);
        printTeamStat(blueTeam);

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

            if (game.getLiveHeroesCount(redTeam) == 0) {
                System.out.printf("%s 팀 승리\n", blueTeam.getName());
                break;
            }

            if (game.getLiveHeroesCount(blueTeam) == 0) {
                System.out.printf("%s 팀 승리\n", redTeam.getName());
                break;
            }
        }

        System.out.println("[ 전투 종료 ]");
        System.out.println("[ 전투 결과 ]");

        printTeamStat(redTeam);
        printTeamStat(blueTeam);

        System.out.printf("\n총 배틀 횟수 : %s\n", Hero.getGlobalBattleCount());
    }

    private static void printTeamStat(Team<Hero> team) {
        System.out.printf("\n[ %s팀 ]\n\n", team.getName());
        for (Hero hero : team.view()) {
            System.out.println(hero);
        }
    }
}
