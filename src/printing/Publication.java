package printing;

public class Publication {
    private String title;
    private int numberOfCopies;
    private int numberOfPages;
    private Paper paper;
    private PublicationType type;

    public Publication(String title, int numberOfCopies, int numberOfPages, Paper paper, PublicationType type) {
        this.title = title;
        this.numberOfCopies = numberOfCopies;
        this.numberOfPages = numberOfPages;
        this.paper = paper;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public Paper getPaper() {
        return paper;
    }

    public PublicationType getType() {
        return type;
    }

    public double getTotalPaperCost() {
        return numberOfCopies * numberOfPages * paper.getPrice();
    }

    @Override
    public String toString() {
        return type + ": " + title + ", Брой копия: " + numberOfCopies +
                ", Страници: " + numberOfPages + ", Хартия: " + paper;
    }
}