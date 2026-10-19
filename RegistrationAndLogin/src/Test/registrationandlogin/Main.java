package Test.registrationandlogin;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== REGISTRATION =====");

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter South African cell phone number: ");
        String cellPhoneNumber = scanner.nextLine();

        login user = new login(
                username,
                password,
                cellPhoneNumber,
                firstName,
                lastName
        );

        System.out.println();
        System.out.println(user.registerUser());

        if (user.checkUserName()
                && user.checkPasswordComplexity()
                && user.checkCellPhoneNumber()) {

            System.out.println("\n===== LOGIN =====");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            System.out.println(
                    user.returnLoginStatus(
                            loginUsername,
                            loginPassword
                    )
            );

            if (user.loginUser(loginUsername, loginPassword)) {

                System.out.println("\nWelcome to QuickChat.");

                System.out.print(
                        "How many messages would you like to send? "
                );

                int maxMessages = readNumber(scanner);

                while (maxMessages < 0) {
                    System.out.print("Enter zero or more messages: ");
                    maxMessages = readNumber(scanner);
                }

                int messageCount = 0;
                boolean running = true;

                while (running) {

                    System.out.println("\n===== QUICKCHAT MENU =====");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");

                    System.out.print("Choose an option: ");
                    int option = readNumber(scanner);

                    switch (option) {

                        case 1:

                            if (messageCount >= maxMessages) {
                                System.out.println(
                                        "You have reached your message limit."
                                );
                                break;
                            }

                            System.out.print(
                                    "Enter recipient number (e.g. +27821234567): "
                            );
                            String recipient = scanner.nextLine();

                            System.out.print("Enter your message: ");
                            String text = scanner.nextLine();

                            Message message = new Message(
                                    recipient,
                                    text,
                                    messageCount + 1
                            );

                            if (!message.isValidRecipient()) {
                                System.out.println(
                                        message.checkRecipientCell()
                                );
                                break;
                            }

                            if (!message.isValidMessage()) {
                                System.out.println(
                                        message.checkMessageLength()
                                );
                                break;
                            }

                            System.out.println("\n" + message.printMessage());

                            System.out.println("\nChoose message action:");
                            System.out.println("1) Send Message");
                            System.out.println("2) Store Message");
                            System.out.println("3) Disregard Message");

                            System.out.print("Choose: ");
                            int action = readNumber(scanner);

                            String result;

                            switch (action) {
                                case 1:
                                    result = message.sentMessage("send");
                                    messageCount++;
                                    break;

                                case 2:
                                    result = message.sentMessage("store");
                                    Message.storeMessagesToJSON();
                                    messageCount++;
                                    break;

                                case 3:
                                    result = message.sentMessage("disregard");
                                    break;

                                default:
                                    result = "Invalid action.";
                            }

                            System.out.println(result);
                            break;

                        case 2:
                            System.out.println(
                                    "Coming Soon."
                            );
                            break;

                        case 3:
                            Message.storeMessagesToJSON();
                            System.out.println(
                                    "Thank you for using QuickChat!"
                            );
                            running = false;
                            break;

                        default:
                            System.out.println(
                                    "Invalid option. Please try again."
                            );
                    }
                }

            } else {
                System.out.println(
                        "Login unsuccessful. QuickChat access denied."
                );
            }

        } else {
            System.out.println(
                    "Registration unsuccessful. Please try again."
            );
        }

        scanner.close();
    }

    private static int readNumber(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.print(
                        "Please enter a valid number: "
                );
            }
        }
    }
}
