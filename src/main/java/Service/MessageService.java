package Service;

import DAO.MessageDao;
import Model.Message;

import java.util.List;


public class MessageService {
    public MessageDao messageDao;

    public MessageService() {
        this.messageDao = new MessageDao();
    }

    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public Message postMessage(Message msg) {
        if (msg.getMessage_text() == "" || msg.getMessage_text().length() > 255) return null;

        return messageDao.postMessage(msg);
    }

    public List<Message> getAllMessages(){
        return messageDao.getAllMessages();
    }

    public Message getMessageID(int ID) {
        return messageDao.getMessageID(ID);
    }

    public Message deleteMessageID(int ID) {
        Message msg = getMessageID(ID);
        messageDao.deleteMessageID(ID);
        return msg;
    }

    public Message updateMessage(Message msg) {
        if (msg.getMessage_text() == "" || msg.getMessage_text().length() > 255) return null;
        messageDao.updateMessage(msg);
        return messageDao.getMessageID(msg.getMessage_id());
    }

    public List<Message> getAccountMessages(int ID) {
        return messageDao.getAccountMessages(ID);
    }

    
}