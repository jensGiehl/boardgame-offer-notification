package de.agiehl.boardgame.BoardgameOffersFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoardgameOffersFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardgameOffersFinderApplication.class, args);
	}

}
