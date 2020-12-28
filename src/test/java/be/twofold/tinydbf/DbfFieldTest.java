package be.twofold.tinydbf;

import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

public class DbfFieldTest {

    @Test
    public void testEqualsAndHashcode() {
        EqualsVerifier.forClass(DbfField.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

}
