package practical.services;

import lombok.RequiredArgsConstructor;
import practical.controller.MessagesApiController;
import practical.models.Message;
import practical.repositories.MessageRepository;
import practical.models.request.MessageRequest;
import org.springframework.stereotype.Service;

import java.beans.Transient;

/**
 * <h1>Messages Service</h1>
 * <p>
 *     This class is responsible for handling all the logic related to the Message model.
 *     It is used by the {@link MessagesApiController} class.
 * </p>
 * @see MessageRepository
 */
@Service
@RequiredArgsConstructor
public class MessagesService {
    private final MessageRepository messagesRepository;

    private final ChatRoomService chatRoomService;

    private final UserService userService;


    //==========================================================================
    //=============================== GET ======================================
    //==========================================================================

    /**
     * This method returns all the messages in the database.
     * @return An {@link Iterable} of {@link Message} objects.
     */
    public Iterable<Message> getAllMessages() {
        return messagesRepository.findAll();
    }

    /**
     * This method returns all the messages in the database with the given chat room id.
     * @param chatRoomId The id of the chat room.
     * @return An {@link Iterable} of {@link Message} objects.
     */
    public Iterable<Message> getMessagesByChatRoomId(Integer chatRoomId) {
        return messagesRepository.findByChatRoomId(chatRoomId);
    }

    /**
     * This method returns the last message in the database with the given chat room id.
     * @param chatRoomId The id of the chat room.
     * @return one Message.
     */
    public Message getLastMessageInChatRoom(Integer chatRoomId) {
        return messagesRepository.findFirstByChatRoomIdOrderByTimestampDesc(chatRoomId);
    }

    /**
     * This method returns the message with the given id.
     * @param messageId The id of the message.
     * @return one Message.
     */
    public Message getMessageById(Integer messageId) {
        return messagesRepository.findById(messageId).orElse(null);
    }

    //==========================================================================
    //=============================== POST =====================================
    //==========================================================================

    /**
     * This method creates a new message in the database.
     * @param message The message to be created.
     * @return the created message.
     */
    @Transient
    public Message createMessage(MessageRequest message) {
        var chatRoom = chatRoomService.getChatRoomById(message.chatRoomId);
        var user = userService.getUserById(message.userId);

        if(chatRoom == null || user == null){
            return null;
        }

        Message newMessage = new Message();
        newMessage.setText(message.text);
        newMessage.setTimestamp(message.timestamp);
        newMessage.setChatRoom(chatRoom);
        newMessage.setUser(user);
        return messagesRepository.save(newMessage);
    }

    //==========================================================================
    //============================== DELETE ====================================
    //==========================================================================

    /**
     * This method deletes the message with the given id.
     * @param messageId The id of the message to be deleted.
     */
    public void deleteMessageById(Integer messageId) {
        messagesRepository.deleteById(messageId);
    }

    /**
     * This method deletes all the messages in the database with the given chat room id.
     * @param chatRoomId The id of the chat room.
     */
    public void deleteMessagesByChatRoomId(Integer chatRoomId) {
        messagesRepository.deleteByChatRoomId(chatRoomId);
    }

}
