package com.olabi.olabiflix.config;

import com.olabi.olabiflix.model.entity.Filme;
import com.olabi.olabiflix.repository.FilmeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    private final FilmeRepository filmeRepository;

    public DatabaseInitializer(FilmeRepository filmeRepository){
        this.filmeRepository = filmeRepository;
    }

    public static final List<Filme> filmes = List.of(
            new Filme("10 Things I Hate About You", "1999", "PG-13", "31 Mar 1999", "97 min", "Comedy, Drama, Romance", "Gil Junger", "Karen McCullah, Kirsten Smith", "Heath Ledger, Julia Stiles, Joseph Gordon-Levitt, Larisa Oleynik", "A pretty, popular teenager can't go out on a date until her ill-tempered older sister does.", "English, French", "USA", "2 wins & 13 nominations."),
            new Filme("Avatar", "2009", "PG-13", "18 Dec 2009", "162 min", "Action, Adventure, Fantasy, Sci-Fi", "James Cameron", "James Cameron", "Sam Worthington, Zoe Saldana, Sigourney Weaver, Stephen Lang", "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.", "English, Spanish", "USA", "Won 3 Oscars. Another 86 wins & 129 nominations."),
            new Filme("Inception", "2010", "PG-13", "16 Jul 2010", "148 min", "Action, Adventure, Sci-Fi, Thriller", "Christopher Nolan", "Christopher Nolan", "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page, Tom Hardy", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.", "English, Japanese, French", "USA, UK", "Won 4 Oscars. Another 152 wins & 217 nominations."),
            new Filme("Doctor Strange", "2016", "PG-13", "04 Nov 2016", "115 min", "Action, Adventure, Fantasy, Sci-Fi", "Scott Derrickson", "Jon Spaihts, Scott Derrickson, C. Robert Cargill, Stan Lee (based on the Marvel comics by), Steve Ditko (based on the Marvel comics by)", "Benedict Cumberbatch, Chiwetel Ejiofor, Rachel McAdams, Benedict Wong", "While on a journey of physical and spiritual healing, a brilliant neurosurgeon is drawn into the world of the mystic arts.", "English", "USA", "Nominated for 1 Oscar. Another 20 wins & 65 nominations."),
            new Filme("The Truman Show", "1998", "PG", "05 Jun 1998", "103 min", "Comedy, Drama, Sci-Fi", "Peter Weir", "Andrew Niccol", "Jim Carrey, Laura Linney, Noah Emmerich, Natascha McElhone", "An insurance salesman discovers his whole life is actually a reality TV show.", "English", "USA", "Nominated for 3 Oscars. Another 40 wins & 66 nominations.")
    );



    @Override
    public void run(String... args) throws Exception {
        log.info("Alô pepe moreno? O banco ta conectado");
        filmeRepository.saveAll(filmes);
        filmeRepository.findAll().forEach(filme -> System.out.println(filme));
   }

}
