
/**
 * Created by SPRS1994 on 27/10/15.
 * Implementation of Frequency Analysis
 * Student No: 130391986
 */


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.io.*;
import java.util.HashMap;

public class FrequencyAnalysis {


    public static void main(String[] args) throws IOException {

        FrequencyAnalysis fA = new FrequencyAnalysis();

        //(Plain text)to be read in Change name to desired filename
        String pt = fA.readIn("pg1661.txt");

        //Comparison text for testing
        String pt2 = "Supercalifragilisticexpialidocious";

        //(Cipher text)to be read in Change name to desired filename
        String ct = fA.readIn("Exercise1Ciphertext.txt");

        //Sets cipher text for pt2
        String ct2 = fA.encrypt(pt2,4);

        //Text that will not decrypt properly in this program
        String ct3 = fA.readIn("gadsby_encrypted.txt");

        System.out.println("Frequency analysis of plain text and cipher text: \n");

        //Encrypts both plain texts
        String Encrypt = fA.encrypt(pt, 4);
        String Encrypt2 = fA.encrypt(pt2, 4);

        //Decrypts cipher text using pg1661.txt as reference
        String Decrypt = fA.decrypt(ct, fA.calculation(fA.mostFreq(fA.MapLetters(pt)), fA.mostFreq(fA.MapLetters(ct))));

        //Decrypts cipher text created by encrypting pt2
        String Decrypt2 = fA.decrypt(Encrypt2, fA.calculation(fA.mostFreq(fA.MapLetters(pt2)), fA.mostFreq(fA.MapLetters(Encrypt2))));

        //Decrypts cipher text created by encrypting ct3
        String Decrypt3 = fA.decrypt(ct3, fA.calculation(fA.mostFreq(fA.MapLetters(pt)), fA.mostFreq(fA.MapLetters(ct3))));

        //Prints encrypted text for pt2
        System.out.println(Encrypt2);
        //Prints decryption of pt2
        System.out.println(Decrypt2);

        //Prints most frequent for pt2
        System.out.println("\nMost Frequent letter in \"pt2\": " + fA.mostFreq(fA.MapLetters(pt2)) + "\n" +
                        "Frequencies of each letter: " + "\n" +
                        fA.MapLetters(pt2).toString() + "\n"
        );

        //Prints most frequent for pt2
        System.out.println("\nMost Frequent letter in \"ct2\": " + fA.mostFreq(fA.MapLetters(ct2)) + "\n" +
                        "Frequencies of each letter: " + "\n" +
                        fA.MapLetters(ct2).toString() + "\n"
        );


        //Prints most frequent letter for pg1661.txt
        System.out.println("\nMost Frequent letter in \"pg1661.txt\": " + fA.mostFreq(fA.MapLetters(pt)) + "\n" +
                        "Frequencies of each letter: " + "\n" +
                        fA.MapLetters(pt).toString() + "\n"
        );

        //Prints most frequent letter for Exercise2Ciphertext.txt
        System.out.println("\nMost Frequent letter in \"Exercise1Ciphertext.txt\": " + fA.mostFreq(fA.MapLetters(ct)) + "\n" +
                        "Frequencies of each letter: " + "\n" +
                        fA.MapLetters(ct).toString() + "\n"
        );

        //Prints shift number for Exercise2Ciphertext.txt
        System.out.println("Shift number for ciphertext: " + fA.calculation(fA.mostFreq(fA.MapLetters(pt)), fA.mostFreq(fA.MapLetters(Encrypt))) + "\n");

        //Decrypts cipher text and writes to file
        fA.writeToFile(Decrypt, "fAdecryption.txt");
        //Encryption of plain text writes to file
        fA.writeToFile(Encrypt, "fAencryption.txt");

        //Does not decrypt properly as is does not contain an 'e' see report for more information

        System.out.println(Decrypt3);
        System.out.println(fA.mostFreq(fA.MapLetters(ct3)));
        fA.writeToFile(Decrypt3,"gadsby_dec.txt");

    }

    public String readIn(String PATH_NAME) throws IOException {
        /*
        *
        *Reads in file and returns it to be used in analysis
        *
        */
        String textString = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PATH_NAME));

            String line;

            //While there is text in the file
            while ((line = reader.readLine()) != null) {

                textString += line + " ";

            }


        } catch (Exception e) {
            //if file is not found produce error
            System.err.println("File not Found!");
        }

        return textString;


    }

    public HashMap<Character, Integer> MapLetters(String text) throws IOException {
        /*
        *
        *Map character to a counter which holds the number of times the character is shown i.e. frequency
        *Returns the map
        */
        HashMap<Character, Integer> m = new HashMap<Character, Integer>();
        String t = text.toLowerCase();

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);

            if (c >= 97 && c <= 122) {
                Integer counter = m.get(c);

                if (counter == null) {
                    counter = 1;
                } else {
                    counter++;

                }
                m.put(c, counter);


            }
        }

        return m;
    }

    public char mostFreq(HashMap<Character, Integer> m) {
        /*
        *
        * Uses the map from MapLetters function to get the most frequent character and return it
        *
        */
        int mfreq = 0;
        Character ch = null;
        for (char i = 'a'; i <= 'z'; i++) {
            if (m.get(i) != null) {
                if (m.get(i) > mfreq) {
                    mfreq = m.get(i);
                    ch = i;
                }
            } else {
                i++;
            }

        }
        return ch;
    }

    public int calculation(Character p, Character c) {
        /*
        * Calculates the shift number
        */
        int shiftNum = (int) (c - p);

        return shiftNum;
    }


    public String decrypt(String text, int shift) {
        /*
        *Decrypts the cipher text by shifting back the shift number i.e. offset
        */
        return encrypt(text, 26 - shift);
    }

    public String encrypt(String text, int shift) {
        /*
        *
        *Encryption of text by a given shift number
        */
        shift = shift % 26 + 26;
        StringBuilder encrypted = new StringBuilder();
        for (char i : text.toCharArray()) {
            if (Character.isLetter(i)) {
                if (Character.isUpperCase(i)) {
                    encrypted.append((char) ('A' + (i - 'A' + shift) % 26));
                } else {
                    encrypted.append((char) ('a' + (i - 'a' + shift) % 26));
                }
            } else {
                encrypted.append(i);
            }
        }
        return encrypted.toString();
    }

    public void writeToFile(String text, String filename) throws IOException {
        /*
        *Writes to file
        */
        try {
            File file = new File(filename);

            BufferedWriter bW = new BufferedWriter(new FileWriter(file, false));

            bW.write(text);
            bW.flush();
            bW.close();
            System.out.println(filename + " " + "File Written!");
        } catch (Exception e) {
            System.err.println("File not written!");
        }
    }

}




