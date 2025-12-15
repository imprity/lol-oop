package loloop;

public abstract class Hero {
    public static class BaseStat {
        public final int maxHealth;

        public final  int defense;

        public final int attackDamage;

        public BaseStat(
            int maxHealth,
            int defense,
            int attackDamage
        ) {
            this.maxHealth = maxHealth;
            this.defense = defense;
            this.attackDamage = attackDamage;
        }

        public BaseStat(
            BaseStat other
        ) {
            this.maxHealth = other.maxHealth;
            this.defense = other.defense;
            this.attackDamage = other.attackDamage;
        }
    }

    private BaseStat baseStat = GameConstants.DEFAULT_STAT;
    private int health;

    private final String name;

    public final Team team;

    private final String lastWords;

    private boolean isDead = false;

    private int level = 1;

    protected Hero(
        String name, 
        Team team, 
        BaseStat baseStat, 
        String lastWords
    ) {
        this.name = name;
        this.team = team;
        this.baseStat = baseStat;
        this.lastWords = lastWords;
        this.health = baseStat.maxHealth;
    }

    public void takeDamage(int damage) {
        if (this.isDead) {
            System.err.printf("ERROR: %s이(가) 죽은 상태에서 damage를 받앗습니다!\n",
                    this.teamAndName());
            return;
        }

        damage -= baseStat.defense;

        if (damage < 0) {
            return;
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

        if (this.health > this.baseStat.maxHealth) {
            this.health = this.baseStat.maxHealth;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void levelUp() {
        int prevLevel = level;
        this.level += 1;

        baseStat = onLevelUp(prevLevel, level, this.baseStat);

        if (this.health > baseStat.maxHealth) {
            this.health = baseStat.maxHealth;
        }
    }

    public abstract BaseStat onLevelUp(int prevLevel, int nextLevel, BaseStat prevStat);

    public void doAttack(Hero otherHero) {
        System.out.printf("%s -> %s 기본 공격!\n", 
                this.teamAndName(), otherHero.teamAndName());

        otherHero.takeDamage(this.baseStat.attackDamage);
    }

    public final void doQ(Game game) {
        System.out.printf("%s이(가) Q를 시전\n", this.teamAndName());
        doQImpl(game);
    }

    public abstract void doQImpl(Game game);

    @Override
    public String toString() {
        return String.format(
            "팀:%s, 이름: %s, 체력:%s, level:%s, %s", 
            this.team, 
            this.name, 
            this.health, 
            this.level, 
            this.isDead() ? "죽음" : "살아있음"
        );
    }

    public String teamAndName() {
        return String.format("%s팀 %s", this.team, this.name);
    }
}

