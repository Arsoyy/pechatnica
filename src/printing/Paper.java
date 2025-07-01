package printing;

import java.text.DecimalFormat;

public class Paper {
    private PaperType type;
    private PageSize size;
    private double basePriceA5;

    public Paper(PaperType type, PageSize size, double basePriceA5) {
        this.type = type;
        this.size = size;
        this.basePriceA5 = basePriceA5;
    }

    public double getPrice() {
        // Увеличение с 20% на стъпка нагоре от A5
        double multiplier = 1 + 0.2 * (size.getMultiplier() - 1);
        return basePriceA5 * multiplier;
    }

    public PaperType getType() {
        return type;
    }

    public PageSize getSize() {
        return size;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return type + " " + size + " (" + df.format(getPrice()) + " лв.)";
    }
}