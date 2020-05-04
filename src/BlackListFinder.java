import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Network Programming - HW1-2020
 *
 * @author Antoniya Tsoneva
 */

public class BlackListFinder {
    public static final String HOST_NAME = "zen.spamhaus.org";

    public static void main(String[] args) {
        System.out.println("Enter IP addresses of servers for checking:");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.next();

            if (userInput.equals("exit")) {
                System.out.println("Exiting the program.");
                return;
            }

            String revertedAddress = revertAddress(userInput) + HOST_NAME;

            try {
                InetAddress[] addresses = InetAddress.getAllByName(revertedAddress);
                for (InetAddress inetAddress : addresses) {
                    System.out.print(String.format("The IP address: %s is found in the following Spamhaus public IP zone: ", userInput));
                    printReturnCode(inetAddress.getAddress());
                }
            } catch (UnknownHostException e) {
                System.out.println(String.format("The IP address: %s is NOT found in the Spamhaus blacklists.", userInput));
            }
        }
    }

    private static String revertAddress(String address) {
        String[] octets = address.split("\\.");
        String revertedAddress = "";
        for (int i = octets.length - 1; i >= 0; i--) {
            revertedAddress = revertedAddress.concat(octets[i] + ".");
        }

        return revertedAddress;
    }

    private static void printReturnCode(byte[] address) {
        int fourthOctetValue = address[3];
        switch (fourthOctetValue) {
            case 2:
                System.out.println("'127.0.0.2 - SBL - Spamhaus SBL Data'");
                break;
            case 3:
                System.out.println("127.0.0.3 - SBL - Spamhaus SBL CSS Data");
                break;
            case 4:
                System.out.println("'127.0.0.4 - XBL - CBL Data'");
                break;
            case 9:
                System.out.println("'127.0.0.9 - SBL - Spamhaus DROP/EDROP Data'");
                break;
            case 10:
                System.out.println("'127.0.0.10 - PBL - ISP Maintained'");
                break;
            case 11:
                System.out.println("'127.0.0.11 - PBL - Spamhaus Maintained'");
                break;
        }
    }
}
  