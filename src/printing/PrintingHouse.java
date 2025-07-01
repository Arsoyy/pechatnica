package printing;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrintingHouse {
    private String name;
    private double discountThreshold;
    private double discountPercentage;
    private double managerBonusThreshold;

    private List<Employee> employees;
    private List<PrintingMachine> machines;
    private List<Publication> printedPublications;

    private double paperExpenses = 0;
    private double income = 0;
    private int availablePaperSheets = 0;

    private static final DecimalFormat df = new DecimalFormat("#0.00");

    public PrintingHouse(String name, double discountThreshold, double discountPercentage, double managerBonusThreshold) {
        this.name = name;
        this.discountThreshold = discountThreshold;
        this.discountPercentage = discountPercentage;
        this.managerBonusThreshold = managerBonusThreshold;
        this.employees = new ArrayList<>();
        this.machines = new ArrayList<>();
        this.printedPublications = new ArrayList<>();
    }

    // --- Служители и машини ---

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addMachine(PrintingMachine m) {
        machines.add(m);
    }

    // --- Работа с хартия ---

    public void buyPaper(Paper paper, int sheets) {
        double cost = sheets * paper.getPrice();
        paperExpenses += cost;
        availablePaperSheets += sheets;
        System.out.println("Закупена хартия: " + sheets + " листа за " + df.format(cost) + " лв.");
    }

    public void loadPaperToMachine(String machineId, int sheets) {
        if (sheets > availablePaperSheets) {
            throw new IllegalArgumentException("Недостатъчно налична хартия. Имате само " + availablePaperSheets + " листа.");
        }

        PrintingMachine machine = machines.stream()
                .filter(m -> m.getId().equals(machineId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Машина с ID " + machineId + " не съществува."));

        try {
            machine.loadPaper(sheets);
            availablePaperSheets -= sheets;
            System.out.println("Заредени " + sheets + " листа в машина " + machineId);
        } catch (IllegalArgumentException e) {
            System.err.println("Грешка при зареждане: " + e.getMessage());
        }
    }

    // --- Печат ---

    public void print(Publication pub, int copies, boolean color, String machineId, double pricePerCopy) {
        PrintingMachine machine = machines.stream()
                .filter(m -> m.getId().equals(machineId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Няма такава машина с ID: " + machineId));

        try {
            machine.print(pub, copies, color);

            double effectivePrice = pricePerCopy;
            if (copies > discountThreshold) {
                effectivePrice *= (1 - discountPercentage / 100);
            }

            double total = copies * effectivePrice;
            income += total;
            printedPublications.add(pub);

            System.out.println("Отпечатано: " + pub.getTitle() + " x " + copies + " бр. за " + df.format(total) + " лв.");

        } catch (Exception e) {
            System.err.println("Грешка при печат: " + e.getMessage());
        }
    }

    // --- Финансови отчети ---

    public double calculateSalaries() {
        double total = 0;
        for (Employee e : employees) {
            if (e instanceof Manager) {
                ((Manager) e).setActualRevenue(income);
            }
            total += e.calculateSalary();
        }
        return total;
    }

    public double getPaperExpenses() {
        return paperExpenses;
    }

    public double getIncome() {
        return income;
    }

    public double getTotalExpenses() {
        return paperExpenses + calculateSalaries();
    }

    public double getProfit() {
        return income - getTotalExpenses();
    }

    public int getAvailablePaperSheets() {
        return availablePaperSheets;
    }

    // --- Сериализация и десериализация на служители ---

    public void saveEmployeesToFile(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(employees);
            System.out.println("✅ Служителите са записани във файл: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Грешка при запис на служители: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadEmployeesFromFile(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            employees = (List<Employee>) in.readObject();
            System.out.println("✅ Служителите са заредени от файл: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Грешка при четене на служители: " + e.getMessage());
        }
    }

    // --- Общ отчет ---
    @Override
    public String toString() {
        return "Печатница: " + name + "\n" +
                "Приходи: " + df.format(getIncome()) + " лв.\n" +
                "Разходи: " + df.format(getTotalExpenses()) + " лв.\n" +
                "Печалба: " + df.format(getProfit()) + " лв.\n" +
                "Налична хартия: " + availablePaperSheets + " листа";
    }
}
