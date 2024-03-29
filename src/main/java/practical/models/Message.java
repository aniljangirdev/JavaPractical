package practical.models;

import practical.repositories.MessageRepository;
import practical.models.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <h1>Message</h1>
 * <p>
 *     This class represents a Message object.
 *     It is used to store messages sent by users in a chat room.
 * </p>
 * <p>
 *     this class have a {@link practical.models.user.User} object to represent the user who sent the message.
 *     and a {@link practical.models.ChatRoom} object to represent the chat room where the message was sent.
 * </p>
 * <p>
 *     also this class connected to the following classes:
 *     <ul>
 *         <li>{@link practical.services.MessagesService} provides the business logic for the message.</li>
 *         <li>{@link MessageRepository} JPA for database calls. </li>
 *         <li>{@link practical.models.response.MessageResponse} class for what we want to give to the user. </li>
 *         <li>{@link practical.models.request.MessageRequest} the request we need from the user.</li>
 *     </ul>
 * </p>
 * @see practical.services.MessagesService
 * @see MessageRepository
 * @see practical.models.response.MessageResponse
 * @see practical.models.request.MessageRequest
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    private String text;

    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Message message = (Message) o;
        return getId() != null && Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
