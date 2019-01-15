package api.parental.control.service.impl;

import api.parental.control.exception.TechnicalFailureException;
import api.parental.control.exception.TitleNotFoundException;
import api.parental.control.service.MovieService;
import api.parental.control.service.ParentalControlService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceImplTest {

    @Mock
    private MovieService movieService;

    private ParentalControlService service;

    @Before
    public void init() throws TechnicalFailureException, TitleNotFoundException {

        when(movieService.getParentalControlLevel("MOVIE_U")).thenReturn("U");
        when(movieService.getParentalControlLevel("MOVIE_PG")).thenReturn("PG");
        when(movieService.getParentalControlLevel("MOVIE_12")).thenReturn("12");
        when(movieService.getParentalControlLevel("MOVIE_15")).thenReturn("15");
        when(movieService.getParentalControlLevel("MOVIE_18")).thenReturn("18");

        when(movieService.getParentalControlLevel("not present")).thenThrow(new TitleNotFoundException("Title not found"));
        when(movieService.getParentalControlLevel("error")).thenThrow(new TechnicalFailureException("Technical failure"));

        service = new ParentalControlServiceImpl(movieService);
    }

    @Test
    public void test_adultCanWatchAllMovies() throws TechnicalFailureException, TitleNotFoundException {
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_U", "18"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_PG", "18"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_12", "18"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_15", "18"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_18", "18"));
    }

    @Test
    public void test_age15CanWatchLevel15AndBelow() throws TechnicalFailureException, TitleNotFoundException {

        Assert.assertTrue(service.isAllowedToWatch("MOVIE_U", "15"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_PG", "15"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_12", "15"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_15", "15"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_18", "15"));
    }

    @Test
    public void test_age12CanWatchLevel12AndBelow() throws TechnicalFailureException, TitleNotFoundException {

        Assert.assertTrue(service.isAllowedToWatch("MOVIE_U", "12"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_PG", "12"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_12", "12"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_15", "12"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_18", "12"));
    }

    @Test
    public void test_agePGCanWatchLevelPGAndBelow() throws TechnicalFailureException, TitleNotFoundException {
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_U", "PG"));
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_PG", "PG"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_12", "PG"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_15", "PG"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_18", "PG"));
    }

    @Test
    public void test_underageCanWatchUnderageOnly() throws TechnicalFailureException, TitleNotFoundException {
        Assert.assertTrue(service.isAllowedToWatch("MOVIE_U", "U"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_PG", "U"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_12", "U"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_15", "U"));
        Assert.assertFalse(service.isAllowedToWatch("MOVIE_18", "U"));
    }

    @Test(expected = TitleNotFoundException.class)
    public void test_titleNotFoundExceptionWhenNotPresent() throws TechnicalFailureException, TitleNotFoundException {
        Assert.assertFalse(service.isAllowedToWatch("not present", "18"));
    }

    @Test(expected = TechnicalFailureException.class)
    public void test_technicalExceptionWhenError() throws TechnicalFailureException, TitleNotFoundException {
        Assert.assertFalse(service.isAllowedToWatch("error", "18"));
    }

}
