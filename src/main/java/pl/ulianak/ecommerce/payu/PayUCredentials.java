package pl.ulianak.ecommerce.payu;

public class PayUCredentials {
    private String clientSecret;
    private String clientId;
    private boolean sandbox;

    public PayUCredentials(String clientId, String clientSecret, boolean sandbox){
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.sandbox = sandbox;
    }

    public static PayUCredentials sandbox(String clientId, String secret) {
        return new PayUCredentials(clientId, secret, true);
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientsSecret() {
        return clientSecret;
    }

    public String getBaseUrl(){
        if (sandbox) {
            return "https://secure.snd.payu.com";
        } else {
            return "https://secure.payu.com";
        }
    }
}
