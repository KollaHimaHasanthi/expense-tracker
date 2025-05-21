import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class Transaction {
    private Date date;
    private String type; 
    private String category;
    private double amount;
    private String description;

    public Transaction(Date date, String type, String category, double amount, String description) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() { return date; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    public String toFileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%s,%s,%s,%.2f,%s", sdf.format(date), type, category, amount, description);
    }

    public static Transaction fromFileString(String line) throws Exception {
        String[] parts = line.split(",");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(parts[0]);
        String type = parts[1];
        String category = parts[2];
        double amount = Double.parseDouble(parts[3]);
        String description = parts[4];
        return new Transaction(date, type, category, amount, description);
    }
}

class ExpenseTracker {
    private List<Transaction> transactions;
    private String filePath;
    private static final String[] INCOME_CATEGORIES = {"Salary", "Business", "Investment", "Other"};
    private static final String[] EXPENSE_CATEGORIES = {"Food", "Rent", "Travel", "Utilities", "Other"};

    public ExpenseTracker(String filePath) {
        this.filePath = filePath;
        this.transactions = new ArrayList<>();
        loadFromFile();
    }

    public void addTransaction(Scanner scanner) {
        System.out.println("\nEnter transaction type (1 for Income, 2 for Expense): ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine();

        String type = (typeChoice == 1) ? "Income" : "Expense";
        String[] categories = (typeChoice == 1) ? INCOME_CATEGORIES : EXPENSE_CATEGORIES;

        System.out.println("Select category:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();
        String category = categories[categoryChoice - 1];

        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        System.out.println("Enter date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            date = new Date();
            System.out.println("Invalid date format, using current date.");
        }

        transactions.add(new Transaction(date, type, category, amount, description));
        saveToFile();
        System.out.println("Transaction added successfully!");
    }

    public void showMonthlySummary() {
        System.out.println("\nEnter year (YYYY): ");
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        System.out.println("Enter month (1-12): ");
        int month = scanner.nextInt();

        Calendar cal = Calendar.getInstance();
        Map<String, Double> incomeByCategory = new HashMap<>();
        Map<String, Double> expenseByCategory = new HashMap<>();
        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactions) {
            cal.setTime(t.getDate());
            if (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) + 1 == month) {
                if (t.getType().equals("Income")) {
                    incomeByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
                    totalIncome += t.getAmount();
                } else {
                    expenseByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
                    totalExpense += t.getAmount();
                }
            }
        }

        System.out.println("\nMonthly Summary for " + month + "/" + year);
        System.out.println("Income:");
        incomeByCategory.forEach((cat, amt) -> System.out.printf("  %s: $%.2f\n", cat, amt));
        System.out.printf("Total Income: $%.2f\n", totalIncome);
        System.out.println("\nExpenses:");
        expenseByCategory.forEach((cat, amt) -> System.out.printf("  %s: $%.2f\n", cat, amt));
        System.out.printf("Total Expenses: $%.2f\n", totalExpense);
        System.out.printf("Net Balance: $%.2f\n", totalIncome - totalExpense);
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(Transaction.fromFileString(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing data file found, starting fresh.");
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Transaction t : transactions) {
                writer.write(t.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker("transactions.txt");

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Monthly Summary");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                tracker.addTransaction(scanner);
            } else if (choice == 2) {
                tracker.showMonthlySummary();
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option, try again.");
            }
        }
        scanner.close();
    }
}