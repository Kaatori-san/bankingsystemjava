package OnlineBankingSystem;

import java.util.HashMap;
import java.util.Scanner;

public class Finals {
    final private static HashMap<String, Account> accounts = new HashMap<>();
    final private static Scanner scanner = new Scanner(System.in);
    private static Account currentAccount;

    public static void initializeAccounts() {
        // FOR TESTING ONLY
        accounts.put("user1", new Account(
            "user1",               // Username
            "John Doe",            // Full name
            "01-01-1990",          // Birthday
            "City A",              // Birthplace
            "Address 1",           // Address
            "123-456-7890",        // Mobile number
            "Savings",             // Account type
            "pass123",             // Password
            1000.00,               // Initial balance
            "Blue",                // Favorite color (new field)
            "City A"               // Security answer (birthplace)
        ));
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
    public static void waitForUserInput(){
        System.out.print("Press enter to continue...");
        String waitInput = scanner.nextLine();
    }
    public static void start() {
        boolean loggedIn = false;

        while (!loggedIn) {
            clearScreen();
            System.out.println("\n----------------------------------------");
            System.out.println("Welcome to DigiVault!");
            System.out.println("Program by: Yellow Group of BSITWMA-AW21");
            System.out.println("----------------------------------------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit System");
            System.out.println("----------------------------------------");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    clearScreen();
                    loggedIn = login();
                    break;
                case 2:
                    clearScreen();
                    register();
                    break;
                case 3:
                    clearScreen();
                    forgotPassword();
                    break;
                case 4:
                    clearScreen();
                    System.out.println("Exiting the system. Thank you for using DigiVault!");
                    wait(1000);
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    wait(2000);
            }
        }

        mainMenu();
    }

    public static boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Account account = accounts.get(username);
        if (account != null && account.checkPassword(password)) {
            System.out.println("Login successful!");
            currentAccount = account;
            wait(2000);
            return true;
        } else {
            System.out.println("Invalid username or password.");
            wait(2000);
            return false;
        }
    }

    public static void register() {
        System.out.print("Enter a new username: ");
        String username = scanner.nextLine();

        if (accounts.containsKey(username)) {
            System.out.println("Username already exists. Please try a different one.");
            wait(2000);
            return;
        }

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter birthday (e.g., DD-MM-YYYY): ");
        String birthday = scanner.nextLine();

        System.out.print("Enter birthplace: ");
        String birthplace = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter mobile number: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Choose account type (Savings or Current): ");
        String accountType = scanner.nextLine();

        String password = promptForPassword();

        // Ask security questions
        System.out.println("FOR SECURITY QUESTIONS: ");
        System.out.print("What is your favorite color? ");
        String favoriteColor = scanner.nextLine();

        System.out.print("What is the name of your birthplace? ");
        String securityAnswer = scanner.nextLine();

        System.out.print("Enter initial deposit amount: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        if (balance < 0) {
            System.out.println("Initial deposit cannot be negative. Registration failed.");
            wait(2000);
            return;
        }

        Account newAccount = new Account(username, fullName, birthday, birthplace, address, mobileNumber, accountType, password, balance, favoriteColor, securityAnswer);
        accounts.put(username, newAccount);
        clearScreen();
        System.out.println("Registration successful! Here are your account details:");
        System.out.println("Username: " + username);
        System.out.println("Account Number: " + newAccount.getAccountNumber());
        System.out.println("Account Type: " + newAccount.getAccountType());

        System.out.print("Going back to account menu in: ");
        wait(1000);
        System.out.print("3 ");
        wait(1000);
        System.out.print("2 ");
        wait(1000);
        System.out.print("1");
        wait(1000);
    }

    // Method to prompt for password with asterisks (masked input)
    public static String promptForPassword() {
        System.out.print("Enter a new password: ");
        char[] password = new char[20];
        int i = 0;
        while (true) {
            try {
                char ch = (char) System.in.read();
                if (ch == '\n') break;
                if (ch != '\r') {
                    password[i++] = ch;
                    System.out.print('*');
                }
            } catch (Exception e) {
                break;
            }
        }
        return new String(password, 0, i);
    }

    // Forgot password feature
    public static void forgotPassword() {
        System.out.print("Enter username to reset password: ");
        String username = scanner.nextLine();
        Account account = accounts.get(username);

        if (account == null) {
            System.out.println("Username not found.");
            wait(2000);
            return;
        }

        System.out.print("What is your favorite color? ");
        String colorAnswer = scanner.nextLine();
        System.out.print("What is the name of your birthplace? ");
        String birthplaceAnswer = scanner.nextLine();

        if (account.getFavoriteColor().equalsIgnoreCase(colorAnswer) && account.getSecurityAnswer().equalsIgnoreCase(birthplaceAnswer)) {
            System.out.print("Security answers are correct. Enter a new password: ");
            String newPassword = scanner.nextLine();
            account.setPassword(newPassword);
            System.out.println("Password has been successfully reset.");
            wait(2000);
        } else {
            System.out.println("Incorrect answers to security questions.");
            wait(2000);
        }
    }

    public static void mainMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n----------------------------------------");
            System.out.println("Main Menu:");
            System.out.println("----------------------------------------");
            System.out.println("1. Show Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. Pay Bills");
            System.out.println("6. Logout");
            System.out.println("----------------------------------------");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline

            switch (choice) {
                case 1:
                    showBalance();
                    break;
                case 2: // Deposit
                    clearScreen();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    currentAccount.deposit(depositAmount, true); 
                    break;
                case 3: // Withdraw
                    clearScreen();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    currentAccount.withdraw(withdrawalAmount);
                    waitForUserInput(); 
                    break;
                case 4:
                    clearScreen();
                    transferMoney();
                    break;
                case 5:
                    clearScreen();
                    payBills();
                    break;
                case 6:
                    clearScreen();
                    System.out.println("Logging out... Thank you for using DigiVault!");
                    currentAccount = null;
                    start(); // Return to login screen after logging out
                    return;  // Exit main menu loop after logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void transferMoney() {
        System.out.println("Choose transfer type:");
        System.out.println("1. Same Bank (No Transfer Fee)");
        System.out.println("2. Other Bank (20 pesos Transfer Fee)");
        System.out.print("Enter choice: ");
        int transferType = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline

        double transferFee = 0;
        if (transferType == 2) {
            transferFee = 20; // Fee for transferring to another bank
        } else if (transferType != 1) {
            System.out.println("Invalid choice. Transfer canceled.");
            wait(2000);
            return;
        }

        // Proceed to the transferring process
        System.out.print("Enter recipient account number: ");
        String recipientAccountNumber = scanner.nextLine();

        // Find recipient account by account number
        Account recipient = null;
        for (Account account : accounts.values()) {
            if (account.getAccountNumber().equals(recipientAccountNumber)) {
                recipient = account;
                break;
            }
        }

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            wait(2000);
            return;
        }

        // Prevent transferring to the current account
        if (recipient.equals(currentAccount)) {
            System.out.println("Error: Cannot transfer to your own account.");
            wait(2000);
            return;
        }

        System.out.print("Enter transfer amount: ");
        double transferAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume leftover newline

        double totalAmount = transferAmount + transferFee;

        if (transferAmount <= 0 || totalAmount > currentAccount.getBalance()) {
            System.out.println("Invalid amount or insufficient balance.");
            wait(2000);
            return;
        }

        // Display transfer preview
        System.out.printf("\nTransfer Preview:\nRecipient Username: %s\nTransfer Amount: %.2f\nTransfer Fee: %.2f\nTotal Amount: %.2f\n\nCurrent Balance: %.2f\n",
                recipient.getUsername(), transferAmount, transferFee, totalAmount, currentAccount.getBalance());

        System.out.print("Confirm transfer? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            currentAccount.withdraw(totalAmount);
            recipient.deposit(transferAmount, false);  // Deposit without showing balance
            System.out.println("Transfer successful!");
            wait(2000);
        } else {
            System.out.println("Transfer canceled.");
            wait(2000);
        }
    }

    private static void payBills() {
    // Available bill categories
    System.out.println("Available bill categories:");
    System.out.println("1. Electricity");
    System.out.println("2. Water");
    System.out.println("3. Internet");
    System.out.print("Enter bill category number: ");
    int billCategory = scanner.nextInt();
    scanner.nextLine(); // Consume leftover newline

    // Ask for the biller's name
    System.out.print("Enter biller's name: ");
    String billerName = scanner.nextLine();

    // Ask for the bill amount
    System.out.print("Enter bill amount: ");
    double billAmount = scanner.nextDouble();
    scanner.nextLine(); 

    if (billAmount <= 0) {
        System.out.println("Invalid bill amount.");
        wait(2000);
        return;
    }

    if (billAmount > currentAccount.getBalance()) {
        System.out.println("Insufficient funds to pay this bill.");
        wait(2000);
        return;
    }

    // Display bill payment preview
    System.out.printf("\nBill Payment Preview:\nBiller Name: %s\nBill Category: %s\nBill Amount: %.2f\n\nCurrent Balance: %.2f\n",
            billerName, getBillCategoryName(billCategory), billAmount, currentAccount.getBalance());

    // Ask for confirmation before paying the bill
    System.out.print("Confirm payment? (yes/no): ");
    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("yes")) {
        currentAccount.withdraw(billAmount);
        System.out.println("Bill payment successful!");
        wait(2000);
    } else {
        System.out.println("Bill payment canceled.");
        wait(2000);
    }
}

// Helper method to get the bill category name
private static String getBillCategoryName(int category) {
    return switch (category) {
        case 1 -> "Electricity";
        case 2 -> "Water";
        case 3 -> "Internet";
        default -> "Unknown";
    };
}

    private static void showBalance() {
    clearScreen();
    System.out.println("Account Balance");
    System.out.println("Your current balance is: " + currentAccount.getBalance());

    // Calculate balance after 1 month with 0.1250% interest
    double interestRate = 0.00125;
    double balanceAfterInterest = currentAccount.getBalance() * (1 + interestRate);

    System.out.printf("Your balance after 1 month (with 0.1250%% interest): %.2f%n", balanceAfterInterest);

    waitForUserInput();
}

}
