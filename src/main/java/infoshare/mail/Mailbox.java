package infoshare.mail;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mailbox {

    private List<Message> messages = new ArrayList();

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message m) {
        messages.add(m);
    }

    public Mailbox(List<Message> messages) {
        this.messages = messages;
    }

    public Mailbox(Message ... messages) {
//        this.messages = new ArrayList<>(Arrays.asList(messages));
        this.messages = Stream.of(messages).collect(Collectors.toList());
    }
 }
