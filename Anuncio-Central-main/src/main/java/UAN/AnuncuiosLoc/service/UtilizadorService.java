package UAN.AnuncuiosLoc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import UAN.AnuncuiosLoc.entity.Utilizador;
import UAN.AnuncuiosLoc.repository.UtilizadorRepository;

@Service
public class UtilizadorService {

    private final UtilizadorRepository repository;

    public UtilizadorService(UtilizadorRepository repository) {
        this.repository = repository;
    }

    public Utilizador guardar(Utilizador utilizador) {
        return repository.save(utilizador);
    }

    public List<Utilizador> listarTodos() {
        return repository.findAll();
    }
}