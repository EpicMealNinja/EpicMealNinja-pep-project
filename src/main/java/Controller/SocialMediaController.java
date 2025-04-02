package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerAccount);
        app.post("/login", this::loginAccount);
        app.post("/messages", this::postMessage);
        app.get("/messages", this::getMessages);
        app.get("/messages/{message_id}", this::getMessageID);
        app.delete("/messages/{message_id}", this::deleteMessageID);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getAccountMessages);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerAccount(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acnt = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.createAccount(acnt);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    private void loginAccount(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acnt = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.loginAccount(acnt);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(401);
        }
    }

    private void postMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = null;
        if (accountService.accountExist(msg.getPosted_by())) {
            addedMessage = messageService.postMessage(msg);
        }
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    private void getMessages(Context ctx) throws JsonProcessingException {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageID(Context ctx) throws JsonProcessingException {
        int ID = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.getMessageID(ID);
        if(msg!=null){
            ctx.json((msg));
        }else{
            ctx.status(200);
        }
    }

    private void deleteMessageID(Context ctx) throws JsonProcessingException {
        int ID = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.deleteMessageID(ID);
        if(msg!=null){
            ctx.json((msg));
        }else{
            ctx.status(200);
        }
    }

    private void updateMessage(Context ctx) throws JsonProcessingException {
        int ID = Integer.parseInt(ctx.pathParam("message_id"));

        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        msg.setMessage_id(ID);

        msg = messageService.updateMessage(msg);
        if (msg != null) {
            ctx.json((msg));
        }else{
            ctx.status(400);
        }
    }

    private void getAccountMessages(Context ctx) throws JsonProcessingException {
        int ID = Integer.parseInt(ctx.pathParam("account_id"));

        ctx.json(messageService.getAccountMessages(ID));
    }

}