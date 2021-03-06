package be.twofold.tinydbf;

import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

public class DbfValueLogicalTest {

    private final DbfValue value = DbfValueLogical.False;

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(DbfValueLogical.class)
            .verify();
    }

    @org.junit.jupiter.api.Test
    public void testIs() {
        assertThat(value.isNull()).isFalse();
        assertThat(value.isCharacter()).isFalse();
        assertThat(value.isDate()).isFalse();
        assertThat(value.isLogical()).isTrue();
        assertThat(value.isNumeric()).isFalse();
    }

    @org.junit.jupiter.api.Test
    public void testAs() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asCharacter);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asDate);
        assertThat(value.asLogical()).isFalse();
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asNumeric);
    }

}
