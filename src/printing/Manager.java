package printing;

public class Manager extends Employee {
    private double bonusPercentage;
    private double revenueThreshold;
    private double actualRevenue;

    public Manager(String name, double baseSalary, double bonusPercentage, double revenueThreshold) {
        super(name, baseSalary);
        this.bonusPercentage = bonusPercentage;
        this.revenueThreshold = revenueThreshold;
    }

    public void setActualRevenue(double revenue) {
        this.actualRevenue = revenue;
    }



    @Override
    public double calculateSalary() {
        if (actualRevenue > revenueThreshold) {
            return getBaseSalary() * (1 + bonusPercentage / 100);
        } else {
            return getBaseSalary();
        }
    }

    @Override
    public String toString() {
        return "Мениджър - " + super.toString();
    }
}