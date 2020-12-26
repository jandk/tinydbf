package be.tjoener.tinydbf.value;

import nl.jqno.equalsverifier.*;
import org.junit.*;

import java.time.*;

import static org.assertj.core.api.Assertions.*;

public class DbfDateTest {

    private final DbfValue value = new DbfDate(LocalDate.now());

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(DbfDate.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

    @Test
    public void testIs() {
        assertThat(value.isNull()).isFalse();
        assertThat(value.isCharacter()).isFalse();
        assertThat(value.isDate()).isTrue();
        assertThat(value.isLogical()).isFalse();
        assertThat(value.isNumeric()).isFalse();
    }

    @Test
    public void testAs() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asCharacter);
        assertThat(value.asDate()).isEqualTo(LocalDate.now());
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asLogical);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asNumeric);
    }

}
