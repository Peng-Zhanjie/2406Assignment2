package Test;

import Model.Road;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

class RoadTest {
    Model.Road road = new Model.Road("0", 1, 5, new int[]{0, 0}, Road.Orientation.HORIZONTAL);

    @Test
    void getId() {
        Assert.assertEquals(5, road.getLength());
    }

    @Test
    void getSpeedLimit() {
        Assert.assertEquals(1, road.getSpeedLimit());
    }

    @Test
    void getLength() {
        Assert.assertEquals(5, road.getLength());
    }

    @Test
    void getStartLocationTest() {
        int[] expected1 = {0, 0};
        int[] actual = this.road.getStartLocation();
        Assert.assertEquals(expected1, actual);
    }

    @Test
    void getEndLocation() {
        int[] expected = {5, 0};
        Assert.assertEquals(expected, road.getEndLocation());
    }

    @Test
    void getCars() {
        ArrayList<Model.Car> expected = new ArrayList<>();
        Assert.assertEquals(expected, road.getVehiclesOnRoad());
    }

    @Test
    void getLights() {
        ArrayList<Model.TrafficLight> expected = new ArrayList<>();
        Assert.assertEquals(expected, road.getLightsOnRoad());
    }

    @Test
    void getConnectedRoads() {
        ArrayList<Model.Road> expected = new ArrayList<>();
        Assert.assertEquals(expected, road.getConnectedRoads());
    }
}