import java.util.HashMap;
import java.util.Map;

public class Solution {
    // This function calculates the maximum number of clean sock pairs that can be obtained
    // given a washing machine capacity, an array of clean socks, and an array of dirty socks.
    public static int solution(int k, int[] c, int[] d) {
        int capacity = k;
        Map<Integer, Integer> clean = new HashMap<>(); // Map to store the count of each color of clean socks
        Map<Integer, Integer> dirty = new HashMap<>(); // Map to store the count of each color of dirty socks

        // Count the occurrences of each color in the array of clean socks
        for (int s : c) {
            clean.put(s, clean.getOrDefault(s, 0) + 1);
        }

        // Count the occurrences of each color in the array of dirty socks
        for (int s : d) {
            dirty.put(s, dirty.getOrDefault(s, 0) + 1);
        }

        int pairs = 0; // Variable to store the total number of clean sock pairs

        // Pack the pairs of clean socks
        for (Map.Entry<Integer, Integer> entry : clean.entrySet()) {
            int color = entry.getKey();
            int quantity = entry.getValue();
            pairs += quantity / 2; // Add the maximum number of pairs that can be obtained from this color
            clean.put(color, quantity % 2); // Update the count of remaining socks after packing pairs
        }

        // If the washing machine has no capacity, return the total clean sock pairs
        if (capacity == 0) {
            return pairs;
        }

        // Remove colors with no remaining clean socks
        clean.entrySet().removeIf(entry -> entry.getValue() == 0);

        // Pair single clean socks with single dirty socks until the washing machine is full
        for (int color : clean.keySet()) {
            if (dirty.containsKey(color) && dirty.get(color) > 0) {
                dirty.put(color, dirty.get(color) - 1);
                pairs++;
                capacity--;
                if (capacity == 0) {
                    return pairs;
                }
            }
        }

        int capPairs = capacity / 2;

        // If the washing machine has no more capacity for pairs, return the total clean sock pairs
        if (capPairs == 0) {
            return pairs;
        }

        // Wash remaining dirty sock pairs until the washing machine is full
        for (int value : dirty.values()) {
            int toWash = Math.min(value / 2, capPairs);
            pairs += toWash;
            capPairs -= toWash;
            if (capPairs == 0) {
                return pairs;
            }
        }

        // Return the total number of clean sock pairs
        return pairs;
    }

    // Main method to test the solution with sample inputs
    public static void main(String[] args) {
        System.out.println(solution(2, new int[]{1, 2, 1, 1}, new int[]{1, 4, 3, 2, 4})); // should be 3
        System.out.println(solution(0, new int[]{1, 2, 3, 4}, new int[]{3, 2, 1, 5})); // should be zero
    }
}
