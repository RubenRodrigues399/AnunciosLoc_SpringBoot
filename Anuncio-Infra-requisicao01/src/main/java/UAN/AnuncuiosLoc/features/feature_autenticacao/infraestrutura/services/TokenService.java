package UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.services;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.data_source.SessaoDataSource;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.SessaoEntidade;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class TokenService {
    private final SessaoDataSource jpaSessao;
    private final SecretKey keyToken;

    public TokenService(SessaoDataSource jpaSessao) {
        this.jpaSessao = jpaSessao;
        
        // Substitua por uma chave Base64 válida (exemplo abaixo)
        String secretString = "U3VwZXJTZWNyZXRLZXlBZ2VyYVBhcmFFc3RhQXBsaWNhY2FvMjAyNA==";
        
        try {
            this.keyToken = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretString));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Chave Base64 inválida: " + e.getMessage(), e);
        }
    }

 


   public String generateToken(String idSessao) {
    // 2. Define a expiração (opcional)
    Date expiration = new Date(System.currentTimeMillis() + 3600000); // 1 hora
        String token = Jwts.builder()
        .setSubject(idSessao)
        .claim("api_origin", "my-soap-api") // Identificador da sua API
        .signWith(keyToken)
       // .setExpiration(expiration)
        .compact();
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(keyToken)
                .build()
                .parseClaimsJws(token)
                .getBody();
            
            int idSessao = Integer.parseInt(claims.getSubject());
            Optional<SessaoEntidade> sessao = jpaSessao.findById(idSessao);
            
            return sessao.isPresent() && sessao.get().getDataFim() == null;
        } catch (Exception e) {
            return false;
        }
    }
}
