package test;

import printing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {

    @Test
    public void testSalaryWithoutBonus() {
        Manager manager = new Manager("Георги", 2000, 10, 5000); // 10% бонус, праг 5000
        manager.setActualRevenue(3000); // под прага
        assertEquals(2000, manager.calculateSalary(), 0.0001);
    }

    @Test
    public void testSalaryWithBonus() {
        Manager manager = new Manager("Мария", 2000, 10, 5000); // 10% бонус, праг 5000
        manager.setActualRevenue(6000); // над прага
        assertEquals(2200, manager.calculateSalary(), 0.0001); // 2000 + 10%
    }
}