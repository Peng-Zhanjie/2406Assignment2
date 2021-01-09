package Test;
import Model.Road;
import org.junit.Assert;
import org.junit.Test;

class BusTest {
    Model.Road road = new Model.Road("0", 1, 5, new int[]{0, 0}, Road.Orientation.VERTICAL);
    Model.Bus bus = new Model.Bus("0", road);

    @Test
    void getLength() {
        Assert.assertEquals(12, bus.getLength());
    }

    @Test
    void getId() {
        Assert.assertEquals("bus_0", bus.getId());
    }

    @Test
    void testInheritance() {
        Assert.assertEquals(0, bus.getSpeed());
        Assert.assertEquals(-12, bus.getPosition());
    }
}