import java.util.ArrayList;

public class Statement {

	private String description;
	private ArrayList<Transaction> transitions;
	
	public Statement(String description, ArrayList<Transaction> transitions) {
		super();
		this.description = description;
		this.transitions = transitions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Transaction> getTransitions() {
		return transitions;
	}

	public void setTransitions(ArrayList<Transaction> transitions) {
		this.transitions = transitions;
	}
}
