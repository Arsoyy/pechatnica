package printing;

public enum PageSize {
    A5(1),
    A4(2),
    A3(3),
    A2(4),
    A1(5);

    private final int multiplier;

    PageSize(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getPriceMultiplier() {
        return multiplier;
    }
}