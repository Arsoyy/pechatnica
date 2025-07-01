package printing;

public class MachineOperator extends Employee {

    public MachineOperator(String name, double baseSalary) {
        super(name, baseSalary);
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary();
    }

    @Override
    public String toString() {
        return "Оператор - " + super.toString();
    }
}