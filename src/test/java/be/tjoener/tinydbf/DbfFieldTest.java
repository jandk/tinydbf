package be.tjoener.tinydbf;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class DbfFieldTest {

    @Test
    public void testEqualsAndHashcode() {
        EqualsVerifier.forClass(DbfField.class)
                .suppress(Warning.NULL_FIELDS)
                .verify();
    }

}
