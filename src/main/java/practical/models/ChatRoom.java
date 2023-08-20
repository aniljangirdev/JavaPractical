package practical.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import practical.repositories.ChatRoomRepository;
import practical.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <h1>ChatRoom</h1>
 * <p>
 *     This class is used to represent a chat room.
 *     It contains a name and a list of users.
 *     It is used to create a chat room in the database.
 * </p>
 * <p>
 *     this class related to the following tables:
 *     <ul>
 *         <li>chat_rooms</li>
 *         <li>user_chat_room</li>
 *     </ul>
 *
 *      so we have many to many relation between user and chat room.
 * </p>
 * <p>
 *     also this class connected to the following classes:
 *     <ul>
 *         <li>{@link practical.services.ChatRoomService} provides the business logic for the chat room.</li>
 *         <li>{@link ChatRoomRepository} JPA for database calls. </li>
 *         <li>{@link practical.models.response.ChatRoomResponse} class for what we want to give to the user. </li>
 *         <li>{@link practical.models.request.ChatRoomRequest} the request we need from the user.</li>
 *     </ul>
 * </p>
 * @see practical.services.ChatRoomService
 * @see ChatRoomRepository
 * @see practical.models.response.ChatRoomResponse
 * @see practical.models.request.ChatRoomRequest
 */
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @ManyToMany(mappedBy = "chatRooms")
    @ToString.Exclude
    @JsonManagedReference
    private Set<User> users = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return getId() != null && Objects.equals(getId(), chatRoom.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
