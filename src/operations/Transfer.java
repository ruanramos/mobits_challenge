package operations;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transfer extends Transaction{

	public Transfer(LocalDate date, LocalTime time, BigDecimal value, String description) {
		super(date, time, value, description);
		
	}

}
