package operations;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class will be the base class for any transaction, 
 * such as Withdrawal or Transfer
 */

public abstract class Transaction {

	private LocalDate date;
	private LocalTime time;
	private BigDecimal value;
	private String description;


	public Transaction(LocalDate date, LocalTime time, BigDecimal value, String description) {
		this.date = date;
		this.time = time;
		this.value = value;
		this.description = description;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
