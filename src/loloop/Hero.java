package loloop;

public abstract class Hero {
    public static class Stat {
        public final int maxHealth;

        public final int defense;

        public final int attackDamage;

        public Stat(
            int maxHealth,
            int defense,
            int attackDamage
        ) {
            this.maxHealth = maxHealth;
            this.defense = defense;
            this.attackDamage = attackDamage;
        }

        public Stat(
            Stat other
        ) {
            this.maxHealth = other.maxHealth;
            this.defense = other.defense;
            this.attackDamage = other.attackDamage;
        }
    }

    private static int battleCounterGlobal = 0;
    private int battleCounter = 0;

    private Stat stat;
    private int health;

    private final String name;

    public final Team<Hero> team;

    private final String lastWords;

    private boolean isDead = false;

    private int level = 1;

    protected Hero(
        String name,
        Team<Hero> team,
        String lastWords
    ) {
        this.name = name;
        this.team = team;
        this.stat = GameConstants.getBaseStat(this.getClass());
        this.lastWords = lastWords;
        this.health = stat.maxHealth;
    }

    public int getHealth() {
        return this.health;
    }

    public void takeDamage(int damage) {
        if (this.isDead) {
            System.err.printf("ERROR: %s이(가) 죽은 상태에서 damage를 받앗습니다!\n",
                this.teamAndName());
            return;
        }

        damage -= stat.defense;

        if (damage < 0) {
            damage = 0;
        }

        System.out.printf("%s이(가) %s dmg를 받음!\n", this.teamAndName(), damage);
        this.health -= damage;

        if (this.health <= 0) {
            System.out.printf("%s이(가) 사망 - \"%s\"\n", this.teamAndName(), this.lastWords);
            this.health = 0;
            isDead = true;
        }
    }

    public void getHealed(int healing) {
        if (this.isDead) {
            System.err.printf("ERROR: %s이(가) 죽은 상태에서 healing을 받앗습니다!\n",
                this.teamAndName());
            return;
        }

        System.out.printf("%s이(가) %s 힐링을 받음!\n", this.teamAndName(), healing);
        this.health += healing;

        if (this.health > this.stat.maxHealth) {
            this.health = this.stat.maxHealth;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void levelUp() {
        int prevLevel = level;
        this.level += 1;

        stat = onLevelUp(prevLevel, level, this.stat);

        if (this.health > stat.maxHealth) {
            this.health = stat.maxHealth;
        }
    }

    public abstract Stat onLevelUp(int prevLevel, int nextLevel, Stat prevStat);

    public void doAttack(Hero otherHero) {
        System.out.printf("%s -> %s 기본 공격!\n",
            this.teamAndName(), otherHero.teamAndName());

        otherHero.takeDamage(this.stat.attackDamage);
    }

    public final void doQ(Game game) {
        System.out.printf("%s이(가) Q를 시전\n", this.teamAndName());
        doQImpl(game);
    }

    public abstract void doQImpl(Game game);

    public final void resurrect() {
        if (!this.isDead) {
            System.err.printf("ERROR: %s이(가) 살아있는은 상태에서 resurrect를 받앗습니다!\n",
                this.teamAndName());
            return;
        }

        System.out.printf("%s이(가) 부활했습니다!\n", this.teamAndName());

        this.isDead = !this.isDead;
        this.health = Util.divideCeil(this.stat.maxHealth, 10);
        if (this.health <= 0) {
            this.health = 1;
        }
    }

    public void increaseBattleCounter() {
        battleCounterGlobal += 1;
        battleCounter += 1;
    }

    public int getBattleCount() {
        return this.battleCounter;
    }

    public static int getGlobalBattleCount() {
        return battleCounterGlobal;
    }

    @Override
    public String toString() {
        return String.format(
            "팀:%s, 이름: %s, 체력:%s, level:%s, 배틀횟수: %s, %s",
            this.team.getName(),
            this.name,
            this.health,
            this.level,
            this.getBattleCount(),
            this.isDead() ? "죽음" : "살아있음"
        );
    }

    public String teamAndName() {
        return String.format("%s팀 %s", this.team.getName(), this.name);
    }
}

