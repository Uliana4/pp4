package pl.ulianak.ecommerce.payu;

public class OrderCreateResponse {
    String redirectUri;
    String orderId;

    public OrderCreateResponse setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    public OrderCreateResponse setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderCreateResponse setExtOrderId(String extOrderId) {
        this.extOrderId = extOrderId;
        return this;
    }

    String extOrderId;
    public String getRedirectUri() {
        return null;
    }

    public String getOrderId() {
        return null;
    }

    public String getExtOrderId() {
        return null;
    }
}
