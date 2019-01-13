package api.parental.control.service;

import api.parental.control.exception.TechnicalFailureException;
import api.parental.control.exception.TitleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface ParentalControlService {

    boolean isAllowedToWatch(String movieId, String preferredLevel)
            throws TechnicalFailureException, TitleNotFoundException;
}
