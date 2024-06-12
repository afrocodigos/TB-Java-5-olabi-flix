package com.olabi.olabiflix.exception;

public class FilmeException extends RuntimeException{

    public FilmeException(String message){
        super(message);
    }

    public static class DuplicateFilmeException extends FilmeException{
        public DuplicateFilmeException(){
            super("Filme já cadastrado");
        }
    }

    public static class TitleAlreadyExistsException extends FilmeException{
        public TitleAlreadyExistsException(String message){
            super(message);
        }
    }

    public static class InvalidReleaseYearException extends FilmeException {
        public InvalidReleaseYearException(String message) {
            super(message);
        }
    }

    public static class FilmNotFoundException extends FilmeException{
        public FilmNotFoundException(String message){
            super(message);
        }
    }



}
