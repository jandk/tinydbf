package be.twofold.tinydbf;

import nl.jqno.equalsverifier.*;
import org.junit.*;

import static org.assertj.core.api.Assertions.*;

public class LogicalTest {

    private final DbfValue value = DbfValue.Logical.False;

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(DbfValue.Logical.class)
            .verify();
    }

    @Test
    public void testIs() {
        assertThat(value.isNull()).isFalse();
        assertThat(value.isCharacter()).isFalse();
        assertThat(value.isDate()).isFalse();
        assertThat(value.isLogical()).isTrue();
        assertThat(value.isNumeric()).isFalse();
    }

    @Test
    public void testAs() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asCharacter);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asDate);
        assertThat(value.asLogical()).isFalse();
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asNumeric);
    }

}
