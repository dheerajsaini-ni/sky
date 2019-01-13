package api.parental.control;

import api.parental.control.exception.TechnicalFailureException;
import api.parental.control.exception.TitleNotFoundException;
import api.parental.control.service.ParentalControlService;
import api.parental.control.service.impl.MovieServiceMockImpl;
import api.parental.control.service.impl.ParentalControlServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner{

	private static Logger LOG = LoggerFactory
			.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");
		ParentalControlService parentalControlService = new ParentalControlServiceImpl(new MovieServiceMockImpl());

		if (args.length != 2){
			LOG.info("Please provide movieId and preferredLevel as inputs. Sample is");
			LOG.info("java -jar build/libs/dheerajkumarsaini-0.0.1-SNAPSHOT.jar \"Sample Movie 15\" \"18\"");
			System.exit(1);
		}

		try {
			boolean allowed = parentalControlService.isAllowedToWatch(args[0], args[1]);
			LOG.info(String.format("You are %s to watch this movie", (allowed ? "allowed" : "not allowed")));

		} catch (TechnicalFailureException | TitleNotFoundException e) {
			LOG.error(e.getMessage());
		}
	}
}

