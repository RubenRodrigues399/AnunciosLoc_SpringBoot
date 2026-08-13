package UAN.AnuncuiosLoc.client;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import UAN.AnuncuiosLoc.soap.AnuncioInfraResponse;
import UAN.AnuncuiosLoc.soap.AnuncioRequest;

@Component
public class InfraClient {

    private static final String URL_INFRA =
            "http://localhost:8084/ws";

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public Integer enviarAnuncio(AnuncioRequest request) {

        try {

            AnuncioInfraResponse response =
                    (AnuncioInfraResponse)
                            webServiceTemplate
                                    .marshalSendAndReceive(
                                            URL_INFRA,
                                            request
                                    );

            return response.getStatus();

        } catch (Exception e) {

            e.printStackTrace();
            return 500;

        }

    }

}