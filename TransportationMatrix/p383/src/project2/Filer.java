package project2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Filer {

    public boolean readInputFromFile(String filename, ArrayList<String> cities,
            ArrayList<ArrayList<Integer>> highwayMatrix, ArrayList<ArrayList<Integer>> airwayMatrix,
            ArrayList<ArrayList<Integer>> railwayMatrix) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("Highway")) {
                    br.readLine(); 
                    readMatrix(br, cities, highwayMatrix);
                } else if (line.equals("Airway")) {
                    br.readLine(); 
                    readMatrix(br, cities, airwayMatrix);
                } else if (line.equals("Railway")) {
                    br.readLine(); 
                    readMatrix(br, cities, railwayMatrix);
                }
            }
        }
        return false;
    }

    private void readMatrix(BufferedReader br, ArrayList<String> cities, ArrayList<ArrayList<Integer>> matrix)
            throws IOException {
        String matrixLine;
        while ((matrixLine = br.readLine()) != null && !matrixLine.isEmpty()) {
            String[] parts = matrixLine.split(" ");
            String city = parts[0];
            cities.add(city);
            ArrayList<Integer> row = new ArrayList<>();
            for (int i = 1; i < parts.length; i++) {
                row.add(Integer.parseInt(parts[i]));
            }
            matrix.add(row);
        }
    }

    public boolean writeOutputToFile(String text) {
        try {
            FileWriter fileWriter = new FileWriter("output.txt", true);
            fileWriter.write(text);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean resetOutputFile() {
        try {
            FileWriter fileWriter = new FileWriter("output.txt", false);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> readQueryFile(String filename) throws FileNotFoundException, IOException {
        ArrayList<String> queries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                queries.add(line);
            }
        }
        return queries;
    }
}
