package be.tjoener.tinydbf.value;

import nl.jqno.equalsverifier.*;
import org.junit.*;

import static org.assertj.core.api.Assertions.*;

public class DbfCharacterTest {

    private final DbfValue value = new DbfCharacter("");

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(DbfCharacter.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

    @Test
    public void testIs() {
        assertThat(value.isNull()).isFalse();
        assertThat(value.isCharacter()).isTrue();
        assertThat(value.isDate()).isFalse();
        assertThat(value.isLogical()).isFalse();
        assertThat(value.isNumeric()).isFalse();
    }

    @Test
    public void testAs() {
        assertThat(value.asCharacter()).isEqualTo("");
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asDate);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asLogical);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asNumeric);
    }

}
