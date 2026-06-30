package UAN.AnuncuiosLoc.features.feature_gerir_local.aplicacao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anuncios.ws.local.RegistarLocalRequest;

import UAN.AnuncuiosLoc.core.exception.ValorJaExisteException;
import UAN.AnuncuiosLoc.features.feature_gerir_local.dominio.repositorio.LocalRepository;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;

@Service
public class RegistarLocalUseCase {
    private final LocalRepository localRepository;
    RegistarLocalUseCase(LocalRepository _localRepository){
        this.localRepository = _localRepository;
    }

    public LocalEntidade executar (RegistarLocalRequest params){
        System.out.println(params.getNome());
        Optional<LocalEntidade> localOptNome= localRepository.findByNome(params.getNome());

        if (localOptNome.isPresent()) {
            throw new ValorJaExisteException("Já existe um local com este nome.");   
        }
        
        Optional<LocalEntidade> localOptUrl = localRepository.findByUrl(params.getUrl());

        if (localOptUrl.isPresent()) {
            throw new ValorJaExisteException("Já existe um local com essa URL.");   
        }
        
        Optional<LocalEntidade> localOptCoordenadas= localRepository.findByLatAndLotAndRaio(params.getLatitude(), params.getLongitude(), params.getRaio());
        

        if (localOptCoordenadas.isPresent()) {
            throw new ValorJaExisteException("Já existe um local com estas coordenadas.");   
        }

        LocalEntidade localEntidade = new LocalEntidade();
        localEntidade.setLatitude(params.getLatitude());
        localEntidade.setLongitude(params.getLongitude());
        localEntidade.setNome(params.getNome());
        localEntidade.setRaio(params.getRaio());
        localEntidade.setUrl(params.getUrl());
        localEntidade.setCreatedAt(LocalDateTime.now());
        localEntidade.setUpdatedAt(LocalDateTime.now());

        return localRepository.registarLocal(localEntidade);
        
    } 
}
