import infoshare.mail.Message;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MessageBuilders {

    public static final String TEST_EMAIL = "test@test.pl";

    // doesn't work with Java 9
    @Ignore
    @Test
    public void lombok_builder() {
        Message message = null;
        // uncomment on Java 8
        //message = Message.builder().from(TEST_EMAIL).to(TEST_EMAIL).build();
        assertThat(message).isNotNull();
    }

    @Test
    public void standard_builder() {
        Message message = new Message.StandardBuilder().build();
        assertThat(message).isNotNull();
    }

    @Test
    public void converting_builder() {
        Message message = Message.convertingBuilder()
                .base64EncodedMessage("test")
                .sentNow()
                .to(TEST_EMAIL)
                .from(TEST_EMAIL)
                .build();
        System.out.println(message.toString());
        assertThat(message).isNotNull();
        assertThat(message.toString()).contains("dGVzdA==");
    }

    @Test
    public void validating_builder() {

        Message message = new Message.GuavaValidatingBuilder()
                .from(TEST_EMAIL)
                .to(TEST_EMAIL)
                .build();

        assertThat(message).isNotNull();
        assertThat(message.toString()).containsIgnoringCase(TEST_EMAIL);

    }

    @Test
    public void validating_builder_should_not_allow_to_build_invalid_message() {
        Message.GuavaValidatingBuilder builder = new Message.GuavaValidatingBuilder()
                .from(null)
                .to(TEST_EMAIL);

        assertThatThrownBy(()->builder.build()).isInstanceOf(Exception.class);
    }


}
