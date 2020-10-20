import static org.junit.jupiter.api.Assertions.*;

class SavingsTest {

    @org.junit.jupiter.api.Test
    void minBalance() {
        Savings s = new Savings(250);
        assertEquals(5.0, s.MinBalance());
    }
}