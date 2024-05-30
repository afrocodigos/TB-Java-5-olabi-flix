package com.olabi.olabiflix.controller;

import com.olabi.olabiflix.model.entity.Filme;
import com.olabi.olabiflix.repository.FilmeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeRepository repository;

    public FilmeController(FilmeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Filme> getFilmes(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Filme> getById(@PathVariable UUID id){
        return repository.findById(id);
    }

// solucao sem  mudar o tipo
//    public Filme getFilmesById(@PathVariable(value = "id") UUID id){
//        return repository.findById(id).orElse(null);
//    }

    @GetMapping("/busca-title")
    public ResponseEntity<Filme> findByTitle(@RequestParam(name = "title", defaultValue = "") String title){
        Optional<Filme> filmeEncontrado = repository.findByTitle(title);

        if(filmeEncontrado.isPresent()){
            Filme filme = filmeEncontrado.get();
            return ResponseEntity.ok(filme);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/busca-genero")
    public ResponseEntity<List<Filme>> findByGenre(@RequestParam(name = "genre", defaultValue = "") String genre){
        List<Filme> filmes = repository.findByGenreContainsIgnoreCase(genre);
        return ResponseEntity.ok(filmes);
    }

    @PostMapping("/criar")
    public Filme create(@RequestBody Filme filmeBody){
        return repository.save(filmeBody);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        repository.deleteById(id);
    }


}
