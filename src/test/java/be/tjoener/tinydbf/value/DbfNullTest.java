package be.tjoener.tinydbf.value;

import nl.jqno.equalsverifier.*;
import org.junit.*;

import static org.assertj.core.api.Assertions.*;

public class DbfNullTest {

    private final DbfValue value = DbfNull.Null;

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(DbfNull.class)
            .verify();
    }

    @Test
    public void testIs() {
        assertThat(value.isNull()).isTrue();
        assertThat(value.isCharacter()).isFalse();
        assertThat(value.isDate()).isFalse();
        assertThat(value.isLogical()).isFalse();
        assertThat(value.isNumeric()).isFalse();
    }

    @Test
    public void testAs() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asCharacter);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asDate);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asLogical);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asNumeric);
    }

}
