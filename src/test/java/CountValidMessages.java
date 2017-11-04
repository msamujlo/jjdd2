import infoshare.mail.Mailbox;
import infoshare.mail.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CountValidMessages {

    public static final String ALICE = "alice@infoshare.com";
    public static final String BOB = "bob@infoshare.com";
    private Mailbox mailbox;

    @Before
    public void setup() {
        Message.StandardBuilder mb = new Message.StandardBuilder();

        mailbox = new Mailbox(
                mb
                        .from(ALICE)
                        .to(BOB)
                        .build(),
                mb
                        .from(BOB)
                        .to(ALICE)
                        .build(),
                mb
                        .from(ALICE)
                        .to(BOB)
                        .build(),
                mb.build(),
                mb.build()
        );
    }

    @Test
    public void filter_and_count(){
        Map<String, Long> messagesBySender = mailbox
                .getMessages()
                .stream()
                .filter(m->m.getFrom()!=null)
                .collect(
                        Collectors.groupingBy(Message::getFrom, Collectors.counting())
                );

        assertThat(messagesBySender.keySet()).containsOnly(ALICE,BOB);
        assertThat(messagesBySender.get(ALICE)).isEqualTo(2);
        assertThat(messagesBySender.get(BOB)).isEqualTo(1);

    }

}
