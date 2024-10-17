package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The class Transaction for creating instances for each transaction.
 */
public class Transaction {

    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;


    /**
     * Instantiates a new Constructor named Transaction.
     *
     * @param date        the date
     * @param time        the time
     * @param description the description
     * @param vendor      the vendor
     * @param amount      the amount
     */
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    /**
     * Gets the date the transaction occurred.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the time the transaction occurred.
     *
     * @return the time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Gets the description of the transaction.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the person or company that this transaction was with.
     *
     * @return the vendor
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Gets the dollar amount for the transaction.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Prints out the header for the transactions.csv.
     * @return Header formatted as {@code String.format("\n %-10s | %-10s | %-40s | %-15s | %-5s", "Date", "Time", "Description", "Vendor", "Amount")} and a line separator {@code "--".repeat(55);}
     */
    public static String toHeaderString(){
        String string1 = String.format("\n %-10s | %-10s | %-40s | %-15s | %-5s", "Date", "Time", "Description", "Vendor", "Amount");
        String string2 = "--".repeat(55);
        return  string1 + "\n" + string2;
    }

    /**
     * Prints out the formatted string for the transactions.csv.
     * @return Formatted string as {@code String.format("%-10s | %-10s | %-40s | %-15s | $%5.2f", getDate(), getTime().format(dateTimeFormatter), getDescription(), getVendor(), getAmount())}
     */
    @Override
    public String toString(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%-10s | %-10s | %-40s | %-15s | $%5.2f", getDate(), getTime().format(dateTimeFormatter), getDescription(), getVendor(), getAmount());
    }

}
