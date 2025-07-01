package test;

import printing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrintingHouseTest {

    @Test
    public void testBuyPaperIncreasesInventoryAndCost() {
        PrintingHouse house = new PrintingHouse("ТестПечат", 50, 5, 7000);
        Paper paper = new Paper(PaperType.NORMAL, PageSize.A5, 0.10);

        house.buyPaper(paper, 1000);

        assertEquals(1000, house.getAvailablePaperSheets());

        // A5 няма увеличение, така че цената си е 0.10 * 1000 = 100.0
        assertEquals(100.0, house.getTotalExpenses(), 0.0001);
    }

    @Test
    public void testSuccessfulPrintIncreasesRevenue() {
        PrintingHouse house = new PrintingHouse("Печалбар", 40, 4, 6000);
        Paper paper = new Paper(PaperType.NORMAL, PageSize.A5, 0.10);
        house.buyPaper(paper, 1000);

        PrintingMachine machine = new PrintingMachine("C01", true, 1000, 60);
        house.addMachine(machine);
        house.loadPaperToMachine("C01", 200);

        // публикацията е 5 страници, 10 копия, цена на копие 2.0
        Publication book = new Publication("Библия", 10, 5, paper, PublicationType.BOOK);
        house.print(book, 10, true, "C01", 2.0);

        // Приход: 10 копия * 2.0 = 20.0
        assertEquals(20.0, house.getIncome(), 0.0001);


        assertEquals(800, house.getAvailablePaperSheets()); // 1000 - 200
    }

    @Test
    public void testLoadPaperReducesInventory() {
        PrintingHouse house = new PrintingHouse("Инвентар", 100, 20, 15000);
        Paper paper = new Paper(PaperType.NORMAL, PageSize.A5, 0.10);
        house.buyPaper(paper, 500);

        PrintingMachine machine = new PrintingMachine("B01", false, 500, 60);
        house.addMachine(machine);

        house.loadPaperToMachine("B01", 300);

        // 500 закупени - 300 заредени = 200 налични
        assertEquals(200, house.getAvailablePaperSheets());
    }

    @Test
    public void testPrintThrowsWhenNotEnoughPaperInHouse() {
        PrintingHouse house = new PrintingHouse("ПровалПечат", 60, 10 , 9000);
        Paper paper = new Paper(PaperType.NORMAL, PageSize.A5, 0.10);
        house.buyPaper(paper, 100);

        PrintingMachine machine = new PrintingMachine("X01", false, 200, 60);
        house.addMachine(machine);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            house.loadPaperToMachine("X01", 200); // иска да зареди повече, отколкото има
        });

        assertTrue(exception.getMessage().contains("Недостатъчно налична хартия"));
    }
}
