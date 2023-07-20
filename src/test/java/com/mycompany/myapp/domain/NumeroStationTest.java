package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NumeroStationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumeroStation.class);
        NumeroStation numeroStation1 = new NumeroStation();
        numeroStation1.setId("id1");
        NumeroStation numeroStation2 = new NumeroStation();
        numeroStation2.setId(numeroStation1.getId());
        assertThat(numeroStation1).isEqualTo(numeroStation2);
        numeroStation2.setId("id2");
        assertThat(numeroStation1).isNotEqualTo(numeroStation2);
        numeroStation1.setId(null);
        assertThat(numeroStation1).isNotEqualTo(numeroStation2);
    }
}
