package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UltimaExecucaoService {
    @Autowired
    private UtlimaExecucaoDataSouce utlimaExecucaoRepository;
    
    public LocalDateTime getUltimaData(String tabela) {
        return utlimaExecucaoRepository.findById(tabela)
                .map(UltimaExecucaoEntidade::getUltimaData)
                .orElse(null);
    }

    public void atualizarUltimaExecucaoNoRegisto(String chave, LocalDateTime novaData) {
        UltimaExecucaoEntidade entidade = utlimaExecucaoRepository.findById(chave)
                .orElseGet(() -> {
                    UltimaExecucaoEntidade nova = new UltimaExecucaoEntidade();
                    nova.setTabela(chave);
                    nova.setUltimaData(novaData);
                    return nova;
                });

        if (entidade.getUltimaData() == null) {
            entidade.setUltimaData(novaData);
        }
        utlimaExecucaoRepository.save(entidade);
    }

    public void atualizarUltimaExecucaoNaReplicacao(String chave) {
        UltimaExecucaoEntidade entidade = utlimaExecucaoRepository.findById(chave)
                .orElseGet(() -> {
                    UltimaExecucaoEntidade nova = new UltimaExecucaoEntidade();
                    nova.setTabela(chave);
                    nova.setUltimaData(null);
                    return nova;
                });

        entidade.setUltimaData(null);
        utlimaExecucaoRepository.save(entidade);
    }
    
    public void atualizarUltimaExecucaoNaReplicacaoErro(String chave, LocalDateTime novaData) {
        UltimaExecucaoEntidade entidade = utlimaExecucaoRepository.findById(chave)
                .orElseGet(() -> {
                    UltimaExecucaoEntidade nova = new UltimaExecucaoEntidade();
                    nova.setTabela(chave);
                    nova.setUltimaData(null);
                    return nova;
                });

        entidade.setUltimaData(novaData);
        utlimaExecucaoRepository.save(entidade);
    }
}
