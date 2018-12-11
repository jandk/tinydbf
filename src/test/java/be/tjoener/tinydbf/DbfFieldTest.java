package be.tjoener.tinydbf;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class DbfFieldTest {

    @Test
    public void testEqualsAndHashcode() {
        EqualsVerifier.forClass(DbfField.class)
                .withNonnullFields("name", "type")
                .verify();
    }

}
