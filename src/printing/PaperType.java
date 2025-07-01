package printing;

public enum PaperType {
    NORMAL(1.0),
    GLOSSY(1.2),
    NEWSPAPER(0.8);

    private final double priceMultiplier;

    PaperType(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}