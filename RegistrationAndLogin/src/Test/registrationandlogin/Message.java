package Test.registrationandlogin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    private static final HashSet<String> usedIDs = new HashSet<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();
    private static final Random random = new Random();

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int messageNumber;

    public Message(String recipient, String messageText, int messageNumber) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        String id;
        do {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                builder.append(random.nextInt(10));
            }
            id = builder.toString();
        } while (!usedIDs.add(id));

        return id;
    }

    public String checkRecipientCell() {
        if (recipient == null ||
            !Pattern.matches("\\+?[0-9]{1,12}", recipient)) {
            return "Cell phone number is incorrectly formatted "
                    + "or does not contain an international code.";
        }
        return "Cell phone number successfully captured.";
    }

    public boolean isValidRecipient() {
        return recipient != null
                && Pattern.matches("\\+[0-9]{1,12}", recipient);
    }

    public String checkMessageLength() {
        if (messageText == null || messageText.length() > 250) {
            return "Message exceeds 250 characters.";
        }
        return "Message ready to send.";
    }

    public boolean isValidMessage() {
        return messageText != null
                && !messageText.trim().isEmpty()
                && messageText.length() <= 250;
    }

    public String createMessageHash() {
        if (messageText == null || messageText.trim().isEmpty()) {
            return "";
        }

        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return (messageID.substring(0, 2)
                + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(String choice) {
        if (!isValidRecipient() || !isValidMessage()) {
            return "Message cannot be processed. Check recipient and text.";
        }

        switch (choice.trim().toLowerCase()) {
            case "send":
                return "Message successfully sent.";

            case "store":
                storedMessages.add(this);
                return "Message successfully stored.";

            case "disregard":
                return "Press 0 to delete message.";

            default:
                return "Invalid option.";
        }
    }

    public String printMessage() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public static int returnTotalMessages() {
        return storedMessages.size();
    }

    private static String escapeJSON(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public static void storeMessagesToJSON() {
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {
                Message m = storedMessages.get(i);

                writer.write("  {\n");
                writer.write("    \"messageID\": \"" + escapeJSON(m.messageID) + "\",\n");
                writer.write("    \"recipient\": \"" + escapeJSON(m.recipient) + "\",\n");
                writer.write("    \"messageText\": \"" + escapeJSON(m.messageText) + "\",\n");
                writer.write("    \"messageHash\": \"" + escapeJSON(m.messageHash) + "\"\n");
                writer.write("  }");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("]\n");
            System.out.println("Messages saved to messages.json.");

        } catch (IOException e) {
            System.out.println("Error saving messages EEEr: " + e.getMessage());
        }
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }
}
