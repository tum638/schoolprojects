
import java.util.Vector;

import structure5.Assert;
import structure5.SinglyLinkedList;
import structure5.StackList;

/**
 * An implementation of a basic PostScript interpreter.
 * STUDENTS, FILL IN THE CONTENTS OF THIS COMMENT WITH A MEANINGFUL
 * BUT CONCISE DESCRIPTION OF THE CLASS
 */
public class Interpreter {
	// the table containing symbols
	private SymbolTable symbols;
	// the stack for tokens
	private StackList<Token> stack;

	/**
	 * Constructor for the interpreter class
	 */
	public Interpreter() {
		symbols = new SymbolTable();
		stack = new StackList<>();
	}

	/**
	 * Pops two consecutive elements from the stack
	 * 
	 * @returns a list of two tokens
	 */
	public Token[] popTwo() {
		// assert that the stack is non empty
		assertStackNonEmpty();
		Token[] tokens = new Token[2]; // initialize the array
		Token first = this.pop(); // pop the first element of the stack
		Token second = this.pop(); // pop the second element of the stack
		// append tokens to the list
		tokens[0] = first;
		tokens[1] = second;
		return tokens;
	}

	/**
	 * Checks if the stack contains at least two tokens
	 * 
	 * @return true if there are two tokens in the stack, otherwise returns false
	 */
	public boolean containsTwo() {
		try {
			// attempting to pop and peek for two elements from the list
			Token first = this.pop();
			this.peek();
			this.push(first);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * A function to push two tokens consecutively to the stack
	 * 
	 * @param first  a token to be pushed to the stack
	 * @param second a different token to be pushed to the stack
	 */
	public void pushTwo(Token first, Token second) {
		this.push(second);
		this.push(first);
	}

	/**
	 * The backbone function for handling tokens, does the different operations for
	 * the different commands
	 * 
	 * @param toks   an array containing the two top most tokens from the stack
	 * @param symbol the string representation of the command to be executed
	 */
	public void handleSwitchCases(Token[] toks, String symbol) {
		// check case by case for the operation to do depending on the symbol
		switch (symbol) {
			case "add":
				this.push(this.add(toks[0], toks[1]));
				break;
			case "mul":
				this.push(this.mul(toks[0], toks[1]));
				break;
			case "div":
				this.push(this.div(toks[1], toks[0]));
				break;
			case "sub":
				this.push(this.sub(toks[1], toks[0]));
				break;
			case "exch":
				this.pushTwo(toks[0], toks[1]);
				this.exch();
				break;
			case "eq":
				this.push(this.eq(toks[0], toks[1]));
				break;
			case "lt":
				this.push(this.lt(toks[1], toks[0]));
				break;
			case "if":
				this.fi(toks[1], toks[0]);
				break;
			case "ne":
				this.push(this.ne(toks[0], toks[1]));
				break;
			default:
				this.pushTwo(toks[0], toks[1]);
				System.out.println("Unrecognized token");
				System.exit(0);
		}
	}

	/**
	 * a different function to handle more complicated cases of commands
	 * 
	 * @param token a token to be handled
	 */
	public void handleSymbol(Token token) {
		// check case by case for the operation to do for the symbol
		String symbol = token.getSymbol();

		if (symbol.equals("pstack")) {
			this.pstack();
			return;
		}
		if (symbol.equals("ptable")) {
			this.ptable();
			return;
		}
		if (symbol.equals("dup")) {
			Token originalToken = this.peek();
			this.push(this.dup(originalToken));
			return;
		}
		// equivalent of asserting that the stack has at least two elements
		if (!containsTwo()) {
			return;
		}
		Token[] toks = popTwo();
		handleSwitchCases(toks, symbol);
	}

	/**
	 * Designed to conditionally handle symbols, inteprets a symbol according to the
	 * type it is.
	 * 
	 * @param next the symbol token to be intepreted
	 * @param r    the rest of the reader object
	 */
	public void interpretSymbol(Token next, Reader r) {
		// get the string representation of the symbol
		String nextSymbol = next.getSymbol();
		// if the string is quit, do nothing and return
		if (nextSymbol.equals("quit")) {
			return;
			// if the symbol starts with a forward slash, its a definition, add it to the
			// symbols table
		} else if (nextSymbol.charAt(0) == '/') {
			Token value = r.next();
			r.next();
			symbols.add(nextSymbol.substring(1), value);
			intrepret(r);
			// check if the symbol is in the table
		} else if (symbols.contains(nextSymbol)) {
			Token valueSymbol = symbols.get(nextSymbol);
			Reader read = new Reader(valueSymbol);
			// interpret the symbol separately
			intrepret(read);
			// continue with the rest of the intepretation
			intrepret(r);
			// otherwise, check for the predefined operations
		} else {
			handleSymbol(next);
			intrepret(r);
		}
	}

	/**
	 * The entry point to the interpretation process
	 * 
	 * @param r a reader object
	 */
	public void intrepret(Reader r) {
		// are there any more tokens? if not we are done. return.
		if (!r.hasNext()) {
			return;
		}
		// get the next token
		Token next = r.next();
		// is the token a symbol? if yes, interpret it as such.
		if (next.isSymbol()) {
			interpretSymbol(next, r);
		}
		// is the token a procedure?
		if (next.isProcedure()) {
			// push it to the stack
			this.push(next);
			// continue intepreting
			intrepret(r);
		}
		// is the token a number or a boolean?
		if (next.isNumber() || next.isBoolean()) {
			// push it to the stack
			this.push(next);
			// continue interpreting
			intrepret(r);
		}

	}

	/**
	 * pushes a token to the top of the stack
	 * 
	 * @param t a token
	 */
	public void push(Token t) {
		this.stack.push(t);
	}

	/**
	 * checks for the token at the top of the stack without removing it
	 * 
	 * @returns the token at the top of the stack
	 */
	public Token peek() {

		return this.stack.peek();
	}

	/**
	 * a function that inteprets a token if the boolean is true, otherwise it does
	 * nothing.
	 * 
	 * @param boolea    a boolean specifying whether to execute the token a not
	 * @param procedure a token to be intepreted or not
	 */
	public void fi(Token boolea, Token procedure) {
		Assert.pre(boolea.isBoolean() == true, "At least one of the tokens should be boolean");
		Boolean bool = boolea.getBoolean();
		// should the token be intepreted? if not do nothing
		if (!bool) {
			return;
		}
		// add the token to the reader and interpret it
		Reader r = new Reader(procedure);
		this.intrepret(r);
	}

	/**
	 * gets the numerical values of tokens and places them in an array.
	 * 
	 * @param thisT a numerical token
	 * @param thatT a numerical token
	 * @returns an array containing the numerical values of the tokens
	 */
	public Double[] getNumbers(Token thisT, Token thatT) {
		// assert that both tokens are numbers
		assertNumbers(thisT, thatT);
		Double[] numbers = new Double[2];
		// get the number value of the first number
		Double firstNumber = thisT.getNumber();
		// get the number value of the second number
		Double secondNumber = thatT.getNumber();
		numbers[0] = firstNumber;
		numbers[1] = secondNumber;
		return numbers;

	}

	/**
	 * checks if two tokens are not equal
	 * 
	 * @param thisT a token to be checked
	 * @param thatT another token to be checked
	 * @return true if tokens are not equal, otherwise return false
	 */
	public Token ne(Token thisT, Token thatT) {
		Token notEqualTo = new Token(!thisT.equals(thatT));
		return notEqualTo;
	}

	/**
	 * calculates the the sum of two numerical tokens
	 * 
	 * @param thisT a numerical token
	 * @param thatT a numerical token
	 * @return the token representing the product between two tokens
	 */
	public Token add(Token thisT, Token thatT) {
		// assert that both tokens are numbers
		assertNumbers(thisT, thatT);
		// get the numbers from the tokens
		Double[] n = getNumbers(thisT, thatT);
		// create a new token with the values summed values
		Token addedToken = new Token(n[0] + n[1]);
		return addedToken;
	}

	/**
	 * a function that calculates the product between two numerical tokens
	 * 
	 * @param thisT a token
	 * @param thatT a token
	 * @returns a token representing the product between the two tokens
	 */
	public Token mul(Token thisT, Token thatT) {
		// assert that both tokens are numbers
		assertNumbers(thisT, thatT);
		// get the numbers from the tokens
		Double[] n = getNumbers(thisT, thatT);
		// create new token with multiplied values
		Token multipliedToken = new Token(n[0] * n[1]);
		return multipliedToken;
	}

	/**
	 * asserts that both tokens are numbers.
	 * 
	 * @param thisT a token to be checked
	 * @param thatT a different token to be checked
	 */
	public void assertNumbers(Token thisT, Token thatT) {
		Assert.pre(thisT.isNumber(), "The first token must be a number");
		Assert.pre(thatT.isNumber(), "The second token must be a number");
	}

	/**
	 * calculates the difference between two numerical tokens
	 * 
	 * @param thisT a token to subtract thatT from
	 * @param thatT a token to subtract from thisT
	 * @returns the token representing (thisT - thatT)
	 */
	public Token sub(Token thisT, Token thatT) {
		// assert that both tokens are numbers
		assertNumbers(thisT, thatT);
		// get the numbers from the tokens
		Double[] n = getNumbers(thisT, thatT);
		// create new token with subtracted values N.B the second parameter is
		// subtracted from the first
		Token subtractedToken = new Token(n[0] - n[1]);
		return subtractedToken;
	}

	/**
	 * 
	 * @param symbol a string symbol
	 * @param value  the value represented by the symbol
	 */
	public void def(Token symbol, Token value) {
		Assert.pre(symbol.isSymbol(), "one of the tokens must be a symbol");
		String stringSymbol = symbol.getSymbol();
		symbols.add(stringSymbol, value);
	}

	/**
	 * calculates the quotient from the divisor and dividend
	 * 
	 * @param thisT the dividend
	 * @param thatT the divisor
	 * @returns a token representing the quotient obtained after dividing
	 */
	public Token div(Token thisT, Token thatT) {
		// assert that both tokens are numbers
		assertNumbers(thisT, thatT);
		// get the numbers from the tokens
		Double[] n = getNumbers(thisT, thatT);
		// create a new token of the qoutient from the divisor and the divident N.B The
		// divident is the first parameter to the function, the divisor is the second
		Token quotientToken = new Token(n[0] / n[1]);
		return quotientToken;
	}

	/**
	 * duplicates the token it is provided with
	 * 
	 * @param thisT the token to duplicate
	 * @returns the duplicate of the token
	 */
	public Token dup(Token thisT) {
		Double tokenValue = thisT.getNumber();
		Token newToken = new Token(tokenValue);
		return newToken;
	}

	/**
	 * Asserts that the stack is non empty
	 */
	public void assertStackNonEmpty() {
		Assert.pre(!this.stack.isEmpty(), "The stack must not be empty");
	}

	/**
	 * a function that exchanges the positions of the two topmost elements in a
	 * stack
	 */
	public void exch() {
		// assert that the stack is not empty
		assertStackNonEmpty();
		Token first = this.pop();
		Token second = this.pop();
		this.push(first);
		this.push(second);
	}

	/**
	 * a function that checks to see if thisT is equal to thatT
	 * 
	 * @param thisT a token to compare
	 * @param thatT a token to compare
	 * @returns true if thisT is equal to thatT, else false.
	 */
	public Token eq(Token thisT, Token thatT) {
		Token isEqual = new Token(thisT.equals(thatT));
		return isEqual;
	}

	// public boolean ne()

	/**
	 * checks if thisT is less than thatT
	 * 
	 * @param thisT first token to the inputs
	 * @param thatT second token to the inputs
	 * @returns true if thisT is less than thatT, otherwise returns false
	 */
	public Token lt(Token thisT, Token thatT) {
		// assert that both tokens are numbers
		assertNumbers(thisT, thatT);
		Token isLessThan = new Token(thisT.getNumber() < thatT.getNumber());
		return isLessThan;
	}

	/**
	 * removes the element on top of the stack
	 * 
	 * @returns the token on top of the stack
	 */
	public Token pop() {
		return this.stack.pop();
	}

	/**
	 * prints the stack to the console
	 */
	public void pstack() {
		System.out.print("Top [ ");
		for (Token t : this.stack) {
			System.out.print(t + ", ");
		}
		System.out.println("] Bottom");
	}

	/**
	 * a function to print out the contents os the symbol table
	 */
	public void ptable() {
		System.out.println(symbols.toString());
	}

	/**
	 * entry point to the program
	 * 
	 * @param arg an array containing user input
	 */
	public static void main(String[] args) {
		Reader r = new Reader();
		Interpreter interpreter = new Interpreter();

		interpreter.intrepret(r);
		
	}
}
