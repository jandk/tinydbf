package be.twofold.tinydbf;

import nl.jqno.equalsverifier.*;
import org.junit.*;

public class DbfFieldTest {

    @Test
    public void testEqualsAndHashcode() {
        EqualsVerifier.forClass(DbfField.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

}
