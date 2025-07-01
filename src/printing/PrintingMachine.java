package printing;

import java.util.HashMap;
import java.util.Map;

public class PrintingMachine {
    private String id;
    private boolean colorCapable;
    private int maxPaperCapacity;
    private int currentPaperLoaded;
    private int pagesPerMinute;
    private Map<Publication, Integer> printedPublications;

    public PrintingMachine(String id, boolean colorCapable, int maxPaperCapacity, int pagesPerMinute) {
        this.id = id;
        this.colorCapable = colorCapable;
        this.maxPaperCapacity = maxPaperCapacity;
        this.pagesPerMinute = pagesPerMinute;
        this.printedPublications = new HashMap<>();
    }

    public void loadPaper(int sheets) throws IllegalArgumentException {
        if (currentPaperLoaded + sheets > maxPaperCapacity) {
            throw new IllegalArgumentException("Надвишен капацитет на хартията!");
        }
        currentPaperLoaded += sheets;
    }

    public void print(Publication publication, int copies, boolean color) throws UnsupportedOperationException {
        if (color && !colorCapable) {
            throw new UnsupportedOperationException("Машината не поддържа цветен печат.");
        }

        int requiredSheets = copies * publication.getNumberOfPages();

        if (requiredSheets > currentPaperLoaded) {
            throw new IllegalStateException("Недостатъчно хартия в машината.");
        }

        printedPublications.merge(publication, copies, Integer::sum);
        currentPaperLoaded -= requiredSheets;
    }

    public int getTotalPagesPrinted() {
        int total = 0;
        for (Map.Entry<Publication, Integer> entry : printedPublications.entrySet()) {
            total += entry.getKey().getNumberOfPages() * entry.getValue();
        }
        return total;
    }

    public String getId() {
        return id;
    }

    public boolean isColorCapable() {
        return colorCapable;
    }

    public int getCurrentPaperLoaded() {
        return currentPaperLoaded;
    }

    public Map<Publication, Integer> getPrintedPublications() {
        return printedPublications;
    }

    @Override
    public String toString() {
        return "Машина [" + id + "] | Цветен печат: " + (colorCapable ? "Да" : "Не") +
                " | Заредена хартия: " + currentPaperLoaded + " | Скорост: " + pagesPerMinute + " стр/мин";
    }
}
