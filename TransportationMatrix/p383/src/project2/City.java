package project2;


import java.util.ArrayList;

public class City {
    private String name;
    private ArrayList<City> highwayNeighbors = new ArrayList<>();
    private ArrayList<City> airwayNeighbors = new ArrayList<>();
    private ArrayList<City> railwayNeighbors = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<City> getHighwayNeighbors() {
        return new ArrayList<>(highwayNeighbors);
    }

    public void setHighwayNeighbors(ArrayList<City> highwayNeighbors) {
        this.highwayNeighbors = new ArrayList<>(highwayNeighbors);
    }

    public ArrayList<City> getAirwayNeighbors() {
        return new ArrayList<>(airwayNeighbors);
    }

    public void setAirwayNeighbors(ArrayList<City> airwayNeighbors) {
        this.airwayNeighbors = new ArrayList<>(airwayNeighbors);
    }

    public ArrayList<City> getRailwayNeighbors() {
        return new ArrayList<>(railwayNeighbors);
    }

    public void setRailwayNeighbors(ArrayList<City> railwayNeighbors) {
        this.railwayNeighbors = new ArrayList<>(railwayNeighbors);
    }

    public ArrayList<City> getNeighbors(char transportType) {
        switch (transportType) {
            case 'H':
                return new ArrayList<>(this.highwayNeighbors);
            case 'A':
                return new ArrayList<>(this.airwayNeighbors);
            case 'R':
                return new ArrayList<>(this.railwayNeighbors);
            default:
                return new ArrayList<>();
        }
    }
}
