package test;

import printing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PrintingMachineTest {

    @Test
    public void testLoadPaper() {
        PrintingMachine machine = new PrintingMachine("M01", true, 1000, 60);
        machine.loadPaper(1000);
        assertEquals(1000, machine.getCurrentPaperLoaded());
    }

    @Test
    public void testPrintSuccess() {
        PrintingMachine machine = new PrintingMachine("M02", false, 1000, 50);
        machine.loadPaper(500);

        Paper paper = new Paper(PaperType.NORMAL, PageSize.A5, 0.10);
        Publication book = new Publication("Тестова книга", 10, 5, paper, PublicationType.BOOK);
        machine.print(book, 10, false);

        assertEquals(50, machine.getTotalPagesPrinted()); // 5 стр. * 10 копия
        assertEquals(10, machine.getPrintedPublications().get(book));
    }

    @Test
    public void testPrintThrowsExceptionWhenNotEnoughPaper() {
        PrintingMachine machine = new PrintingMachine("M03", false, 60, 100);
        machine.loadPaper(10); // недостатъчно за 3 копия по 5 стр.

        Paper paper = new Paper(PaperType.NORMAL, PageSize.A4, 0.10);
        Publication book = new Publication("Книга", 3, 5, paper, PublicationType.BOOK);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            machine.print(book, 3, false); // 3 * 5 = 15 страници → грешка
        });

        assertTrue(exception.getMessage().contains("Недостатъчно хартия"));
    }
}
