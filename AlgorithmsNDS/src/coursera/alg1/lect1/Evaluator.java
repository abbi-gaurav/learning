package coursera.alg1.lect1;
import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.OPERATOR;
import coursera.alg1.lect2.Stack;
import edu.princeton.cs.introcs.StdOut;

public class Evaluator {
	public static void main(String[] args) {
		doEvaluation(args);
	}

	private static void doEvaluation(String[] args) {
		Stack<OPERATOR> operatorStack = new LinkedStack<OPERATOR>();
		Stack<Double> operandStack = new LinkedStack<Double>();
		
		for (String read : args) {
			
			if (read.equals("(")){
				;
			}else if (read.equals("+") || read.equals("*") || read.equals("/")
					|| read.equals("-")) {
				operatorStack.push(getOperator(read.toCharArray()[0]));
			}else  if (read.equals(")")) {
				Double val1 = operandStack.pop();
				Double val2 = operandStack.pop();
				OPERATOR operator = operatorStack.pop();
				operandStack.push(operator.compute(val1, val2));
			}else{
				operandStack.push(Double.parseDouble(read));
			}
		}
		StdOut.println(operandStack.pop());
	}

	private static OPERATOR getOperator(char c) {
		switch (c) {
		case '+':
			return OPERATOR.PLUS;
		case '-':
			return OPERATOR.MINUS;
		case '*':
			return OPERATOR.MULTIPLY;
		default:
			return OPERATOR.DIVIDE;
		}
	}
}
