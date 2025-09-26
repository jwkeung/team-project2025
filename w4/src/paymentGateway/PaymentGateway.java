package paymentGateway;

public interface PaymentGateway {
    boolean pay(String userId, double amount);
}