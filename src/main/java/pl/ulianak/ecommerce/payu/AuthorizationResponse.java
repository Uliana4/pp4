package pl.ulianak.ecommerce.payu;

public class AuthorizationResponse {
    public String getAccess_token() {
        return access_token;
    }

    public AuthorizationResponse setAccess_token(String access_token) {
        this.access_token = access_token;
        return this;
    }

    String access_token;

    public String getAccessToken(){
        return access_token;
    }

}
