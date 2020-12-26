package be.tjoener.tinydbf;

import nl.jqno.equalsverifier.*;
import org.junit.*;

public class DbfHeaderTest {

    @Test
    public void testEqualsAndHashcode() {
        EqualsVerifier.forClass(DbfHeader.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

}
