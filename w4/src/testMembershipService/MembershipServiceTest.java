package testMembershipService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.*;

import clockProvider.ClockProvider;
import membershipService.MembershipService;

class FixedClockStub implements ClockProvider {
 
	private LocalDate fixed_date;
	FixedClockStub (LocalDate date) {
		fixed_date = date; 
	}
	@Override public LocalDate today() { 
        // your implementation here
		return fixed_date;
		
		// fix
     }
}

public class MembershipServiceTest {
    @Test
    void activeBeforeExpire() {
        MembershipService svc =
            new MembershipService(new FixedClockStub(LocalDate.of(2025, 9, 24)));
        assertTrue(svc.isActive(LocalDate.of(2025, 9, 25)));
    }


}