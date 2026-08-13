package UAN.AnuncuiosLoc.client;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import UAN.AnuncuiosLoc.soap.AnuncioInfraResponse;
import UAN.AnuncuiosLoc.soap.AnuncioRequest;

@Component
public class InfraAnuncioClient {

    private static final String URL_INFRA =
            "http://localhost:8084/ws";

    private final WebServiceTemplate webServiceTemplate =
            new WebServiceTemplate();

    public Integer registar(AnuncioRequest request) {

        try {

            AnuncioInfraResponse response =
                    (AnuncioInfraResponse) webServiceTemplate
                            .marshalSendAndReceive(
                                    URL_INFRA,
                                    request);

            return response.getStatus();

        } catch (Exception e) {

            e.printStackTrace();

            return 500;
        }

    }

}