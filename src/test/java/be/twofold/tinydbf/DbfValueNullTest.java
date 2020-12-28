package be.twofold.tinydbf;

import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

public class DbfValueNullTest {

    private final DbfValue value = DbfValueNull.Instance;

    @org.junit.jupiter.api.Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(DbfValueNull.class)
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

    @org.junit.jupiter.api.Test
    public void testAs() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asCharacter);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asDate);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asLogical);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asNumeric);
    }

}
