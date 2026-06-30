package UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.RoleUltimaExecucao;
import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.UltimaExecucaoService;
import UAN.AnuncuiosLoc.features.feature_gerir_local.dominio.repositorio.LocalRepository;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.data_sorce.LocalDataSource;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;

@Repository
public class LocalRepositorioImpl implements LocalRepository{
    private final LocalDataSource localDataSource;
    private final UltimaExecucaoService ultimaExecucaoService;

    LocalRepositorioImpl(LocalDataSource _localDataSource, UltimaExecucaoService ultimaExecucaoService){
        this.localDataSource = _localDataSource;
        this.ultimaExecucaoService = ultimaExecucaoService;
    }

    @Override
    public LocalEntidade registarLocal(LocalEntidade request) {
        request = localDataSource.save(request);
        ultimaExecucaoService.atualizarUltimaExecucaoNoRegisto(RoleUltimaExecucao.LOCAL.toString(), request.getUpdatedAt());
        return request;
    }

    @Override
    public Optional<LocalEntidade> findByNome(String nome) {
        return localDataSource.findByNome(nome);
    }

    @Override
    public Optional<LocalEntidade> findById(Integer id) {
        return localDataSource.findById(id);
    }

    @Override
    public Optional<LocalEntidade> findByLatAndLotAndRaio(double latitude, double longitude, double raio) {
        return  localDataSource.findByLatitudeAndLongitudeAndRaio(latitude, longitude, raio);
    }

    @Override
    public Optional<LocalEntidade> findByUrl(String url) {
        return localDataSource.findByUrl(url);
    }

    @Transactional(readOnly = true)
    @Override
    public List<LocalEntidade> pegarTodosLocais() {
        return localDataSource.findAll();
    }

    @Override
    public List<LocalEntidade> pegarLocaisPorCoordenadas(double latitudeUser, double longitudeUser) {
        List<LocalEntidade> locais = localDataSource.findAll();
        List<LocalEntidade> response = new ArrayList<LocalEntidade>();
        for (LocalEntidade local : locais) {
            double distancia = calculaDistanciaEmMetros(latitudeUser, longitudeUser, local.getLatitude(), local.getLongitude());
            if (distancia <= local.getRaio()) {
                response.add(local);
            }
        }
        return response;
    }

    private double calculaDistanciaEmMetros(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // raio da terra em metros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}
