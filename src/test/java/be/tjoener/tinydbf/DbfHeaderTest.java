package be.tjoener.tinydbf;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class DbfHeaderTest {

    @Test
    public void testEqualsAndHashcode() {
        EqualsVerifier.forClass(DbfHeader.class)
                .withNonnullFields("lastModified", "fields")
                .verify();
    }

}
