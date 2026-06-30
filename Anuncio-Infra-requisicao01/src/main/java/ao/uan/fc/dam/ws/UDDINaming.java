package ao.uan.fc.dam.ws.uddi;

public class UDDINaming {

    private final String url;

    public UDDINaming(String url) {
        this.url = url;
        System.out.println("[UDDI MOCK] Conectado a " + url);
    }

    public void rebind(String name, String endpoint) {
        System.out.println(
                "[UDDI MOCK] Registrado: "
                        + name
                        + " -> "
                        + endpoint
        );
    }

    public void unbind(String name) {
        System.out.println(
                "[UDDI MOCK] Removido: "
                        + name
        );
    }
}