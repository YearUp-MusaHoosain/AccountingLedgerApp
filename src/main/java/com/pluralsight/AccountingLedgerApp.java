package com.pluralsight;

// 1:23:16

public class AccountingLedgerApp {

    public static void main(String[] args) {

        accountingLedgerHomeScreen();

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
        System.out.println("Add Deposit");

    }

    /**
     * From home screen --> Method to make a payment. (prompt the user for debit information and save it to transactions.csv)
     * Gives option to go back to home screen or exit.
     */
    public static void makePayment(){
        System.out.println("Make Payment");
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
                        accountingLedgerHomeScreen();
                        break;
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
        System.out.println("Display All Entries");
    }

    // * D (From ledger screen --> Verify which user, then display only positive or "Deposit" entries)
    public static void displayLedgerDeposits(){
        System.out.println("Display Ledger Deposits, positive values");
    }

    // * P (From ledger screen --> Verify which user, then display only negative or "Payment" entries)
    public static void displayLedgerPayments(){
        System.out.println("Display Ledger Payments, negative values");
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
        System.out.println("display month to date");
    }

    // * 2 (Previous Month)
    public static void displayPreviousMonth(){
        System.out.println("display previous month");
    }

    // * 3 (Year to Date)
    public static void displayYearToDate(){
        System.out.println("display year to date");
    }

    // * 4 (Previous Year)
    public static void displayPreviousYear(){
        System.out.println("display previous year");
    }

    // * 5 (Search by Vendor)
    public static void displaySearchByVendor(){
        System.out.println("display search by vendor");
    }




}