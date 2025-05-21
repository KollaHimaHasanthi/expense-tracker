# Expense Tracker

A Java-based command-line application for tracking income and expenses, with support for categorizing transactions, viewing monthly summaries, and saving/loading data from a file. This project was developed as part of the technical task for the Java Developer position at Alephys.

## Features
- Add income and expense transactions with predefined categories (e.g., Salary, Business for Income; Food, Rent, Travel for Expenses).
- View monthly summaries of income and expenses, grouped by category, with total income, total expenses, and net balance.
- Persist transaction data to a text file (`data/transactions.txt`) for permanent storage.
- Load existing transactions from the file on program startup.

## Project Structure
```
expense-tracker/
├── src/
│   └── Main.java
├── data/
│   └── transactions.txt
├── README.md
└── .gitignore
```

- `src/Main.java`: Contains the main program with `Transaction`, `ExpenseTracker`, and `Main` classes.
- `data/transactions.txt`: Stores transaction data in CSV format (sample data included).
- `README.md`: This documentation file.
- `.gitignore`: Excludes compiled files and IDE-specific files from version control.

## Requirements
- Java 8 or higher
- A text editor or IDE (e.g., IntelliJ IDEA, VS Code, Eclipse)
- Git (for cloning and version control)

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd expense-tracker
   ```
2. **Ensure Directory Structure**:
   - Verify that the `data/` directory exists and contains `transactions.txt`. A sample file is provided with example transactions.
3. **Compile and Run**:
   ```bash
   javac -d . src/Main.java
   java Main
   ```

## Usage
The program presents a command-line menu with the following options:
1. **Add Transaction**:
   - Select transaction type (1 for Income, 2 for Expense).
   - Choose a category (e.g., Income: Salary, Business, Investment, Other; Expense: Food, Rent, Travel, Utilities, Other).
   - Enter amount, description, and date (format: `yyyy-MM-dd`).
   - Invalid dates default to the current date.
   - Transactions are saved to `data/transactions.txt`.
2. **View Monthly Summary**:
   - Enter a year (e.g., 2025) and month (1-12) to see a summary of income and expenses by category, total income, total expenses, and net balance.
3. **Exit**:
   - Closes the application.

### File Format (`data/transactions.txt`)
- Transactions are stored in CSV format: `yyyy-MM-dd,Type,Category,Amount,Description`.
- Example:
  ```
  2025-05-01,Income,Salary,5000.00,Monthly salary
  2025-05-02,Expense,Food,50.00,Groceries
  2025-05-03,Income,Business,2000.00,Freelance project
  2025-05-04,Expense,Travel,100.00,Taxi fare
  ```

## Sample Output
```
Expense Tracker Menu:
1. Add Transaction
2. View Monthly Summary
3. Exit
Choose an option: 1

Enter transaction type (1 for Income, 2 for Expense): 1
Select category:
1. Salary
2. Business
3. Investment
4. Other
1
Enter amount: 3000.00
Enter description: Bonus payment
Enter date (yyyy-MM-dd): 2025-05-05
Transaction added successfully!

Expense Tracker Menu:
1. Add Transaction
2. View Monthly Summary
3. Exit
Choose an option: 2

Enter year (YYYY): 2025
Enter month (1-12): 5

Monthly Summary for 5/2025
Income:
  Salary: $5000.00
  Business: $2000.00
  Salary: $3000.00
Total Income: $10000.00

Expenses:
  Food: $50.00
  Travel: $100.00
Total Expenses: $150.00
Net Balance: $9850.00
```

## Notes
- The program uses a text file (`data/transactions.txt`) for data persistence, which is both readable and writable by the program.
- File operations include error handling for cases like missing files or permission issues.
- Categories are predefined to simplify input, but the program supports extensibility (e.g., adding new categories).
- Invalid inputs (e.g., date format) are handled gracefully with fallback to the current date.

## Submission Details
- **Source Code**: Available in `src/Main.java`.
- **Sample Data**: Provided in `data/transactions.txt` with example transactions.
- **Screenshots**: Screenshots of the console output (e.g., adding transactions and viewing summaries) are recommended for submission. Run the program, add transactions, and capture the output.
- **Git Repository**: The project is hosted in a Git repository for easy access and review.

## Troubleshooting
- **File Not Found**: Ensure `data/transactions.txt` exists or is writable in the `data/` directory.
- **Permission Issues**: Run the program in a directory with write permissions (e.g., your home directory).
- **Compilation Errors**: Verify Java 8 or higher is installed (`java -version`).

For any questions or clarifications, please contact the Alephys hiring team.