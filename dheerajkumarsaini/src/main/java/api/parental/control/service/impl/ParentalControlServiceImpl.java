package api.parental.control.service.impl;

import api.parental.control.Application;
import api.parental.control.exception.TechnicalFailureException;
import api.parental.control.exception.TitleNotFoundException;
import api.parental.control.service.MovieService;
import api.parental.control.service.ParentalControlLevels;
import api.parental.control.service.ParentalControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ParentalControlServiceImpl implements ParentalControlService {

    private static Logger LOG = LoggerFactory.getLogger(ParentalControlServiceImpl.class);
    private MovieService movieService;

    public ParentalControlServiceImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    public boolean isAllowedToWatch(String movieId, String preferredLevel)
            throws TechnicalFailureException, TitleNotFoundException {

        if (!ParentalControlLevels.ratings.containsKey(preferredLevel))
            throw new TechnicalFailureException("Error, the preferred level is not present");

        String parentalControlLevel = movieService.getParentalControlLevel(movieId);

        return ParentalControlLevels.getByString(parentalControlLevel).getValue() <=
                ParentalControlLevels.getByString(preferredLevel).getValue();
    }
}
