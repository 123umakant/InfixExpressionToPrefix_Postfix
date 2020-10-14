import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class InfixToPostfix {

    public static final int                LEFT_ASSOC      = 0;
    public static final int                RIGHT_ASSOC     = 1;
    public static final Map<String, int[]> ARITH_OPERATORS = new HashMap<String, int[]>();
    public static final Map<String, int[]> REL_OPERATORS   = new HashMap<String, int[]>();
    public static final Map<String, int[]> LOG_OPERATORS   = new HashMap<String, int[]>();
    public static final Map<String, int[]> OPERATORS       = new HashMap<String, int[]>();

    static {
        ARITH_OPERATORS.put("+", new int[] { 25, LEFT_ASSOC });
        ARITH_OPERATORS.put("-", new int[] { 25, LEFT_ASSOC });
        ARITH_OPERATORS.put("*", new int[] { 30, LEFT_ASSOC });
        ARITH_OPERATORS.put("/", new int[] { 30, LEFT_ASSOC });
        ARITH_OPERATORS.put("%", new int[] { 30, LEFT_ASSOC });
        ARITH_OPERATORS.put("^", new int[] { 35, RIGHT_ASSOC });
        ARITH_OPERATORS.put("**", new int[] { 30, LEFT_ASSOC });

        REL_OPERATORS.put("<", new int[] { 20, LEFT_ASSOC });
        REL_OPERATORS.put("<=", new int[] { 20, LEFT_ASSOC });
        REL_OPERATORS.put(">", new int[] { 20, LEFT_ASSOC });
        REL_OPERATORS.put(">=", new int[] { 20, LEFT_ASSOC });
        REL_OPERATORS.put("==", new int[] { 20, LEFT_ASSOC });
        REL_OPERATORS.put("!=", new int[] { 20, RIGHT_ASSOC });

        LOG_OPERATORS.put("!", new int[] { 15, RIGHT_ASSOC });

        LOG_OPERATORS.put("&&", new int[] { 10, LEFT_ASSOC });

        LOG_OPERATORS.put("||", new int[] { 5, LEFT_ASSOC });

        LOG_OPERATORS.put("EQV", new int[] { 0, LEFT_ASSOC });
        LOG_OPERATORS.put("NEQV", new int[] { 0, LEFT_ASSOC });

        OPERATORS.putAll(ARITH_OPERATORS);
        OPERATORS.putAll(REL_OPERATORS);
        OPERATORS.putAll(LOG_OPERATORS);
    }

    public static void main(String args[]) {
        String inputExpression = "( ( A=3 || B=4 ) && ( C=3 || D=5 )";

        String[] input = inputExpression.split(" ");
        List<String> output = infixToRPN(input);

        System.out.println(output.toString());

    }



    private static boolean isAssociative(String token, int type) {
        if (!isOperator(token)) {
            System.out.println("");
        }
        if (OPERATORS.get(token)[1] == type) {
            return true;
        }
        return false;
    }

    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    private static int cmpPrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            System.out.println("");
        }
        return OPERATORS.get(token1)[0] - OPERATORS.get(token2)[0];
    }

    private static ArrayList<String> infixToRPN(String[] inputTokens) {
        ArrayList<String> out = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();

        for (String token : inputTokens) {
            if (isOperator(token)) {

                while (!stack.empty() && isOperator(stack.peek())) {

                    if ((isAssociative(token, LEFT_ASSOC) && cmpPrecedence(token, stack.peek()) <= 0)
                            || (isAssociative(token, RIGHT_ASSOC) && cmpPrecedence(token, stack.peek()) < 0)) {
                        out.add(stack.pop());
                        continue;
                    }
                    break;
                }

                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {

                while (!stack.empty() && !stack.peek().equals("(")) {
                    out.add(stack.pop());
                }
                stack.pop();
            } else {
                out.add(token);
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        return out;
    }
}