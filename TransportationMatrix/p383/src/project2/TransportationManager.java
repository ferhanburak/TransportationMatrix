package project2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TransportationManager {
    Filer filer = new Filer();

    ArrayList<ArrayList<Integer>> highwayMatrix = new ArrayList<>();
    ArrayList<ArrayList<Integer>> airwayMatrix = new ArrayList<>();
    ArrayList<ArrayList<Integer>> railwayMatrix = new ArrayList<>();

    ArrayList<String> cities = new ArrayList<String>();

    Graph graph = new Graph();

    public boolean readInputFromFile(String filename) throws FileNotFoundException, IOException {
        filer.resetOutputFile();
        filer.readInputFromFile("transportation_network.inp", cities, highwayMatrix, airwayMatrix, railwayMatrix);
        graph.buildGraph(cities, highwayMatrix, airwayMatrix, railwayMatrix);
        return false;
    }

    public boolean processQuery(String query) {
        String[] parts = query.split(" ");
        String queryType = parts[0];
        filer.writeOutputToFile("query " + query);
        if (queryType.equals("Q1")) {
            processQuery1(parts[1], parts[2], parts[3], parts[4], parts[5]);
        } else if (queryType.equals("Q2")) {
            processQuery2(parts[1], parts[2], Integer.parseInt(parts[3]));
        } else if (queryType.equals("Q3")) {
            processQuery3(parts[1], parts[2], parts[3]);
        }
        return false;
    }

    public void processQueries() throws FileNotFoundException, IOException {
        readInputFromFile("transportation_network.inp");
        ArrayList<String> result = filer.readQueryFile("query.inp");
        for (String query : result) {
            processQuery(query);
        }
    }

    private void printPath(ArrayList<ArrayList<String>> allPaths) {
        String path = "";
        for (ArrayList<String> p : allPaths) {
            {
                path += String.join(" ", p) + "\n";
            }
        }
        System.out.println(path);
        filer.writeOutputToFile(path);
    }

    private void printFormattedPath(ArrayList<City> path, char[] tOrder, int[] iterationOrder) {
        int counter = 0;
        int segment = 0;
        String output = "";

        for (int i = 0; i < path.size() - 1; i++) {
            City city = path.get(i);
            output += city.getName();
            if (counter < iterationOrder[segment]) {
                output += " " + tOrder[segment] + " ";
                counter++;
            }
            if (counter == iterationOrder[segment] && segment < tOrder.length - 1) {
                segment++;
                counter = 0;
            }
        }

        output += path.get(path.size() - 1).getName() + "\n";
        System.out.println(output);
        filer.writeOutputToFile(output);
    }

    private boolean processQuery1(String city1, String city2, String firstT, String secondT, String thirdT) {
        char[] transportationOrder = { firstT.charAt(0), secondT.charAt(0), thirdT.charAt(0) };
        int[] iterationOrder = { Character.getNumericValue(firstT.charAt(1)),
                Character.getNumericValue(secondT.charAt(1)), Character.getNumericValue(thirdT.charAt(1)) };
        ArrayList<City> path = graph.runQuery1(city1, city2, transportationOrder, iterationOrder);
        printFormattedPath(path, transportationOrder, iterationOrder);
        return false;
    }

    private boolean processQuery2(String city1, String city2, int cityCount) {
        ArrayList<ArrayList<String>> allPaths = graph.runQuery2(city1, city2, cityCount);
        printPath(allPaths);
        return false;
    }

    private boolean processQuery3(String city1, String city2, String transportationType) {
        ArrayList<ArrayList<String>> allPaths = graph.runQuery3(city1, city2, transportationType.charAt(0));
        printPath(allPaths);
        return false;
    }
}
