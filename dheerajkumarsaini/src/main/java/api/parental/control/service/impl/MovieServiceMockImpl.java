package api.parental.control.service.impl;

import api.parental.control.exception.TechnicalFailureException;
import api.parental.control.exception.TitleNotFoundException;
import api.parental.control.service.MovieService;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceMockImpl implements MovieService {

    @Override
    public String getParentalControlLevel(String movieId)
            throws TitleNotFoundException, TechnicalFailureException {
        switch (movieId) {
            case "Sample Movie U":
                return "U";
            case "Sample Movie PG":
                return "U";
            case "Sample Movie 12":
                return "12";
            case "Sample Movie 15":
                return "15";
            case "Sample Movie 18":
                return "18";
            case "error":
                throw new TechnicalFailureException("Sorry, there has been a technical failure.");

            default:
                throw new TitleNotFoundException("Sorry, the movie couldn't be found.");
        }
    }
}
