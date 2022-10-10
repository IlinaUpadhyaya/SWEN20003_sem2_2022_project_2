package entities;

class HealthCalculator {
    private final double maxHealth;
    private double currentHealthPoints;
    private double remainingHealthPercentage;
    private final String parentClass;

    HealthCalculator(double maxHealth, String parentClass) {
        this.maxHealth = maxHealth;
        this.currentHealthPoints = maxHealth;
        this.remainingHealthPercentage = 100.0;
        this.parentClass = parentClass;
    }

    void onDamage(double damage, String damagingClass) {
        this.currentHealthPoints -= damage;
        if (this.currentHealthPoints < 0.0) currentHealthPoints = 0;
        this.remainingHealthPercentage = this.currentHealthPoints / maxHealth * 100.0;

        // Log message
        System.out.printf(damagingClass + " inflicts " + (int)damage + " damage points on " + this.parentClass + ". " +
                        this.parentClass + "'s current health: %d/%d\n", (int)(Math.round(this.currentHealthPoints)),
                (int)maxHealth);
    }

    boolean healthOver() {
        return currentHealthPoints <= 0;
    }

    double getRemainingHealthPercentage() {
        return remainingHealthPercentage;
    }
}
