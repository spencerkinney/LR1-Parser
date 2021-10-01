import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class LR1 {

	private Queue<String> tokens = new LinkedList<String>();
	private Queue<String> temp_tokens;
	private Stack<Item> items = new Stack<Item>();
	private boolean invalidFlag = false;
	private boolean acceptFlag = false;
	
	private final String NUMBER_SIGN = "n";
	private final String PLUS_SIGN = "+";
	private final String MINUS_SIGN = "-";
	private final String MULT_SIGN = "*";
	private final String DIV_SIGN = "/";
	private final String OPEN_PAREN_SIGN = "(";
	private final String CLOSE_PAREN_SIGN = ")";
	private final String DOLLAR_SIGN = "$";
	
	private int E_state;
	private int T_state;
	private int F_state;
	
	
	
	public LR1(String expression) {
		tokenize(expression);
	}

	public void tokenize(String expression) {
		StringTokenizer st = new StringTokenizer(expression, "*/-+()", true);
		while (st.hasMoreTokens()) {
			tokens.add(st.nextToken());
		}
		tokens.add("$");
		temp_tokens = tokens;
	}
	
	private String queuePrettyPrint(Queue<?> e) {
		String list = e.toString();
		list = list.replace(",", "");
		list = list.replace(" ", "");
		return list;
	}
	
	private String stackPrettyPrint(Stack<Item> e) {
		String list = e.toString();
		list = list.replace(",", "");
		return list;
	}
	
	private void printState() {
		String queueOutput = queuePrettyPrint(temp_tokens);
		String stackOutput = stackPrettyPrint(items);
		System.out.println("Stack: " + stackOutput + "    " + "Input Queue: " + queueOutput);
	}
	
	public void algorithm() {
		Item first = new Item();
		items.push(first);
		if (invalidFlag == false) {
			printState();	
		}
		while (acceptFlag == false && invalidFlag == false) {
			printState();
			int currentState = items.peek().getState();
			switch (currentState) {
			case 0:
				ruleZero();
				break;
			case 1:
				ruleOne();
				break;
			case 2:
				ruleTwo();
				break;
			case 3:
				ruleThree();
				break;
			case 4:
				ruleFour();
			case 5:
				ruleFive();
				break;
			case 6:
				ruleSix();
				break;
			case 7:
				ruleSeven();
				break;
			case 8:
				ruleEight();
			case 9:
				ruleNine();
				break;
			case 10:
				ruleTen();
				break;
			case 11:
				ruleEleven();
			default:
				invalidIndicator();
				break;
			}

		}
	}
	
	private Object tryParseInt(String currentSymbol) {
		try {
			Number nextNumber;
			nextNumber = NumberFormat.getInstance().parse(currentSymbol);
			return nextNumber;
		} catch (NumberFormatException | ParseException e) {
		}
		return currentSymbol;
	}
	
	private void invalidIndicator() {
		invalidFlag = true;
		System.out.println("===============");
		System.out.println("INVALID INPUT");
		System.out.println("===============");
	}
	
	private void accept() {
		acceptFlag = true;
		System.out.println("Valid Expression, value = " + items.peek().getValue() + ".");
	}
	
	private void ruleZero() {
		E_state = 1;
		T_state = 2;
		F_state = 3;
		Object currentValue = tryParseInt(temp_tokens.poll());
		if (currentValue instanceof Number) {
			Item e = new Item(NUMBER_SIGN, currentValue, 5);
			items.push(e);
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			Item e = new Item(OPEN_PAREN_SIGN, 4);
			items.push(e);
		}
		else {
			invalidIndicator();
		}
	}

	private void ruleOne() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			invalidIndicator();
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN) || currentValue.equals(CLOSE_PAREN_SIGN) || currentValue.equals(MULT_SIGN) || currentValue.equals(DIV_SIGN)) {
			invalidIndicator();
		}
		else if (currentValue.equals(PLUS_SIGN) || currentValue.equals(MINUS_SIGN)) {
			currentValue = tryParseInt(temp_tokens.poll());
			Item e = new Item(currentValue, 6);
			items.push(e);
		}
		else if (currentValue.equals(DOLLAR_SIGN)) {
			accept();
		}
		else {
			invalidIndicator();
		}
	}
	
	private void ruleTwo() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			invalidIndicator();
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			invalidIndicator();
		}
		else if (currentValue.equals(PLUS_SIGN) || currentValue.equals(MINUS_SIGN) || currentValue.equals(CLOSE_PAREN_SIGN) || currentValue.equals(DOLLAR_SIGN)) {
			items.peek().setSymbol("E");
			items.peek().setState(E_state);
		}
		else if (currentValue.equals(MULT_SIGN) || currentValue.equals(DIV_SIGN)) {
			currentValue = tryParseInt(temp_tokens.poll());
			Item e = new Item(currentValue, 7);
			items.push(e);
		}
		else {
			invalidIndicator();
		}
	}

	private void ruleThree() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			invalidIndicator();
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			invalidIndicator();
		}
		else {
			items.peek().setSymbol("T");
			items.peek().setState(T_state);
		}
	}
	
	private void ruleFour() {
		E_state = 8;
		T_state = 2;
		F_state = 3;
		Object currentValue = tryParseInt(temp_tokens.poll());
		if (currentValue instanceof Number) {
			Item e = new Item(NUMBER_SIGN, currentValue, 5);
			items.push(e);
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			Item e = new Item(OPEN_PAREN_SIGN, 4);
			items.push(e);
		}
		else {
			invalidIndicator();
		}
	}
	
	private void ruleFive() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			invalidIndicator();
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			invalidIndicator();
		}
		else {
			items.peek().setSymbol("F");
			items.peek().setState(F_state);
		}
	}
	
	private void ruleSix() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			currentValue = tryParseInt(temp_tokens.poll());
			Item e = new Item(NUMBER_SIGN, currentValue, 5);
			items.push(e);
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			currentValue = tryParseInt(temp_tokens.poll());
			Item e = new Item(currentValue, 4);
			items.push(e);
		}
		else {
			invalidIndicator();
		}
		T_state = 9;
		F_state = 3;
	}
	
	private void ruleSeven() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			currentValue = tryParseInt(temp_tokens.poll());
			Item e = new Item(NUMBER_SIGN, currentValue, 5);
			items.push(e);
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			currentValue = tryParseInt(temp_tokens.poll());
			Item e = new Item(currentValue, 4);
			items.push(e);
		}
		else {
			invalidIndicator();
		};
		F_state = 10;
	}
	
	private void ruleEight() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			invalidIndicator();
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN) || currentValue.equals(CLOSE_PAREN_SIGN) || currentValue.equals(MULT_SIGN) || currentValue.equals(DIV_SIGN)) {
			invalidIndicator();
		}
		else if (currentValue.equals(PLUS_SIGN) || currentValue.equals(MINUS_SIGN)) {
			currentValue = tryParseInt(temp_tokens.poll());
			Item e = new Item(currentValue, 6);
			items.push(e);
		}
		else if (currentValue.equals(CLOSE_PAREN_SIGN)) {
			currentValue = tryParseInt(temp_tokens.poll());
			Item e = new Item(currentValue, 11);
			items.push(e);
		}
		else {
			invalidIndicator();
		}
	}
	
	private void ruleNine() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			invalidIndicator();
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			invalidIndicator();
		}
		else if (currentValue.equals(PLUS_SIGN) || currentValue.equals(MINUS_SIGN) || currentValue.equals(CLOSE_PAREN_SIGN) || currentValue.equals(DOLLAR_SIGN)) {
			// REDUCE
			long first = (long) items.pop().getValue();
			String middleSign = (String) items.pop().getValue();
			long second = (long) items.pop().getValue();
			if (middleSign.equals(PLUS_SIGN)) {
				Item e = new Item(second+first, E_state);
				items.push(e);
			}
			else if (middleSign.equals(MINUS_SIGN)) {
				Item e = new Item(second-first, E_state);
				items.push(e);
			}
			else if (middleSign.equals(CLOSE_PAREN_SIGN)) {
				Item e = new Item(second*first, E_state);
				items.push(e);
			}
			else if (middleSign.equals(DOLLAR_SIGN)) {
				Item e = new Item(second/first, E_state);
				items.push(e);
			}
		}
		else if (currentValue.equals(MULT_SIGN) || currentValue.equals(DIV_SIGN)) {
			currentValue = tryParseInt(temp_tokens.poll());
			Item e = new Item(currentValue, 7);
			items.push(e);
		}
		else {
			invalidIndicator();
		}
	}

	private void ruleTen() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			invalidIndicator();
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			invalidIndicator();
		}
		else {
			long first = (long) items.pop().getValue();
			String middleSign = (String) items.pop().getValue();
			long second = (long) items.pop().getValue();
			if (middleSign.equals(PLUS_SIGN)) {
				Item e = new Item(second+first, T_state);
				items.push(e);
			}
			else if (middleSign.equals(MINUS_SIGN)) {
				Item e = new Item(second-first, T_state);
				items.push(e);
			}
			else if (middleSign.equals(CLOSE_PAREN_SIGN)) {
				Item e = new Item(second*first, T_state);
				items.push(e);
			}
			else if (middleSign.equals(DOLLAR_SIGN)) {
				Item e = new Item(second/first, T_state);
				items.push(e);
			}
			else if (middleSign.equals(MULT_SIGN)) {
				Item e = new Item(second*first, T_state);
				items.push(e);
			}
			else if (middleSign.equals(DIV_SIGN)) {
				Item e = new Item(second/first, T_state);
				items.push(e);
			}
		}
	}

	private void ruleEleven() {
		Object currentValue = tryParseInt(temp_tokens.peek());
		if (currentValue instanceof Number) {
			invalidIndicator();
		}
		else if (currentValue.equals(OPEN_PAREN_SIGN)) {
			invalidIndicator();
		}
		else {
			items.pop();
			Object expression = (Object) items.pop().getValue();
			items.pop();
			Item e = new Item(expression, F_state);
			items.push(e);
			}
		}

	public static void main(String[] args) {
		if(args.length == 0 || args[0].length() < 3) {
			System.out.println("You must provide a valid expression as an argument");
		}
		String input = args[0];
		input.replaceAll("\\s+","");
		LR1 parse = new LR1(input);
		parse.algorithm();
	}
}
