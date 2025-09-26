package membershipService;

import java.time.*;

import clockProvider.ClockProvider;

public class MembershipService {
    private final ClockProvider clock;
    public MembershipService(ClockProvider clock) { this.clock = clock; }

    public boolean isActive(LocalDate expireDate) {
        return !clock.today().isAfter(expireDate);
    }
}