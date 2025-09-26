package testCheckoutService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import checkoutService.CheckoutService;
import paymentGateway.PaymentGateway;

class PaymentGatewayStub implements PaymentGateway {
    private final boolean[] scripted;
    private int index = 0;
    int callCount = 0;

    PaymentGatewayStub(boolean... scripted) {
        this.scripted = scripted;
    }

    @Override
    public boolean pay(String userId, double amount) {
        callCount++;
        if (index < scripted.length) {
            return scripted[index++];
        }
        return false;
    }
}

public class CheckoutServiceTest {

    @Test
    void paysAfterTwoRetries() {
        PaymentGatewayStub stub = new PaymentGatewayStub(false, false, true); // failed in first two tries, succeed in the third try
        CheckoutService svc = new CheckoutService(stub);
        assertEquals("PAID", svc.checkout("u1", 99));
        assertEquals(3, stub.callCount);
    }

    @Test
    void temporaryFailureAfterThreeAttempts() {
        PaymentGatewayStub stub = new PaymentGatewayStub(false, false, false); // failed all three tries
        CheckoutService svc = new CheckoutService(stub);
        assertEquals("TEMPORARY_FAILURE", svc.checkout("u1", 99));
        assertEquals(3, stub.callCount);
    }

    @Test
    void rejectWhenOverLimit_NoGatewayCalls() {
        PaymentGatewayStub stub = new PaymentGatewayStub(true); // succeed in one try
        CheckoutService svc = new CheckoutService(stub);
        assertEquals("REJECTED_OVER_LIMIT", svc.checkout("u1", 1000.01));
        assertEquals(0, stub.callCount);
    }
}
