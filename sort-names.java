
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;

class SortNames
{
    public static void main(String[] args){
        
        if (args.length != 1){
            System.out.println("Missing required argument\n\nUsage:\n\tsort-names PATH_TO_INPUT_FILE");
            return;
        }

        String inputFilePath = args[0];

        // read names from file
        File input = new File(inputFilePath);
        Scanner reader;

        try {
            reader = new Scanner(input);
        } catch (FileNotFoundException e){
            System.out.println("Input file not found");
            return;
        }

        ArrayList<String> names = new ArrayList<String>();

        while (reader.hasNextLine()) {
            names.add(reader.nextLine());
        }

        reader.close();

        // basic filter for junk data, expand as needed depending on the source & reliability of data
        names.removeIf(name -> name.isBlank());

        // sort
        names.sort(String::compareToIgnoreCase);

        // write to output
        String outputFilePath = inputFilePath.endsWith(".txt")
            ? inputFilePath.substring(0, inputFilePath.length() - 4).concat("-sorted.txt")
            : inputFilePath.concat("-sorted.txt");
        
        Path file = Paths.get(outputFilePath);
        try {
            Files.write(file, names);
        } catch (IOException e){
            System.out.println("Failed to write results to " + outputFilePath);
            return;
        }

        // print output
        names.forEach(name -> System.out.println(name));
        System.out.println("Finished: created " + outputFilePath);
    }
}
