package Model;

public class Taxi extends Vehicle {

    public Taxi(String id, Road currentRoad) {
        super(currentRoad);
        this.id = ("Taxi_" + id);
        setLength(super.getLength() * 1);
        position = -length;
    }
}