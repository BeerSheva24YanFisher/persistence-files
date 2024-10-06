package telran.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SplitCodeAndComments {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalArgumentException();
        }

        try (BufferedReader inputReader = new BufferedReader(new FileReader(args[0]));
             BufferedWriter codeWriter = new BufferedWriter(new FileWriter(args[1]));
             BufferedWriter commentsWriter = new BufferedWriter(new FileWriter(args[2]))) {

            String line;
            while ((line = inputReader.readLine()) != null) {
                handleLine(line, codeWriter, commentsWriter);
            }
        }
    }

    private static void handleLine(String line, BufferedWriter codeWriter, BufferedWriter commentsWriter) throws IOException {
        String trimmedLine = line.trim();
        if (trimmedLine.startsWith("//")) {
            commentsWriter.write(line);
            commentsWriter.newLine();
        } else {
            int commentIndex = line.indexOf("//");
            if (commentIndex >= 0) {
                codeWriter.write(line.substring(0, commentIndex).trim());
                codeWriter.newLine();
                commentsWriter.write(line.substring(commentIndex).trim());
                commentsWriter.newLine();
            } else {
                codeWriter.write(line);
                codeWriter.newLine();
            }
        }
    }
}