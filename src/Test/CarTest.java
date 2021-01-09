package Test;
import Model.Road;
import org.junit.Assert;
import org.junit.Test;

class CarTest {
    Model.Road road = new Model.Road("0", 1, 5, new int[]{0, 0}, Road.Orientation.VERTICAL);
    Model.Car car = new Model.Car("0", road);

    @Test
    void testMove() {
        car.move();
        Assert.assertEquals(-3, car.getPosition());
    }

    @Test
    void getLength() {
        Assert.assertEquals(4, car.getLength());
    }

    @Test
    void getBreadth() {
        Assert.assertEquals(2.0, car.getBreadth(),0);
    }

    @Test
    void getSpeed() {
        Assert.assertEquals(0, car.getSpeed());
    }

    @Test
    void getPosition() {
        Assert.assertEquals(-4, car.getPosition());
    }

    @Test
    void getRoad() {
        Assert.assertEquals(road, car.getCurrentRoad());
    }

    @Test
    void getId() {
        Assert.assertEquals("car_0", car.getId());
    }

}