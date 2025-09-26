package checkoutService;

import paymentGateway.PaymentGateway;

public class CheckoutService {
    private final PaymentGateway gateway;
    public CheckoutService(PaymentGateway gateway) { this.gateway = gateway; }

    public String checkout(String userId, double amount) {
        if (amount > 1000) return "REJECTED_OVER_LIMIT";
        int attempts = 0;
        while (attempts < 3) { // try at most three times
            attempts++;
            if (gateway.pay(userId, amount)) return "PAID";
        }
        return "TEMPORARY_FAILURE";
    }
}