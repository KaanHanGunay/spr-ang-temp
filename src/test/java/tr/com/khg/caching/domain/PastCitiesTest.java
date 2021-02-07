package tr.com.khg.caching.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tr.com.khg.caching.web.rest.TestUtil;

public class PastCitiesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PastCities.class);
        PastCities pastCities1 = new PastCities();
        pastCities1.setId(1L);
        PastCities pastCities2 = new PastCities();
        pastCities2.setId(pastCities1.getId());
        assertThat(pastCities1).isEqualTo(pastCities2);
        pastCities2.setId(2L);
        assertThat(pastCities1).isNotEqualTo(pastCities2);
        pastCities1.setId(null);
        assertThat(pastCities1).isNotEqualTo(pastCities2);
    }
}
