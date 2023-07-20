package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeletravailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teletravail.class);
        Teletravail teletravail1 = new Teletravail();
        teletravail1.setId("id1");
        Teletravail teletravail2 = new Teletravail();
        teletravail2.setId(teletravail1.getId());
        assertThat(teletravail1).isEqualTo(teletravail2);
        teletravail2.setId("id2");
        assertThat(teletravail1).isNotEqualTo(teletravail2);
        teletravail1.setId(null);
        assertThat(teletravail1).isNotEqualTo(teletravail2);
    }
}
