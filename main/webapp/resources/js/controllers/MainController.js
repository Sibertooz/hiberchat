'use strict';
/**
 * MainController
 * @constructor
 */
var MainController = function ($scope, $http) {
    $scope.message = {
        messageText: "",
        fromUserId: 0,
        toUserId: 0
    };

    var getUserURL = 'getUser';
    $scope.getUser = function () {
        $http.get(getUserURL).success(function (data) {
            $scope.user = data;
        })
    };
    $scope.getUser();

    $scope.userIsSelected = false;
    $scope.friendId = 0;
    $scope.friendName = "";
    $scope.changeSelectedUser = function (toUserId, toUserName) {
        $scope.userIsSelected = true;
        $scope.friendId = toUserId;
        $scope.friendName = toUserName;
        $scope.message.fromUserId = $scope.user.id;
        $scope.message.toUserId = $scope.friendId;
        $scope.sendSelectedUserId(toUserId);
    };

    $scope.sendSelectedUserId = function (toUserId) {
        $http({
            method: 'POST',
            url: 'sendToUserId',
            data: toUserId,
            headers: {'Content-Type': 'application/json'}
        }).success(function () {
            $scope.getMessages();
        });
    };

    var getMyFriendsURL = 'getMyFriends';
    $scope.getMyFriends = function () {
        $http.get(getMyFriendsURL).success(function (myFriends) {
            $scope.myFriends = myFriends;
        });
    };
    $scope.getMyFriends();

    var getMessagesURL = 'getMessages';
    $scope.messages = [];
    $scope.getMessages = function () {
        $http.get(getMessagesURL).then(function (response) {
            if (response.status === 204) $scope.messages = null;
            if (response.status === 200) $scope.messages = response.data;
        });
    };


    var stompClient = null;

    $scope.getToken = function () {
        $http.get('csrf').success(function (data) {
            $scope.token = data;
        });
    };
    $scope.getToken();

    $scope.setConnected = function (connected) {
        document.getElementById('connect').disabled = connected;
        document.getElementById('disconnect').disabled = !connected;
        document.getElementById('conversationDiv').style.visibility
            = connected ? 'visible' : 'hidden';
        document.getElementById('response').innerHTML = '';
    };

    $scope.connect = function () {
        var socket = new SockJS('secured/chat');
        stompClient = Stomp.over(socket);
        var sessionId = "";

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            var url = stompClient.ws._transport.url;
            console.log("YOUR URL IS: " + url);

            url = url.replace("ws://localhost:8082/hiberchat_war_exploded/secured/chat/", "");
            url = url.replace("/websocket", "");
            url = url.replace(/^[0-9]+\//, "");
            console.log("Your current session is: " + url);
            sessionId = url;

            stompClient.subscribe('/secured/user/queue/history' + '-user' + sessionId, function (messageOutput) {
                $scope.showMessageOutput(JSON.parse(messageOutput.body));
            });

        });
    };

    $scope.disconnect = function () {
        if (stompClient != null) {
            stompClient.disconnect();
            $('#msg_container').empty();
        }
        console.log("Disconnected");
    };

    $scope.sendMessage = function () {
        document.getElementById("noMessagesDiv").style.display = "none";
        stompClient.send("/spring-security-mvc-socket/secured/chat", {}, JSON.stringify($scope.message));
        $scope.message.messageText = "";
    };

    $scope.showMessageOutput = function (messageOutput) {
        document.getElementById("noMessagesDiv").style.display = "none";
        var msg_container = document.getElementById('msg_container');

        var new_div = document.createElement('div');
        if (messageOutput.fromUserId === $scope.friendId) new_div.className = 'chat_in clearfix';
        if (messageOutput.fromUserId === $scope.user.id) new_div.className = 'chat_out clearfix';

        var span = document.createElement('p');
        span.style.wordWrap = 'break-word';
        span.className = 'message';
        span.appendChild(document.createTextNode(messageOutput.messageText));
        new_div.appendChild(span);

        var span_date = document.createElement('p');
        span_date.style.wordWrap = 'break-word';
        span_date.className = 'author';
        span_date.appendChild(document.createTextNode(' on ' + messageOutput.sendingDate.year + '-' +
            messageOutput.sendingDate.monthValue + '-' + messageOutput.sendingDate.dayOfMonth + ' at ' +
            messageOutput.sendingDate.hour + ":" + messageOutput.sendingDate.minute));
        new_div.appendChild(span_date);

        msg_container.appendChild(new_div);
    };


    $scope.friendNickname = "";
    $scope.findUserMessage = "";
    $scope.showModalWindow = false;
    var modalWindow = document.getElementById("findUserModal");
    $scope.findUser = function () {
        $http({
            method: 'POST',
            url: 'findUser',
            data: $scope.friendNickname,
            headers: {'Content-Type': 'application/json'}
        }).then(function (response) {
            if (response.status === 204) {
                modalWindow.style.display = "block";
                $scope.findUserMessage = "USER NOT FOUND";
            }
            if (response.status === 200) {
                $scope.foundUser = response.data;
                $scope.showModalWindow = true;
                modalWindow.style.display = "block";
            }
        });
    };

    $scope.setModalWindowNone = function () {
        modalWindow.style.display = "none";
        $scope.showModalWindow = false;
    };

    $scope.reload = function () {
        window.location.reload();
    };

    $scope.addUserToFriendsList = function () {
        $http({
            method: 'POST',
            url: 'addUser',
            data: $scope.friendNickname,
            headers: {'Content-Type': 'application/json'}
        }).success(function (response) {
            $scope.showModalWindow = false;
            $scope.findUserMessage = response.responseText;
            if (response.responseCode === 200) {
                document.getElementById("smile").className = 'far fa-grin-beam';
                setTimeout($scope.reload, 2000);
            }

            modalWindow.style.display = "block";
            console.log("ERROR CODE IS : "+ response.responseCode);
        });
    };


    $scope.deleteUser = function (nickname) {
        $http({
            method: 'DELETE',
            url: 'deleteFriend',
            data: nickname,
            headers: {'Content-Type': 'application/json'}
        });
    };

    $scope.deleteHistory = function (nickname) {
        $http({
            method: 'DELETE',
            url: 'deleteHistory',
            data: nickname,
            headers: {'Content-Type': 'application/json'}
        });
    };

};