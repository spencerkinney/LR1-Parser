
public class Item {
	private String symbol;
	private Object value;
	private int state;
	
	Item() {
		symbol = null;
		value = new String("X");
		state = 0;
	}
	
	Item(Object value, int state) {
		symbol = null;
		this.value = value;
		this.state = state;
	}
	
	Item(String symbol, Object value, int state) {
		this.symbol = symbol;
		this.value = value;
		this.state = state;
	}

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public String toString() {
		if (symbol == null) {
			return "(" + String.valueOf(value) + ":" + String.valueOf(state) + ")";
		}
		return "(" + symbol + "=" + String.valueOf(value) + ":" + String.valueOf(state) + ")";
	}
	
}
