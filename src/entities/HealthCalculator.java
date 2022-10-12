package entities;

class HealthCalculator {
    private final double maxHealth;
    private final String parentClassName;
    private double currentHealthPoints;
    private double remainingHealthPercentage;

    HealthCalculator(double maxHealth, String parentClassName) {
        this.maxHealth = maxHealth;
        this.currentHealthPoints = maxHealth;
        this.remainingHealthPercentage = 100.0;
        this.parentClassName = parentClassName;
    }

    void onDamage(double damage, String damagingClass) {
        this.currentHealthPoints -= damage;
        if (this.currentHealthPoints < 0.0) currentHealthPoints = 0;
        this.remainingHealthPercentage = this.currentHealthPoints / maxHealth * 100.0;

        System.out.printf("%s inflicts %d damage points on %s. %s's current health: %d/%d\n",
                damagingClass, (int) damage, this.parentClassName, this.parentClassName,
                (int) (Math.round(this.currentHealthPoints)), (int) (Math.round(this.maxHealth)));
    }

    boolean healthOver() {
        return currentHealthPoints <= 0;
    }

    double getRemainingHealthPercentage() {
        return remainingHealthPercentage;
    }
}
