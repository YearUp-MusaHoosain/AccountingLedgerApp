package com.pluralsight;

// 1:23:16

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class AccountingLedgerApp {

    public final static String dataFileName = "transactions.csv";
    public static ArrayList<Transaction> transactions = getTransactions();


    public static void main(String[] args) {

        accountingLedgerHomeScreen();

    }

    // This Method gets all the transactions from the transactions.csv
    public static ArrayList<Transaction> getTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        try{
            FileReader fr = new FileReader(dataFileName);
            BufferedReader br = new BufferedReader(fr);

            br.readLine();

            String input;
            while( (input = br.readLine()) != null){
                String[] tokens = input.split(Pattern.quote("|"));
                LocalDate transactionDate = LocalDate.parse(tokens[0]);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime transactionTime = LocalTime.parse(tokens[1], formatter);
                String transactionDescription = tokens[2];
                String transactionVendor = tokens[3];
                double transactionAmount = Double.parseDouble(tokens[4]);
                Transaction t = new Transaction(transactionDate, transactionTime, transactionDescription, transactionVendor, transactionAmount);
                transactions.add(t);
            }
            br.close();
        }
        catch (Exception e){
            System.out.println("ERROR!!");
            e.printStackTrace();
        }
        return transactions;
    }

    // This Method writes transactions (and info) to the transactions.csv
    public static void writeTransactions(){

        try{
            FileWriter fw = new FileWriter(dataFileName);

            fw.write("date|time|description|vendor|amount \n");

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            for(Transaction t : transactions){
                String formattedTime = t.getTime().format(timeFormatter);
                String data = t.getDate() + "|" + formattedTime + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount() + "\n";
                fw.write(data);
            }

            fw.close();
        } catch (Exception e) {
            System.out.println("FILE WRITE ERROR");
            e.printStackTrace();
        }

    }


    /**
     * home screen (top level) menu. user can select a few options:
     * D (Add Deposit), P (Make Payment), L (Ledger), X (Exit)
     */
    public static void accountingLedgerHomeScreen() {
        do {
            try{
                System.out.println();
                System.out.println("--".repeat(55));
                System.out.println("++++++++ WELCOME TO ACCOUNTING LEDGER APPLICATION! ++++++++");
                System.out.println("++++++++++++ Please select from the following: ++++++++++++");
                System.out.println("+++                                                     +++");
                System.out.println("+++   D - Add Deposit                                   +++");
                System.out.println("+++   P - Make Payment (Debit)                          +++");
                System.out.println("+++   L - Check Ledger                                  +++");
                System.out.println("+++   E - Exit                                          +++");
                System.out.println("+++                                                     +++");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println();
                System.out.print("+++ (Home Screen) Choose a Command: ");

                String option = Console.PromptForString();

                switch(option){
                    case "D": // D or d (Add Deposit)
                    case "d":
                        addDeposit();
                        break;
                    case "P": // P or p (Make Payment)
                    case "p":
                        makePayment();
                        break;
                    case "L": // L or l (Ledger)
                    case "l":
                        displayLedgerMenu();
                        break;
                    case "E": // X or x (Exit)
                    case "e":
                        return;
                    default:
                        System.out.println("Invalid Letter, please try again.");

                }
            } catch (Exception e){
                System.out.println("ERROR, please try again with valid Home Menu commands.");
            }
        } while(true);

    }

    /**
     * From home screen --> Method to add a deposit.
     * Gives option to go back to home screen or exit.
     *
     * (prompt the user for deposit information and save it to transactions.csv)
     */
    public static void addDeposit(){
        //add deposit requires:  description, vendor, amount, date, time

        //description
        String depositDescription = Console.PromptForString("Enter description of the deposit: ");
        //vendor
        String depositVendor = Console.PromptForString("Enter the name of the vendor: ");
        //deposit amount
        double depositAmount = Console.PromptForDouble("Enter the amount you want to deposit:  ");
        //date + time
        LocalDate depositDate = LocalDate.now();
        LocalTime depositTime = LocalTime.now();

        System.out.println(Transaction.toHeaderString());
        //make a new transaction instance
        Transaction t = new Transaction(depositDate, depositTime,
                depositDescription, depositVendor, depositAmount);
        //add it to transactions
        transactions.add(t);
        //write it to the existing transactions.csv file
        writeTransactions();
        //print out t, to let the user know the deposit occurred
        System.out.println(t.toString());
        System.out.println("\n Deposit Successfully Completed!");

    }

    /**
     * From home screen --> Method to make a payment.
     * Gives option to go back to home screen or exit.
     *
     * (prompt the user for debit information and save it to transactions.csv)
     */
    public static void makePayment(){
        //add payment requires:  description, vendor, amount, date, time

        //description
        String paymentDescription = Console.PromptForString("Enter description of the payment: ");
        //vendor
        String paymentVendor = Console.PromptForString("Enter the name of the vendor: ");
        //payment amount
        double paymentAmount = Console.PromptForDouble("Enter the amount you want to pay:  ");
        //date + time
        LocalDate paymentDate = LocalDate.now();
        LocalTime paymentTime = LocalTime.now();

        //make paymentAmount equal to negative values
        paymentAmount = paymentAmount * -1;

        System.out.println(Transaction.toHeaderString());
        //make a new transaction instance
        Transaction t = new Transaction(paymentDate, paymentTime,
                paymentDescription, paymentVendor, paymentAmount);
        //add it to transactions
        transactions.add(t);
        //write it to the existing transactions.csv file
        writeTransactions();
        //print out t, to let the user know the deposit occurred
        System.out.println(t.toString());
        System.out.println("\n Payment Successfully Completed!");
    }


    /**
     * From home screen --> second level menu - the ledger screen. User can select a few options:
     * A (From ledger screen --> display All entries)
     * D (From ledger screen --> display only positive or "Deposit" entries)
     * P (From ledger screen --> display only negative or "Payment" entries)
     * R (From ledger screen --> third level menu - the "reports" screen. Users can run predefined reports or custom vendor search)
     * H (From ledger screen --> return to Home screen)
     */
    public static void displayLedgerMenu(){
        do {
            try{
                System.out.println();
                System.out.println("--".repeat(55));
                System.out.println("++++++++++++++++++++++ LEDGER SCREEN ++++++++++++++++++++++");
                System.out.println("++++++++++++ Please select from the following: ++++++++++++");
                System.out.println("+++                                                     +++");
                System.out.println("+++   A - Display All Entries                           +++");
                System.out.println("+++   D - Display Deposit Entries                       +++");
                System.out.println("+++   P - Display Payment Entries                       +++");
                System.out.println("+++   R - Check Reports                                 +++");
                System.out.println("+++   H - Return to Home Menu                           +++");
                System.out.println("+++                                                     +++");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println();
                System.out.print("+++ (Ledger Screen) Choose a Command: ");

                String option = Console.PromptForString();

                switch(option){
                    case "A": // A or a (From ledger screen --> display All entries)
                    case "a":
                        displayLedgerAllEntries();
                        break;
                    case "D": // D or d (From ledger screen --> display only positive or "Deposit" entries)
                    case "d":
                        displayLedgerDeposits();
                        break;
                    case "P": // P or p (From ledger screen --> display only negative or "Payment" entries)
                    case "p":
                        displayLedgerPayments();
                        break;
                    case "R": // R or r (From ledger screen --> third level menu - the "reports" screen. Users can run predefined reports or custom vendor search)
                    case "r":
                        displayReportsMenu();
                        break;
                    case "H": // H or h (From ledger screen --> return to Home screen)
                    case "h":
                       return;
                    default:
                        System.out.println("Invalid Letter, please try again.");

                }
            } catch (Exception e){
                System.out.println("ERROR, please try again with valid Ledger Menu commands.");
            }
        } while(true);
    }

    // * A (From ledger screen --> display All entries)
    public static void displayLedgerAllEntries(){
        System.out.println("All Entries Are Being Displayed: ");
        System.out.println(Transaction.toHeaderString());

        Comparator<Transaction> comparator = Comparator.comparing(Transaction::getDate).reversed();
        transactions.sort(comparator);
        // loops through the length of transactions and returns all of them
        for (int i = 0; i < transactions.size(); i++){
        Transaction t = transactions.get(i);
        // prints all the transactions
        System.out.println(t.toString());
        }
    }

    // * D (From ledger screen --> display only positive or "Deposit" entries)
    public static void displayLedgerDeposits(){
        System.out.println("Ledger Deposits Are Being Displayed: ");
        System.out.println(Transaction.toHeaderString());
        Comparator<Transaction> comparator = Comparator.comparing(Transaction::getDate).reversed();
        transactions.sort(comparator);

        // loops through the length of transactions and returns all of them
        for (int i = 0; i < transactions.size(); i++){
            Transaction t = transactions.get(i);
            // if the amount is greater than 0, it prints all the "great than zero" transactions
            if (t.getAmount() > 0){
                System.out.println((t.toString()));
            }
        }
    }


    // * P (From ledger screen --> display only negative or "Payment" entries)
    public static void displayLedgerPayments(){
        System.out.println("Ledger Payments Are Being Displayed: ");
        System.out.println(Transaction.toHeaderString());
        Comparator<Transaction> comparator = Comparator.comparing(Transaction::getDate).reversed();
        transactions.sort(comparator);

        // loops through the length of transactions and returns all of them
        for (int i = 0; i < transactions.size(); i++){
            Transaction t = transactions.get(i);
            // if the amount is less than 0, it prints all the "less than zero" transactions
            if (t.getAmount() < 0){
                System.out.println((t.toString()));
            }
        }
    }


    /**
     * R -- From ledger screen --> third level menu - the "reports" screen. User can select from a few options or search by vendor:
     *
     * 1 (From reports screen --> Display reports for Current Month to Current Date)
     * 2 (From reports screen --> Display reports for Previous Month)
     * 3 (From reports screen --> Display reports for Current Year to Current Date)
     * 4 (From reports screen --> Display reports for Previous Year)
     * 5 (From reports screen --> Display reports that are Searched by Vendor)
     * 0 (From reports screen --> Back to ledger screen)
     */
    public static void displayReportsMenu(){
        do {
            try{
                System.out.println();
                System.out.println("--".repeat(55));
                System.out.println("+++++++++++++++++++++ REPORTS SCREEN ++++++++++++++++++++++");
                System.out.println("+++++++++++++Please select from the following:+++++++++++++");
                System.out.println("+++                                                     +++");
                System.out.println("+++   1 - Month to Date                                 +++");
                System.out.println("+++   2 - Previous Month                                +++");
                System.out.println("+++   3 - Year to Date                                  +++");
                System.out.println("+++   4 - Previous Year                                 +++");
                System.out.println("+++   5 - Search by Vendor                              +++");
                System.out.println("+++   0 - Back to Ledger Screen                         +++");
                System.out.println("+++                                                     +++");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println();
                System.out.print("+++ (Reports Screen) Choose a Command: ");

                int option = Console.PromptForInt();

                switch(option){
                    case 1: // 1 (From reports screen --> Display reports for Current Month to Current Date)
                        displayMonthToDate();
                        break;
                    case 2: // 2 (From reports screen --> Display reports for Previous Month)
                        displayPreviousMonth();
                        break;
                    case 3: // 3 (From reports screen --> Display reports for Current Year to Current Date)
                        displayYearToDate();
                        break;
                    case 4: // 4 (From reports screen --> Display reports for Previous Year)
                        displayPreviousYear();
                        break;
                    case 5: // 5 (From reports screen --> Display reports that are Searched by Vendor)
                        displaySearchByVendor();
                        break;
                    case 0: // 0 (From reports screen --> Back to ledger screen)
                        return;
                    default:
                        System.out.println("Invalid Number, please try again.");

                }
            } catch (Exception e){
                System.out.println("ERROR, please try again with valid Report Menu commands.");
            }
        } while(true);
    }

    // * 1 (From reports screen --> Display reports for Current Month to Current Date)
    public static void displayMonthToDate(){
        System.out.println("Displaying Beginning of Current Month to Current Date: ");
        System.out.println(Transaction.toHeaderString());

        // loops through the length of transactions and returns all of them
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            // if the transaction month and year are equal to the current month and year, it will return those transactions until current date and time.
            if (t.getDate().getMonth() == LocalDate.now().getMonth() && t.getDate().getYear() == LocalDate.now().getYear()) {
                System.out.println(t.toString());
            }
        }
    }

    // * 2 (From reports screen --> Display reports for Previous Month)
    public static void displayPreviousMonth(){
        System.out.println("Displaying Previous Month: ");
        System.out.println(Transaction.toHeaderString());

        // loops through the length of transactions and returns all of them
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            // if the transaction month is equal to the previous month, it will return those transactions until current date and time.
            if (t.getDate().getMonthValue() == LocalDate.now().getMonthValue()-1) {
                System.out.println(t.toString());
            }
        }

    }

    // * 3 (From reports screen --> Display reports for Current Year to Current Date)
    public static void displayYearToDate(){
        System.out.println("Displaying Beginning of Current Year to Current Date: ");
        System.out.println(Transaction.toHeaderString());

        // loops through the length of transactions and returns all of them
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            // if the transaction year is equal to the current year, it will return those transactions until current date and time.
            if (t.getDate().getYear() == LocalDate.now().getYear()) {
                System.out.println(t.toString());
            }
        }
    }

    // * 4 (From reports screen --> Display reports for Previous Year)
    public static void displayPreviousYear(){
        System.out.println("Displaying Previous Year: ");
        System.out.println(Transaction.toHeaderString());

        // loops through the length of transactions and returns all of them
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            // if the transaction year is equal to the previous year, it will return those transactions until current date and time.
            if (t.getDate().getYear() == LocalDate.now().getYear()-1) {
                System.out.println(t.toString());
            }
        }
    }

    // * 5 (From reports screen --> Display reports that are Searched by Vendor)
    public static void displaySearchByVendor(){
        String vendor = Console.PromptForString("Search By Vendor: ");
        System.out.println(Transaction.toHeaderString());

        // loops through the length of transactions and returns all of them
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            // if the transaction vendor is equal vendor from user's input, it will return all transactions from that vendor.
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(t.toString());
            }
        }
    }

}