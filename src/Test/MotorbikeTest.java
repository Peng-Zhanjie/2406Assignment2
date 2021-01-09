package Test;
import Model.Road;
import org.junit.Assert;
import org.junit.Test;


class MotorbikeTest {
    Model.Motorbike bike = new Model.Motorbike("0", new Model.Road("0", 1, 5, new int[]{0, 0}, Road.Orientation.VERTICAL));

    @Test
    void getLength() {
        Assert.assertEquals(2.0, bike.getLength(),0);
    }

    @Test
    void getId() {
        Assert.assertEquals("bike_0", bike.getId());
    }

    @Test
    void testInheritance() {
        Assert.assertEquals(0, bike.getSpeed());
        Assert.assertEquals(-2, bike.getPosition());
    }
}