package com.pluralsight;

import java.util.Scanner;

/**
 * The Console class stores all the utility methods.
 */
public class Console {

    /**
     * The Scanner, used to take the user's input.
     */
    static Scanner scanner = new Scanner(System.in);

    /**
     * Prompt for string string.
     *
     * @param prompt the prompt to the user
     * @return the string
     */
    public static String PromptForString(String prompt){
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Prompt for string string, without a prompt to the user
     *
     * @return the string
     */
    public static String PromptForString(){
        return scanner.nextLine().trim();
    }

    /**
     * Prompt for yes no boolean.
     *
     * @param prompt the prompt to the user
     * @return the boolean
     */
    public static boolean PromptForYesNo(String prompt){
        System.out.print(prompt + " ( Y for Yes, N for No ) ?");
        String userInput = scanner.nextLine().trim();

        return
                (
                        userInput.equalsIgnoreCase("Y")
                                ||
                                userInput.equalsIgnoreCase("YES")
                );

    }

    /**
     * Prompt for short short.
     *
     * @param prompt the prompt to the user
     * @return the short
     */
    public static short PromptForShort(String prompt){
        System.out.print(prompt);
        String value = scanner.nextLine();
        short userinput = Short.parseShort(value);
        return  userinput;
    }

    /**
     * Prompt for int int.
     *
     * @param prompt the prompt to the user
     * @return the int
     */
    public static int PromptForInt(String prompt){
        System.out.print(prompt);
        String value = scanner.nextLine();
        int userinput = Integer.parseInt(value);
        return userinput;
    }

    /**
     * Prompt for int int.
     *
     * @return the int
     */
    public static int PromptForInt(){
        String value = scanner.nextLine();
        int userinput = Integer.parseInt(value);
        return userinput;
    }


    /**
     * Prompt for double double.
     *
     * @param prompt the prompt to the user
     * @return the double
     */
    public static double PromptForDouble(String prompt){
        System.out.print(prompt);
        String userInputs = scanner.nextLine();
        double userinput = Double.parseDouble(userInputs);
        return userinput;
    }

    /**
     * Prompt for byte byte.
     *
     * @param prompt the prompt to the user
     * @return the byte
     */
    public static byte PromptForByte(String prompt){
        System.out.print(prompt);
        String value = scanner.nextLine();
        byte userinput = Byte.parseByte(value);
        return userinput;
    }

    /**
     * Prompt for byte byte.
     *
     * @return the byte
     */
    public static byte PromptForByte(){
        String value = scanner.nextLine();
        byte userinput = Byte.parseByte(value);
        return userinput;
    }

    /**
     * Prompt for float float.
     *
     * @param prompt the prompt to the user
     * @return the float
     */
    public static float PromptForFloat(String prompt){
        System.out.print(prompt);
        String value = scanner.nextLine();
        float userinput = Float.parseFloat(value);
        return  userinput;
    }

}
