package com.olabi.olabiflix.controller;

import com.olabi.olabiflix.exception.FilmeException;
import com.olabi.olabiflix.model.entity.Filme;
import com.olabi.olabiflix.repository.FilmeRepository;
import com.olabi.olabiflix.service.FilmeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private static final Logger log = LoggerFactory.getLogger(FilmeController.class);

    private final FilmeRepository repository;

    private final FilmeService service;

    public FilmeController(FilmeRepository repository, FilmeService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Filme>> getFilmes(){
        try {
            List<Filme> filmes = service.getAll();
            return ResponseEntity.ok(filmes);

        } catch (Exception e){
            log.error("Erro ao buscar os filmes");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> getById(@PathVariable UUID id){
        Optional<Filme> filme = repository.findById(id);

        if(filme.isPresent()){
            return ResponseEntity.ok(filme.get());
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<Object> create(@RequestBody Filme filmeBody){
        try {
            Filme filme = service.create(filmeBody);
            return ResponseEntity.status(HttpStatus.CREATED).body(filme);
        }catch (FilmeException.DuplicateFilmeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@PathVariable UUID id, @RequestBody Filme filmeBody){

        try{
            Filme filme = service.update(id, filmeBody);
            return ResponseEntity.ok(filme);
        } catch (FilmeException.FilmNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Filme> patch(@PathVariable UUID id, @RequestBody Map<String, String> requestBody) throws IllegalAccessException {
        Optional<Filme> filmeEncontrado = repository.findById(id);
        //log.info(String.valueOf(filmeEncontrado));

        if(filmeEncontrado.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Filme filme = filmeEncontrado.get();

        //lista dos campos da minha model
        List<Field> camposDaModel = List.of(filme.getClass().getDeclaredFields());

        //log.info(String.valueOf(camposDaModel));

        for(Field campo : camposDaModel){
            //tirando o privado
            campo.setAccessible(true);
            String nomeCampo = campo.getName();

            if(requestBody.containsKey(nomeCampo)){
                //log.info(nomeCampo);
                String atualizacaoRequest = requestBody.get(nomeCampo);
                campo.set(filme, atualizacaoRequest);
            }
        }

        repository.save(filme);
        return ResponseEntity.ok(filme);

    }


}
