package tr.com.khg.caching.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tr.com.khg.caching.web.rest.TestUtil;

public class IdentityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Identity.class);
        Identity identity1 = new Identity();
        identity1.setId(1L);
        Identity identity2 = new Identity();
        identity2.setId(identity1.getId());
        assertThat(identity1).isEqualTo(identity2);
        identity2.setId(2L);
        assertThat(identity1).isNotEqualTo(identity2);
        identity1.setId(null);
        assertThat(identity1).isNotEqualTo(identity2);
    }
}
