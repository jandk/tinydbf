package be.twofold.tinydbf;

import nl.jqno.equalsverifier.*;
import org.junit.jupiter.api.*;

import java.time.*;

import static org.assertj.core.api.Assertions.*;

public class DbfValueDateTest {

    private final DbfValue value = new DbfValueDate(LocalDate.now());

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(DbfValueDate.class)
            .suppress(Warning.NULL_FIELDS)
            .verify();
    }

    @org.junit.jupiter.api.Test
    public void testIs() {
        assertThat(value.isNull()).isFalse();
        assertThat(value.isCharacter()).isFalse();
        assertThat(value.isDate()).isTrue();
        assertThat(value.isLogical()).isFalse();
        assertThat(value.isNumeric()).isFalse();
    }

    @org.junit.jupiter.api.Test
    public void testAs() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asCharacter);
        assertThat(value.asDate()).isEqualTo(LocalDate.now());
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asLogical);
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(value::asNumeric);
    }

}
