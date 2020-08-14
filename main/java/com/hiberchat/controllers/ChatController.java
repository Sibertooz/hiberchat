package com.hiberchat.controllers;

import com.hiberchat.exceptions.UserException;
import com.hiberchat.models.Message;
import com.hiberchat.models.MyFriends;
import com.hiberchat.models.Response;
import com.hiberchat.models.User;
import com.hiberchat.services.MessageService;
import com.hiberchat.services.MyFriendsService;
import com.hiberchat.services.UserService;
import com.hiberchat.tempmodels.TempMessage;
import com.hiberchat.tempmodels.TempUser;
import com.hiberchat.utils.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private MyFriendsService myFriendsService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    private Long toUserId;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String chatPage() {
        return "chat";
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        System.out.println("USER NICKNAME IS " + user.getNickname());
        System.out.println("USER COUNTRY IS " + user.getCountry());
        TempUser tempUser = new TempUser();
        tempUser.setNickname(user.getNickname());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ResponseEntity<String> sendMessage(@RequestBody TempMessage message) {
        System.out.println("MESSAGE IS : " + message.getMessageText());
        System.out.println("TO USER ID IS : " + message.getToUserId());
        Message messageToSend = new Message().new Builder().setMessageText(message.getMessageText())
                .setFromUserId(message.getFromUserId())
                .setToUserId(message.getToUserId())
                .setSendingDate(LocalDateTime.now()).build();
        messageService.addMessage(messageToSend);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getMyFriends", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getMyFriendsList(Principal principal) {
        Long myId = userService.getUserByEmail(principal.getName()).getId();
        List<User> myFriendsList = new ArrayList<>(myFriendsService.getAllMyFriends(myId));
        return new ResponseEntity<>(myFriendsList, HttpStatus.OK);
    }

    @RequestMapping(value = "/sendToUserId", method = RequestMethod.POST)
    public ResponseEntity<String> sendToUserId(@RequestBody String toUserId) {
        this.toUserId = Long.parseLong(toUserId);
        System.out.println("FRIEND ID IS " + this.toUserId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessages(Principal principal) {
        Long myId = userService.getUserByEmail(principal.getName()).getId();
        List<Message> allMsgs = new ArrayList<>(messageService.getAllMessages(myId, toUserId));
        if (allMsgs.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(allMsgs, HttpStatus.OK);
    }

    @MessageMapping("/secured/chat")
    public void send(@Payload TempMessage tempMessage, Principal principal, @Header("simpSessionId") String sessionId) {
        System.out.println("MESSAGE HAS BEEN SENT");

        Message messageToSend = new Message().new Builder().setMessageText(tempMessage.getMessageText())
                .setFromUserId(tempMessage.getFromUserId())
                .setToUserId(tempMessage.getToUserId())
                .setSendingDate(LocalDateTime.now()).build();

        String userTo = userService.getUserById(tempMessage.getToUserId()).getEmail();

        messageService.addMessage(messageToSend);
        simpMessagingTemplate.convertAndSendToUser(userTo, "/secured/user/queue/history", messageToSend);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/secured/user/queue/history", messageToSend);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody Response addUserToFriendsList(@RequestBody String friendNickname, Principal principal) {
        Long myId = userService.getUserByEmail(principal.getName()).getId();

        MyFriends myFriend = new MyFriends(myId, userService.getUserByNickname(friendNickname).getId());
        MyFriends hisFriend = new MyFriends(userService.getUserByNickname(friendNickname).getId(), myId);

        try {
            myFriendsService.addMyFriend(myFriend);
        } catch (UserException ex) {
            if (ex.getErrorCode() == ErrorCodes.BAD_REQUEST) return new Response("You can't add yourself to friends list", ErrorCodes.NO_CONTENT);
            if (ex.getErrorCode() == ErrorCodes.CONFLICT) return new Response("The user is already in the friends list", ErrorCodes.NO_CONTENT);
        }

        myFriendsService.addMyFriend(hisFriend);
        return new Response("The user was successfully added to the friends list", ErrorCodes.OK);
    }

    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    public ResponseEntity<User> findUserByNickname(@RequestBody String nickname) {
        try {
            userService.getUserByNickname(nickname);
        } catch (UserException ex) {
            if (ex.getErrorCode() == ErrorCodes.NOT_FOUND) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            if (ex.getErrorCode() == ErrorCodes.BAD_REQUEST) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userService.getUserByNickname(nickname), HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteFriend", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserFromFriendsList(@RequestBody String friendNickname, Principal principal) {
        Long friendId = userService.getUserByNickname(friendNickname).getId();
        Long myId = userService.getUserByEmail(principal.getName()).getId();

        myFriendsService.deleteMyFriend(myId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteHistory", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteHistoryFromChat(@RequestBody String friendNickname, Principal principal) {
        Long friendId = userService.getUserByNickname(friendNickname).getId();
        Long myId = userService.getUserByEmail(principal.getName()).getId();

        messageService.deleteHistory(myId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
