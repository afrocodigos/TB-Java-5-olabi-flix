package com.olabi.olabiflix.controller;

import com.olabi.olabiflix.model.entity.Serie;
import com.olabi.olabiflix.model.value.Ratings;
import com.olabi.olabiflix.repository.SerieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/series")
public class SerieController {

    private static final Logger log = LoggerFactory.getLogger(FilmeController.class);

    private final SerieRepository repository;


    public SerieController(SerieRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Serie> getAll(){ return repository.findAll();}

    @GetMapping("/{id}")
    public Optional<Serie> getbyId(@PathVariable UUID id){return repository.findById(id);}

    @GetMapping("/busca-titulo")
    public ResponseEntity<Serie> findByTitle(@RequestParam(name="title") String title){
        Optional<Serie> pesquisa = repository.findByTitle(title);

        if(pesquisa.isPresent()){
            Serie serie = pesquisa.get();
            return ResponseEntity.ok(serie);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/busca-genre")
    public ResponseEntity<List<Serie>> findByGenre(@RequestParam(name="genre") String genre){
        List<Serie> series = repository.findSeriesByGenreContainingIgnoreCase(genre);
        return ResponseEntity.ok(series);
    }

    @PostMapping("/criar")
    public ResponseEntity<Serie> create(@RequestBody Serie novaSerie){
        Serie serie = repository.save(novaSerie);
        return new ResponseEntity<>(serie, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/like")
    public ResponseEntity<Serie> like(@PathVariable UUID id){
        Optional<Serie> serieEncontrada = repository.findById(id);

        if(serieEncontrada.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Serie serie = serieEncontrada.get();
        Ratings avaliacao = serie.getRatings();

        //jeito simples
        Integer likesAtuais = Integer.parseInt(avaliacao.getLikes());
        Integer like = likesAtuais + 1;
        avaliacao.setLikes(String.valueOf(like));

        return  ResponseEntity.ok(repository.save(serie));

    }
}
