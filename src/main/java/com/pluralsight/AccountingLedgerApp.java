package com.pluralsight;

// 1:23:16

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;

public class AccountingLedgerApp {

    public final static String dataFileName = "transactions.csv";
    public static ArrayList<Transactions> transactions = getTransactions();
//    public static LocalDateTime now = LocalDateTime.now();

    public static void main(String[] args) {

        accountingLedgerHomeScreen();
//        displayLedgerAllEntries();

    }

    // This Method gets all the transactions from the transactions.csv
    public static ArrayList<Transactions> getTransactions(){
        ArrayList<Transactions> transactions = new ArrayList<Transactions>();
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
                Transactions t = new Transactions(transactionDate, transactionTime, transactionDescription, transactionVendor, transactionAmount);
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
    public static void saveTransactions(){

        try{
            FileWriter fw = new FileWriter(dataFileName);

            fw.write("date|time|description|vendor|amount \n");

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            for(Transactions t : transactions){
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
                System.out.println("--".repeat(30));
                System.out.println("WELCOME TO ACCOUNTING LEDGER APPLICATION!");
                System.out.println("Please select from the following:");
                System.out.println("   D - Add Deposit");
                System.out.println("   P - Make Payment (Debit)");
                System.out.println("   L - Check Ledger");
                System.out.println("   E - Exit");
                System.out.print("Command: ");

                String option = Console.PromptForString();

                switch(option){
                    case "D":
                    case "d":
                        addDeposit();
                        break;
                    case "P":
                    case "p":
                        makePayment();
                        break;
                    case "L":
                    case "l":
                        displayLedgerMenu();
                        break;
                    case "E":
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
     * From home screen --> Method to add a deposit. (prompt the user for deposit information and save it to transactions.csv)
     * Gives option to go back to home screen or exit.
     */
    public static void addDeposit(){
        String depositDescription = Console.PromptForString("Enter description of the deposit: ");
        String depositVendor = Console.PromptForString("Enter the name of the vendor: ");
        double depositAmount = Console.PromptForDouble("Enter the amount you want to deposit:  ");
        LocalDate depositDate = LocalDate.now();
        LocalTime depositTime = LocalTime.now();


        Transactions t = new Transactions(depositDate, depositTime,
                depositDescription, depositVendor, depositAmount);
        transactions.add(t);
        saveTransactions();

    }

    /**
     * From home screen --> Method to make a payment. (prompt the user for debit information and save it to transactions.csv)
     * Gives option to go back to home screen or exit.
     */
    public static void makePayment(){
        String paymentDescription = Console.PromptForString("Enter description of the payment: ");
        String paymentVendor = Console.PromptForString("Enter the name of the vendor: ");
        double paymentAmount = Console.PromptForDouble("Enter the amount you want to pay:  ");
        LocalDate paymentDate = LocalDate.now();
        LocalTime paymentTime = LocalTime.now();

        paymentAmount = paymentAmount * -1;

        Transactions t = new Transactions(paymentDate, paymentTime,
                paymentDescription, paymentVendor, paymentAmount);
        transactions.add(t);
        saveTransactions();
    }


    /**
     * From home screen --> second level menu - the ledger screen. User can select a few options:
     * A (From ledger screen --> Verify which user, then display All entries)
     * D (From ledger screen --> Verify which user, then display only positive or "Deposit" entries)
     * P (From ledger screen --> Verify which user, then display only negative or "Payment" entries)
     * R (From ledger screen --> third level menu - the "reports" screen. Users can run predefined reports or custom vendor search)
     * H (return to Home screen)
     */
    public static void displayLedgerMenu(){
        do {
            try{
                System.out.println();
                System.out.println("--".repeat(30));
                System.out.println("LEDGER SCREEN");
                System.out.println("Please select from the following:");
                System.out.println("   A - Display All Entries");
                System.out.println("   D - Display Deposit Entries");
                System.out.println("   P - Display Payment Entries");
                System.out.println("   R - Check Reports");
                System.out.println("   H - Return to Home Menu");
                System.out.print("Command: ");

                String option = Console.PromptForString();

                switch(option){
                    case "A":
                    case "a":
                        displayLedgerAllEntries();
                        break;
                    case "D":
                    case "d":
                        displayLedgerDeposits();
                        break;
                    case "P":
                    case "p":
                        displayLedgerPayments();
                        break;
                    case "R":
                    case "r":
                        displayReportsMenu();
                        break;
                    case "H":
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

    // * A (From ledger screen --> Verify which user, then display All entries)
    public static void displayLedgerAllEntries(){
        System.out.println("All Entries Are Being Displayed: ");
        for (int i = 0; i < transactions.size(); i++){
        // todo how to reverse it by date
      //for (int i = transactions.size() -1 ; i >= 0; i--){
        Transactions t = transactions.get(i);
        System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
        }
    }

    // * D (From ledger screen --> Verify which user, then display only positive or "Deposit" entries)
    public static void displayLedgerDeposits(){
        System.out.println("Ledger Deposits Are Being Displayed: ");
        // todo how to reverse it by date
        //for (int i = transactions.size() -1 ; i >= 0; i--){
        for (int i = 0; i < transactions.size(); i++){
            Transactions t = transactions.get(i);
            if (t.getAmount() > 0){
                System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
            }
//            else if (t.getAmount() == 0){
//                System.out.println("Deposits of $0 Made!");
//                System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
//            }
        }
    }

    // * P (From ledger screen --> Verify which user, then display only negative or "Payment" entries)
    public static void displayLedgerPayments(){
        System.out.println("Ledger Payments Are Being Displayed: ");
        for (int i = 0; i < transactions.size(); i++){
            Transactions t = transactions.get(i);
            if (t.getAmount() < 0){
                System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
            }
//            if (t.getAmount() == 0){
//                System.out.println("Payments of $0 Made!");
//                System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
//            }
        }
    }


    /**
     * R -- From ledger screen --> third level menu - the "reports" screen. User can select from a few options or search by vendor:
     *
     * 1 (Month to Date)
     * 2 (Previous Month)
     * 3 (Year to Date)
     * 4 (Previous Year)
     * 5 (Search by Vendor)
     * 0 (Back to ledger screen)
     *
     * H (go back to home screen)
     */
    public static void displayReportsMenu(){
        do {
            try{
                System.out.println();
                System.out.println("--".repeat(30));
                System.out.println("REPORTS SCREEN");
                System.out.println("Please select from the following:");
                System.out.println("   1 - Month to Date");
                System.out.println("   2 - Previous Month");
                System.out.println("   3 - Year to Date");
                System.out.println("   4 - Previous Year");
                System.out.println("   5 - Search by Vendor");
                System.out.println("   0 - Back to Ledger Screen");
                System.out.print("Command: ");

                int option = Console.PromptForInt();

                switch(option){
                    case 1:
                        displayMonthToDate();
                        break;
                    case 2:
                        displayPreviousMonth();
                        break;
                    case 3:
                        displayYearToDate();
                        break;
                    case 4:
                        displayPreviousYear();
                        break;
                    case 5:
                        displaySearchByVendor();
                        break;
                    case 0:
                        displayLedgerMenu();
                    default:
                        System.out.println("Invalid Number, please try again.");

                }
            } catch (Exception e){
                System.out.println("ERROR, please try again with valid Report Menu commands.");
            }
        } while(true);
    }

    // * 1 (Month to Date)
    public static void displayMonthToDate(){
        System.out.println("Displaying Beginning of Current Month to Current Date: ");

        for (int i = 0; i < transactions.size(); i++) {
            Transactions t = transactions.get(i);
            if (t.getDate().getMonth() == LocalDate.now().getMonth() && t.getDate().getYear() == LocalDate.now().getYear()) {
                System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
            }
        }
    }

    // * 2 (Previous Month)
    public static void displayPreviousMonth(){
        System.out.println("Displaying Previous Month: ");

        for (int i = 0; i < transactions.size(); i++) {
            Transactions t = transactions.get(i);
            if (t.getDate().getMonthValue() == LocalDate.now().getMonthValue()-1) {
                System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
            }
        }

    }

    // * 3 (Year to Date)
    public static void displayYearToDate(){
        System.out.println("Displaying Beginning of Current Year to Current Date: ");

        for (int i = 0; i < transactions.size(); i++) {
            Transactions t = transactions.get(i);
            if (t.getDate().getYear() == LocalDate.now().getYear()) {
                System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
            }
        }
    }

    // * 4 (Previous Year)
    public static void displayPreviousYear(){
        System.out.println("Displaying Previous Year: ");

        for (int i = 0; i < transactions.size(); i++) {
            Transactions t = transactions.get(i);
            if (t.getDate().getYear() == LocalDate.now().getYear()-1) {
                System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
            }
        }
    }

    // * 5 (Search by Vendor)
    public static void displaySearchByVendor(){
        String vendor = Console.PromptForString("Search By Vendor: ");

        for (int i = 0; i < transactions.size(); i++) {
            Transactions t = transactions.get(i);
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
            }
        }
    }




}