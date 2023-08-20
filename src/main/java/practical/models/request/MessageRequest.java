package practical.models.request;

import java.time.LocalDateTime;

/**
 * <h1>MessageRequest</h1>
 * <p>
 * this class is used to create a message request object
 * for user to simply can fill this object and send it to the server.
 * </p>
 *
 * @see practical.models.Message
 */
public class MessageRequest {
    public String text;
    public LocalDateTime timestamp;
    public Integer userId;
    public Integer chatRoomId;
}
