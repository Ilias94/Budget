import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TransactionApp {
    private static final TransactionDAO transactionDAO = new TransactionDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("Wybierz opcję:");
            System.out.println("1. Dodaj transakcję");
            System.out.println("2. Modyfikacja transakcji");
            System.out.println("3. Usuwanie transkacji");
            System.out.println("4. Wyświetlanie wszystkich przychodów");
            System.out.println("5. Wyświetlanie wszystkich wydatków");
            System.out.println("0. Zakończenie programu");
            String option = scanner.nextLine();
            switch (option) {
                case "1" -> {
                    Transaction newTransaction = creatTransaction();
                    transactionDAO.add(newTransaction);
                    System.out.println("transakcja dodana");
                }
                case "2" -> {
                    Transaction updatedTransaction = updateTransaction();
                    transactionDAO.update(updatedTransaction);
                    System.out.println("transakcja zaktualizowana");
                }
                case "3" -> {
                    int id = idToDeleteTransaction();
                    transactionDAO.deleteById(id);
                    System.out.println("transakcja o numerze id: " + id + " została usunięta");
                }
                case "4" -> transactionDAO.displayAllIncome();
                case "5" -> transactionDAO.displayAllExpenses();
                case "0" -> {
                    System.out.println("Koniec programu");
                    return;
                }
            }
        }
    }

    private static int idToDeleteTransaction() {
        System.out.println("Podaj id transakcji którą chcesz usunąć.");
        return scanner.nextInt();
    }

    private static Transaction updateTransaction() {
        System.out.println("Podaj id transkacji do zmodyfikowania");
        int id = scanner.nextInt();
        scanner.nextLine();
        Transaction transaction = creatTransaction();
        transaction.setId(id);
        return transaction;
    }

    private static Transaction creatTransaction() {

        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("Podaj typ transakcji.");
        String type = scanner.nextLine();
        System.out.println("Podaj opis transakcji");
        String description = scanner.nextLine();
        System.out.println("Podaj kwotę");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj datę (dd-MM-yyy)");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, datePattern);
        Transaction transaction = new Transaction(type, description, amount, date);
        return transaction;

    }
}
