package project2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    private ArrayList<City> cities = new ArrayList<>();

    public void addCity(City city) {
        cities.add(city);
    }

    public void addHighway(City city1, City city2) {
        city1.getHighwayNeighbors().add(city2);
        city2.getHighwayNeighbors().add(city1);
    }

    public void addAirway(City city1, City city2) {
        city1.getAirwayNeighbors().add(city2);
        city2.getAirwayNeighbors().add(city1);
    }

    public void addRailway(City city1, City city2) {
        city1.getRailwayNeighbors().add(city2);
        city2.getRailwayNeighbors().add(city1);
    }

    public ArrayList<City> getCities() {
        return new ArrayList<>(cities);
    }

    public ArrayList<City> getNeighbors(City city, char transportType) {
        if (city == null) {
            return new ArrayList<>();
        }

        if (transportType == 'H') {
            return new ArrayList<>(city.getHighwayNeighbors());
        } else if (transportType == 'A') {
            return new ArrayList<>(city.getAirwayNeighbors());
        } else if (transportType == 'R') {
            return new ArrayList<>(city.getRailwayNeighbors());
        } else {
            return new ArrayList<>();
        }
    }

    public City findCityByName(String cityName) {
        for (City city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }
    public void buildGraph(ArrayList<String> cities, ArrayList<ArrayList<Integer>> highwayMatrix,
            ArrayList<ArrayList<Integer>> airwayMatrix, ArrayList<ArrayList<Integer>> railwayMatrix) {
        for (int i = 0; i < cities.size(); i++) {
            City city = new City();
            city.setName(cities.get(i));
            addCity(city);
        }

        for (int i = 0; i < getCities().size(); i++) {
            for (int j = 0; j < highwayMatrix.get(i).size(); j++) {
                if (highwayMatrix.get(i).get(j) == 1) {
                    addHighway(getCities().get(i), getCities().get(j));
                }
            }
        }

        for (int i = 0; i < getCities().size(); i++) {
            for (int j = 0; j < airwayMatrix.get(i).size(); j++) {
                if (airwayMatrix.get(i).get(j) == 1) {
                    addAirway(getCities().get(i), getCities().get(j));
                }
            }
        }

        for (int i = 0; i < getCities().size(); i++) {
            for (int j = 0; j < railwayMatrix.get(i).size(); j++) {
                if (railwayMatrix.get(i).get(j) == 1) {
                    addRailway(getCities().get(i), getCities().get(j));
                }
            }
        }
    }

    public ArrayList<City> runQuery1(String startCity, String endCity, char[] transportationOrder, int[] iterationOrder) {
        City start = findCityByName(startCity);
        City end = findCityByName(endCity);
        if (start == null || end == null) {
            System.out.println("Start or end city not found");
            return null;
        }

        ArrayList<City> path = new ArrayList<>();
        Set<City> visited = new HashSet<>();

        boolean found = searchPath(start, end, transportationOrder, iterationOrder, visited, path, 0);

        if (found) {
            return path;
        } else {
            System.out.println("No path found that satisfies the criteria.");
        }
        return null;
    }

    private boolean searchPath(City current, City destination, char[] tOrder, int[] iOrder, Set<City> visited,
            ArrayList<City> path, int depth) {
        if (visited.contains(current)) {
            return false;
        }

        if (current.equals(destination) && depth == iOrder[0] + iOrder[1] + iOrder[2]) {
            path.add(current);
            return true;
        }

        visited.add(current);
        path.add(current);

        ArrayList<City> neighbors = getNeighbors(current, tOrder[depth / iOrder[0]]);
        for (City neighbor : neighbors) {
            if (searchPath(neighbor, destination, tOrder, iOrder, visited, path, depth + 1)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        visited.remove(current);
        return false;
    }

    public ArrayList<ArrayList<String>> runQuery2(String startCity, String endCity, int intermediateCities) {
        City start = findCityByName(startCity);
        City end = findCityByName(endCity);
        if (start == null || end == null) {
            System.out.println("City not found");
            return null;
        }

        ArrayList<ArrayList<String>> allPaths = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        path.add(start.getName());
        dfsQuery2(start, end, intermediateCities + 1, path, allPaths, "");
        return allPaths;
    }

    private void dfsQuery2(City current, City destination, int remainingCities, ArrayList<String> path,
            ArrayList<ArrayList<String>> allPaths, String lastTransport) {
        if (remainingCities == 0 && current.equals(destination)) {
            allPaths.add(new ArrayList<>(path));
            return;
        }

        if (remainingCities <= 0) {
            return;
        }

        if (!lastTransport.equals("H")) {
            exploreNeighbors(current.getHighwayNeighbors(), destination, remainingCities, path, allPaths, "H");
        }
        if (!lastTransport.equals("A")) {
            exploreNeighbors(current.getAirwayNeighbors(), destination, remainingCities, path, allPaths, "A");
        }
        if (!lastTransport.equals("R")) {
            exploreNeighbors(current.getRailwayNeighbors(), destination, remainingCities, path, allPaths, "R");
        }
    }

    private void exploreNeighbors(ArrayList<City> neighbors, City destination, int remainingCities,
            ArrayList<String> path, ArrayList<ArrayList<String>> allPaths, String transportType) {
        for (City next : neighbors) {
            if (path.size() < 2 || !next.getName().equals(path.get(path.size() - 2))) {
                path.add(transportType);
                path.add(next.getName());
                dfsQuery2(next, destination, remainingCities - 1, path, allPaths, transportType);
                path.remove(path.size() - 1);
                path.remove(path.size() - 1);
            }
        }
    }

    public ArrayList<ArrayList<String>> runQuery3(String startCity, String endCity, char transportType) {
        City start = findCityByName(startCity);
        City end = findCityByName(endCity);
        if (start == null || end == null) {
            System.out.println("One of the cities was not found in the graph.");
            return null;
        }

        ArrayList<ArrayList<String>> allPaths = new ArrayList<>();
        ArrayList<String> currentPath = new ArrayList<>();
        currentPath.add(start.getName());
        dfsQuery3(start, end, transportType, currentPath, allPaths, new HashSet<>());
        return allPaths;
    }

    private void dfsQuery3(City current, City destination, char transportType, ArrayList<String> currentPath,
            ArrayList<ArrayList<String>> allPaths, Set<City> visited) {
        if (current.equals(destination)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        if (visited.contains(current)) {
            return;
        }

        visited.add(current);

        ArrayList<City> neighbors;
        if (transportType == 'H') {
            neighbors = current.getHighwayNeighbors();
        } else if (transportType == 'A') {
            neighbors = current.getAirwayNeighbors();
        } else if (transportType == 'R') {
            neighbors = current.getRailwayNeighbors();
        } else {
            throw new IllegalArgumentException("Invalid transportation type: " + transportType);
        }

        for (City neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                currentPath.add(neighbor.getName());
                dfsQuery3(neighbor, destination, transportType, currentPath, allPaths, visited);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        visited.remove(current);
    }
}

