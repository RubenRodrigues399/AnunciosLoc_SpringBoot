package UAN.AnuncuiosLoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import UAN.AnuncuiosLoc.entity.Utilizador;

public interface UtilizadorRepository
        extends JpaRepository<Utilizador, String> {

}