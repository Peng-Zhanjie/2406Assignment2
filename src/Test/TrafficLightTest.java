package Test;
import Model.Road;
import Model.TrafficLight;
import org.junit.Assert;
import org.junit.Test;

class TrafficLightTest {
    Road road = new Road("0", 1, 5, new int[]{0, 0}, Road.Orientation.VERTICAL);
    TrafficLight light = new TrafficLight("0", road);

    @Test
    void testOperate() {
        light.operate(3515);
        Assert.assertEquals("green", light.getState());
    }

    @Test
    void getState() {
        Assert.assertEquals("red", light.getState());
    }

    @Test
    void getRoad() {
        Assert.assertEquals(road, light.getRoadAttachedTo());
    }

    @Test
    void getPosition() {
        Assert.assertEquals(5, light.getPosition());
    }

    @Test
    void getId() {
        Assert.assertEquals("light_0", light.getId());
    }
}