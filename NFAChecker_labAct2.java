import java.util.*;

public class NFAChecker_labAct2 {
    
    private static Map<Integer, Map<Character, Set<Integer>>> transitions;
    
    static {
        transitions = new HashMap<>();
        
        //state 0 (q0) - Start    
        Map<Character, Set<Integer>> state0 = new HashMap<>();
        state0.put('a', new HashSet<>(Arrays.asList(0, 1))); 
        state0.put('b', new HashSet<>(Arrays.asList(0)));     
        transitions.put(0, state0);
        
        //state 1
        Map<Character, Set<Integer>> state1 = new HashMap<>();
        state1.put('b', new HashSet<>(Arrays.asList(2)));     
        transitions.put(1, state1);
        
        //state 2 (q2) - Accept
        Map<Character, Set<Integer>> state2 = new HashMap<>();
        state2.put('a', new HashSet<>(Arrays.asList(2)));     
        state2.put('b', new HashSet<>(Arrays.asList(2)));     
        transitions.put(2, state2);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                ==== NFA Checker ====
                Language: Strings containing 'ab' as substring
                Type 'exit' to close
                """);
        
        while (true) {
            System.out.print("Enter a string (a and b): ");
            String input = scanner.nextLine();
            
            //Exit the program
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting... Goodbye...");
                break;
            }

            //Validate the input
            if (!isValid(input)) {
                System.out.println("Invalid input... Input character (a and b) only...\n");
                continue; 
            }

            //Check if input is empty
            if (input.isEmpty()) {
                System.out.println("Invalid input! String cannot be empty...\n");
                continue;
            }
            
            boolean isAccepted = check(input);
            
            if (isAccepted) {
                System.out.println("Result: Accepted\n");
            } else {
                System.out.println("Result: Rejected\n");
            }
        }
        scanner.close();
    }
    
    //method to simulate NFA using recursion
    public static boolean check(String input) {
        Set<String> visited = new HashSet<>();
        return explore(0, 0, input, visited);
    }
    
    //method to recursively explore all possible NFA paths
    private static boolean explore(int state, int position, String input, Set<String> visited) {
        
        String key = state + "-" + position;
        if (visited.contains(key)) {
            return false; 
        }
        visited.add(key);
        
        if (position == input.length()) {
            return state == 2; 
        }
        
        
        char c = input.charAt(position);
        
        
        Set<Integer> nextStates = getNextStates(state, c);
        
        
        for (int nextState : nextStates) {
            if (explore(nextState, position + 1, input, visited)) {
                return true; 
            }
        }
        
        return false; 
    }
    
    
    private static Set<Integer> getNextStates(int state, char c) {
        if (transitions.containsKey(state) && transitions.get(state).containsKey(c)) {
            return transitions.get(state).get(c);
        }
        return new HashSet<>(); 
    }
    
    // Input validation
    public static boolean isValid(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != 'a' && c != 'b') {
                return false;
            }
        }
        return true;
    }
}