package com.olabi.olabiflix.controller;

import com.olabi.olabiflix.model.entity.Filme;
import com.olabi.olabiflix.repository.FilmeRepository;
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

    @PutMapping("/{id}")
    public ResponseEntity<Filme> put(@PathVariable UUID id, @RequestBody Filme filmeBody){
        Optional<Filme> filmeEncontrado = repository.findById(id);
        //log.info(String.valueOf(filmeEncontrado));

        if(filmeEncontrado.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Filme filme = filmeEncontrado.get();
        //log.info(String.valueOf(filme));

        //atualizacao de tudo, campo por campo
        //qualquer campo que for omitido, no json da requisicao, vai ser cadastrado como nulo

        filme.setTitle(filmeBody.getTitle());
        filme.setReleaseYear(filmeBody.getReleaseYear());
        filme.setRated(filmeBody.getRated());
        filme.setReleased(filmeBody.getReleased());
        filme.setRuntime(filmeBody.getRuntime());
        filme.setGenre(filmeBody.getGenre());
        filme.setDirector(filmeBody.getDirector());
        filme.setWriter(filmeBody.getWriter());
        filme.setActors(filmeBody.getActors());
        filme.setPlot(filmeBody.getPlot());
        filme.setLanguage(filmeBody.getLanguage());
        filme.setCountry(filmeBody.getCountry());
        filme.setAwards(filmeBody.getAwards());

        Filme filmeAtualizado = repository.save(filme);

        return ResponseEntity.ok(filmeAtualizado);
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
