import java.util.Scanner;

public class HammingCode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input

        System.out.print("Enter the data to be transmitted: ");
        String data = scanner.next(); // Read user input as a string
        int m = data.length(); // Calculate the length of the input data

        StringBuilder reversedData = new StringBuilder(); // StringBuilder for storing reversed data
        for (int i = m - 1; i >= 0; i--) {
            reversedData.append(data.charAt(i)); // Reverse the input data and store it
        }

        int r = 0; // Initialize the number of redundant bits
        int power = 1; // Initialize the power for calculating redundant bits
        while (power < (m + r + 1)) { // Calculate the number of redundant bits required using Hamming code
            r++;
            power *= 2;
        }

        StringBuilder msg = new StringBuilder(); // StringBuilder for storing message with redundant bits
        int curr = 0; // Initialize a variable to track current data bit position
        for (int i = 1; i <= m + r; i++) {
            if ((i & (i - 1)) != 0) { // Check if 'i' is not a power of 2 (data bit position)
                msg.append(reversedData.charAt(curr++)); // Add data bit to the message
            } else {
                msg.append('n'); // Add 'n' as a placeholder for redundant bit position
            }
        }

        int bit = 0; // Initialize variable to track current redundant bit position
        for (int i = 1; i <= m + r; i *= 2) {
            int count = 0; // Initialize count for parity calculation
            for (int j = i + 1; j <= m + r; j++) {
                if ((j & (1 << bit)) != 0) {
                    if (msg.charAt(j - 1) == '1') count++; // Count '1' bits for parity
                }
            }
            if ((count & 1) != 0) msg.setCharAt(i - 1, '1'); // Set parity bit to '1' for odd count
            else msg.setCharAt(i - 1, '0'); // Set parity bit to '0' for even count
            bit++; // Move to the next redundant bit position
        }

        System.out.print("The data packet to be sent is: ");
        for (int i = m + r - 1; i >= 0; i--) { // Print the data packet to be sent
            System.out.print(msg.charAt(i) + " "); // Print the message with redundant bits
        }
        System.out.println();

        System.out.print("Enter the position to introduce an error (1-" + (m + r) + "): ");
        int errorPosition = scanner.nextInt(); // Read the error position from user input
        if (errorPosition >= 1 && errorPosition <= m + r) { // Check if the error position is valid
            msg.setCharAt(errorPosition - 1, (msg.charAt(errorPosition - 1) == '0') ? '1' : '0');
            // Introduce error at specified position by toggling the bit
            System.out.println("Error introduced at position " + errorPosition + ".");
            System.out.print("Transmitted data with error: ");
            for (int i = m + r - 1; i >= 0; i--) { // Print transmitted data with error
                System.out.print(msg.charAt(i) + " ");
            }
            System.out.println();
        } else {
            System.out.println("Invalid error position.");
        }

        StringBuilder ans = new StringBuilder(); // StringBuilder for storing error positions
        bit = 0; // Reset the bit variable to 0
        for (int i = 1; i <= m + r; i *= 2) {
            int count = 0; // Initialize count for parity calculation
            for (int j = i + 1; j <= m + r; j++) {
                if ((j & (1 << bit)) != 0) {
                    if (msg.charAt(j - 1) == '1') count++; // Count '1' bits for parity
                }
            }
            if ((count & 1) != 0) { // Check if parity is odd for any redundant bit
                if (msg.charAt(i - 1) == '1') ans.append('0'); // If original bit was '1', error detected
                else ans.append('1'); // If original bit was '0', error detected
            } else {
                if (msg.charAt(i - 1) == '0') ans.append('0'); // If original bit was '0', no error detected
                else ans.append('1'); // If original bit was '1', no error detected
            }
            bit++; // Move to the next redundant bit position
        }

        int error = 0; // Initialize variable to store error position
        for (int i = 0; i < r; i++) { // Calculate error position based on detected errors
            if (ans.charAt(i) == '1') {
                error += Math.pow(2, i);
            }
        }

        if (error != 0) { // Check if error is detected and correct if necessary
            System.out.println("Error detected in bit number " + error + ".");
            if (msg.charAt(error - 1) == '0') msg.setCharAt(error - 1, '1'); // Correct the error by toggling the bit
            else msg.setCharAt(error - 1, '0');
            System.out.println("Error corrected.");
        } else {
            System.out.println("No error detected.");
        }

        System.out.print("Corrected data packet received: ");
        for (int i = m + r - 1; i >= 0; i--) { // Print the corrected data packet received
            System.out.print(msg.charAt(i) + " ");
        }
        System.out.println();
    }
}