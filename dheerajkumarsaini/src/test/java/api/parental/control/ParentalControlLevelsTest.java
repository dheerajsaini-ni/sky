package api.parental.control;

import api.parental.control.service.ParentalControlLevels;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParentalControlLevelsTest {

    @Test
    public void test_movieNamesAreAlignedCorrectly() throws Exception {
        Assert.assertEquals(ParentalControlLevels.MOVIE_U, ParentalControlLevels.getByString("U"));
        Assert.assertEquals(ParentalControlLevels.MOVIE_PG, ParentalControlLevels.getByString("PG"));
        Assert.assertEquals(ParentalControlLevels.MOVIE_12, ParentalControlLevels.getByString("12"));
        Assert.assertEquals(ParentalControlLevels.MOVIE_15, ParentalControlLevels.getByString("15"));
        Assert.assertEquals(ParentalControlLevels.MOVIE_18, ParentalControlLevels.getByString("18"));
    }

    @Test
    public void test_movieNamesWhichAreNotPresentReturnNull() throws Exception {
        Assert.assertNull(ParentalControlLevels.getByString("any other movie"));
    }

}
