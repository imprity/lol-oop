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

    private static BaseStat DEFAULT_STAT = new BaseStat(100, 10, 20);

    private BaseStat baseStat = DEFAULT_STAT;
    private int health = 100;

    private final String name;

    public final int team;

    private final String lastWords;

    private boolean isDead = false;

    private int level = 1;

    protected Hero(
        String name, 
        int team, 
        BaseStat baseStat, 
        String lastWords
    ) {
        this.name = name;
        this.team = team;
        this.baseStat = baseStat;
        this.lastWords = lastWords;
    }

    public void takeDamage(int damage) {
        if (this.isDead) {
            System.err.printf("ERROR: 팀%s, %s가 죽은 상태에서 damage를 받앗습니다!\n",
                    this.team, this.name);
            return;
        }

        damage -= baseStat.defense;

        if (damage < 0) {
            return;
        }

        System.out.printf("팀%s:%s이(가) %s dmg를 받음!\n", this.team, this.name, damage);
        this.health -= damage;

        if (this.health <= 0) {
            System.out.printf("팀%s:%s, 사망 - \"%s\"\n", this.team, this.name, this.lastWords);
            this.health = 0;
            isDead = true;
        }
    }

    public void getHealed(int healing) {
        if (this.isDead) {
            System.err.printf("ERROR: 팀%s, %s가 죽은 상태에서 healing을 받앗습니다!\n",
                    this.team, this.name);
            return;
        }

        System.out.printf("팀%s:%s이(가) %s 힐링을 받음!\n", this.team, this.name, healing);
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
        System.out.printf("팀%s:%s -> 팀%s:%s 기본 공격!\n", 
                this.team, this.name, otherHero.team, otherHero.name);

        otherHero.takeDamage(this.baseStat.attackDamage);
    }

    public void doQ(Game game) {
        System.out.printf("팀%s:%s이(가) Q를 시전\n", this.team, this.name);
        doQImpl(game);
    }

    public abstract void doQImpl(Game game);

    @Override
    public String toString() {
        return String.format("팀:%s, 이름: %s, 체력:%s, level:%s, %s", this.team, this.name, this.health, this.level, this.isDead() ? "죽음" : "살아있음");
    }
}

