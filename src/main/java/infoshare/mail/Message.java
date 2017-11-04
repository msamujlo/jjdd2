package infoshare.mail;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import static com.google.common.base.Verify.verifyNotNull;

// - Lombok can generate builder at compile time
// - to use Lombok annotations turn on annotation processing in IntelliJ settings
@Builder
public class Message {

    private String from;
    private String to;
    private String message;
    private Date sentDate;

    public Message(String from, String to, String message, Date sentDate) {
        this.to = to;
        this.from = from;
        this.message = message;
        this.sentDate = sentDate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public Date getSentDate() {
        return sentDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", message='" + message + '\'' +
                ", sentDate=" + sentDate +
                '}';
    }

    public static ConvertingBuilder convertingBuilder() {
        return new ConvertingBuilder();
    }

    // - just a builder
    public static final class StandardBuilder {
        private String from;
        private String to;
        private String message;
        private Date sentDate;

        public StandardBuilder from(String from) {
            this.from = from;
            return this;
        }

        public StandardBuilder to(String to) {
            this.to = to;
            return this;
        }

        public StandardBuilder message(String message) {
            this.message = message;
            return this;
        }

        public StandardBuilder sentDate(Date sentDate) {
            this.sentDate = sentDate;
            return this;
        }

        public Message build() {
            Message message = new Message(from, to, this.message, sentDate);
            reset();
            return message;
        }

        private void reset() {
            this.from = this.to = this.message = null;
            this.sentDate = null;
        }

    }

    // - throws exception when object is invalid state
    public static final class GuavaValidatingBuilder {

        private String from;
        private String to;
        private String message;
        private Date sentDate;

        public GuavaValidatingBuilder from(String from) {
            this.from = from;
            return this;
        }

        public GuavaValidatingBuilder to(String to) {
            this.to = to;
            return this;
        }

        public GuavaValidatingBuilder message(String message) {
            this.message = message;
            return this;
        }

        public GuavaValidatingBuilder sentDate(Date sentDate) {
            this.sentDate = sentDate;
            return this;
        }

        public Message build() {
            verifyNotNull(this.from);
            verifyNotNull(this.to);
            return new Message(from, to, message, sentDate);
        }
    }


    // - converts between java 8 LocalDateTime and deprecated Date
    // - has default values for selected fields
    // - specialized methods make code more readable
    public static final class ConvertingBuilder {

        private String from = "example@example.com";
        private String to = "example@example.com";
        private String message;
        private Date sentDate;

        public ConvertingBuilder from(String from) {
            this.from = from;
            return this;
        }

        public ConvertingBuilder to(String to) {
            this.to = to;
            return this;
        }

        public ConvertingBuilder date(LocalDateTime d) {
            this.sentDate = Date.from(d.atZone(ZoneId.systemDefault()).toInstant());
            return this;
        }

        public ConvertingBuilder sentNow() {
            this.sentDate = Date.from(Instant.now());
            return this;
        }

        public ConvertingBuilder base64EncodedMessage(String message) {
            byte[] encoded = Base64.getEncoder().encode(message.getBytes());
            this.message = new String(encoded);
            return this;
        }

        public Message build() {
            verifyNotNull(this.from);
            verifyNotNull(this.to);
            Message message = new Message(from, to, this.message, sentDate);
            reset();
            return message;
        }

        private void reset() {
            this.from = this.to = this.message = null;
            this.sentDate = null;
        }

    }
}
