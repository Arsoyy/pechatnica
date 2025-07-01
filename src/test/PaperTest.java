package test;
import printing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaperTest {

    @Test
    public void testPriceCalculationForA4() {
        Paper paper = new Paper(PaperType.NORMAL, PageSize.A4, 0.10); // A5 = 0.10
        // A4 multiplier: 1 + 0.2 * (2 - 1) = 1.2 → 0.10 * 1.2 = 0.12
        assertEquals(0.12, paper.getPrice(), 0.0001);
    }

    @Test
    public void testPriceCalculationForA3() {
        Paper paper = new Paper(PaperType.NORMAL, PageSize.A3, 0.10); // A5 = 0.10
        // A3 multiplier: 1 + 0.2 * (3 - 1) = 1.4 → 0.10 * 1.4 = 0.14
        assertEquals(0.14, paper.getPrice(), 0.0001);
    }

    @Test
    public void testPriceCalculationForA5() {
        Paper paper = new Paper(PaperType.NORMAL, PageSize.A5, 0.10); // базов
        assertEquals(0.10, paper.getPrice(), 0.0001);
    }
}
