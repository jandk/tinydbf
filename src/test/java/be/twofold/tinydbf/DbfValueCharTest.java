package be.twofold.tinydbf;

import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

public class DbfValueCharTest {

    private final DbfValue value = new DbfValueChar("");

    @org.junit.jupiter.api.Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(DbfValueChar.class)
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

    @org.junit.jupiter.api.Test
    public void testAs() {
        assertThat(value.asCharacter()).isEqualTo("");
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asDate);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asLogical);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asNumeric);
    }

}
