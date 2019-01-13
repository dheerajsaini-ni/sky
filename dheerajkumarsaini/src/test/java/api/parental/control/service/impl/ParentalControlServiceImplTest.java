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

        when(movieService.getParentalControlLevel("PG")).thenReturn("PG");
        when(movieService.getParentalControlLevel("18")).thenReturn("18");

        when(movieService.getParentalControlLevel("not present")).thenThrow(new TitleNotFoundException("Title not found"));
        when(movieService.getParentalControlLevel("error")).thenThrow(new TechnicalFailureException("Technical failure"));

        service = new ParentalControlServiceImpl(movieService);
    }

    @Test
    public void test_adultCanWatchAdultMovies() throws TechnicalFailureException, TitleNotFoundException {
        Assert.assertTrue(service.isAllowedToWatch("18", "18"));
    }

    @Test
    public void test_underageCannotWatchAdultMovies() throws TechnicalFailureException, TitleNotFoundException {
        Assert.assertFalse(service.isAllowedToWatch("18", "15"));
        Assert.assertFalse(service.isAllowedToWatch("18", "12"));
        Assert.assertFalse(service.isAllowedToWatch("18", "PG"));
        Assert.assertFalse(service.isAllowedToWatch("18", "U"));
    }

    @Test
    public void test_underageCannotWatchPGMovies() throws TechnicalFailureException, TitleNotFoundException {
        Assert.assertTrue(service.isAllowedToWatch("PG", "18"));
        Assert.assertTrue(service.isAllowedToWatch("PG", "15"));
        Assert.assertTrue(service.isAllowedToWatch("PG", "12"));
        Assert.assertTrue(service.isAllowedToWatch("PG", "PG"));
        Assert.assertFalse(service.isAllowedToWatch("PG", "U"));
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
