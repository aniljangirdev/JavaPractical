package practical.repositories;

import practical.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * <h1>ChatRoomRepository</h1>
 * <p>
 *     This interface is used to access the database and perform CRUD operations on the ChatRoom table
 *     It extends JpaRepository which provides the basic CRUD operations for the ChatRoom table.
 * </p>
 * @see JpaRepository
 * @see ChatRoom
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    Set<ChatRoom> findByUsers_Id(Integer userId);
}
