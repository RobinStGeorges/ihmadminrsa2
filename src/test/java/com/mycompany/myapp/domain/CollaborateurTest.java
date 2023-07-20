package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollaborateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collaborateur.class);
        Collaborateur collaborateur1 = new Collaborateur();
        collaborateur1.setId("id1");
        Collaborateur collaborateur2 = new Collaborateur();
        collaborateur2.setId(collaborateur1.getId());
        assertThat(collaborateur1).isEqualTo(collaborateur2);
        collaborateur2.setId("id2");
        assertThat(collaborateur1).isNotEqualTo(collaborateur2);
        collaborateur1.setId(null);
        assertThat(collaborateur1).isNotEqualTo(collaborateur2);
    }
}
