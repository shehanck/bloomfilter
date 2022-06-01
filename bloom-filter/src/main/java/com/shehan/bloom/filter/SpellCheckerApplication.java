package com.shehan.bloom.filter;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements a CLI application to do the lookups based on the SpellChecker library class.
 * @author Shehan Charuka
 */
public class SpellCheckerApplication {
    private static final String COMMAND_EXIT = "exit";

    private int lookupCount;
    private int successCount;
    private int falsePositiveCount;

    SpellChecker spellChecker;

    public void start(String[] args) {
        printWelcomeMessage();

        try{
            if(args==null || args.length==0){
                spellChecker= new SpellChecker();
            } else if(args.length==1) {
                spellChecker= new SpellChecker(args[0], null, null);
            } else if(args.length==2) {
                spellChecker= new SpellChecker(args[0], args[1], null);
            } else {
                spellChecker= new SpellChecker(args[0], args[1], args[2]);
            }
        } catch (SpellCheckerException | IOException e) {
            System.out.println("Initializing Spell Checker failed - "+e.getMessage());
            System.exit(1);
        }

        while (true) {
            int option = getOptionFromUser();
            if (option == 1) {
                lookupCustomWord();
            } else if (option == 2) {
                lookupRandomWord();
            } else if (option == 3) {
                for(int i=0;i<10;i++){
                    lookupRandomWord();
                }
            } else {
                System.out.println("Invalid option entered!");
                System.out.println();
            }
        }
    }

    private void printWelcomeMessage() {
        System.out.println("       *** WELCOME TO ***       ");
        System.out.println("BLOOM-FILTER BASED SPELL-CHECKER");
        System.out.println();
    }

    private void printExitMessage() {
        System.out.println("Thank you for using BLOOM-FILTER BASED SPELL-CHECKER!");
        System.out.println();
    }

    private int getOptionFromUser() {
        int optNo = 0;
        Scanner scn = new Scanner(System.in);
        System.out.println("Choose an option to proceed. Enter 'exit' to terminate.");
        System.out.println("\t\t 1 - Lookup a Custom Word");
        System.out.println("\t\t 2 - Lookup a Random Word (5-Character)");
        System.out.println("\t\t 3 - Lookup 10 Random Words (5-Character)");
        String option = scn.nextLine().trim();

        if (option!=null && !option.isEmpty()) {
            if (option.equalsIgnoreCase(COMMAND_EXIT)) {
                printExitMessage();
                System.exit(0);
            } else {
                try {
                    optNo = Integer.parseInt(option);
                } catch (NumberFormatException ignored) {

                }
            }
        }
        return optNo;
    }

    private void lookupCustomWord() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter a word to lookup");
        String word = scn.nextLine().trim();

        lookupWord(word);
    }

    private void lookupWord(String word){
        if (word==null || word.isEmpty()) {
            System.out.println("No input");
        } else {
            if (spellChecker.lookup(word)) {
                System.out.println(word + " - Found");
                successCount++;

                if (spellChecker.isFalsePositive(word)) {
                    falsePositiveCount++;
                }
            } else {
                System.out.println(word + " - Not Found");
            }
            lookupCount++;
        }
        printSummary();
    }

    private void lookupRandomWord() {
        String word = getRandomWord();
        lookupWord(word);
    }

    private void printSummary(){
        System.out.println("----- Summary -----");
        System.out.println("Total Lookup Count - "+lookupCount);
        System.out.println("Total Success Count - "+successCount);
        System.out.println("Total False Positive Count - "+falsePositiveCount);
        System.out.println("--- End Summary ---");
        System.out.println();
    }

    private String getRandomWord() {
        Random random = new Random();
        return random.ints(97, 122 + 1)
                .limit(5)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
