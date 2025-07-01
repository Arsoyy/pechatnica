import printing.*;

public class main {
    public static void main(String[] args) {
        // Създаваме печатница
        PrintingHouse house = new PrintingHouse("Мега Принт", 50, 5, 10000);

        // Добавяме служители
        house.addEmployee(new MachineOperator("Иван", 1200));
        house.addEmployee(new Manager("Анна", 2000, 10, 10000));

        // Добавяме машини
        PrintingMachine colorMachine = new PrintingMachine("C01", true, 5000, 60);
        PrintingMachine bwMachine = new PrintingMachine("B01", false, 5000, 40);

        house.addMachine(colorMachine);
        house.addMachine(bwMachine);

        // Купуваме три вида хартия
        Paper bookPaper = new Paper(PaperType.NORMAL, PageSize.A5, 0.10);     // A3 нормална
        Paper newspaperPaper = new Paper(PaperType.NEWSPAPER, PageSize.A4, 0.10); // A5 вестникарска
        Paper posterPaper = new Paper(PaperType.GLOSSY, PageSize.A3, 0.10);   // A4 гланцирана

        house.buyPaper(bookPaper, 3000);      // за книгата
        house.buyPaper(newspaperPaper, 2000); // за вестника
        house.buyPaper(posterPaper, 1020);    // за постера

        // Зареждане на хартията в машини
        try {
            house.loadPaperToMachine("C01", 4020); // цветната ще печата постера и книгата
            house.loadPaperToMachine("B01", 2000); // черно-бялата ще печата вестника
        } catch (Exception e) {
            System.err.println("Грешка при зареждане: " + e.getMessage());
        }

        // Създаване издания
        Publication book = new Publication("Библия", 20, 200, bookPaper, PublicationType.BOOK);
        Publication newspaper = new Publication("Телеграф", 100, 16, newspaperPaper, PublicationType.NEWSPAPER);
        Publication poster = new Publication("Постер за концерт", 50, 1, posterPaper, PublicationType.POSTER);

        // Печатене
        house.print(book, 20, false, "C01", 200.00);       // 4000 листа
        house.print(newspaper, 100, false, "B01", 100.00); // 1600 листа
        house.print(poster, 20, true, "C01", 50.00);      // 20 листа

        // Отчет
        System.out.println("\n------ ОТЧЕТ ------");
        System.out.println(house);

        System.out.println("\nОтпечатани страници:");
        System.out.println("C01: " + colorMachine.getTotalPagesPrinted());
        System.out.println("B01: " + bwMachine.getTotalPagesPrinted());

        // Сериализация
        house.saveEmployeesToFile("employees.dat");

        // Десериализация
        house.loadEmployeesFromFile("employees.dat");

        // Принтиране на служителите
        System.out.println("\n--- Заредени служители ---");
        for (Employee e : house.getEmployees()) {
            System.out.println(e);
        }
    }
}
