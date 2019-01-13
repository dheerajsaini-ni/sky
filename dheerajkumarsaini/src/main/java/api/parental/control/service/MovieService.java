package api.parental.control.service;

import api.parental.control.exception.TechnicalFailureException;
import api.parental.control.exception.TitleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface MovieService {

    String getParentalControlLevel(String movieId)
            throws TitleNotFoundException, TechnicalFailureException;
}
