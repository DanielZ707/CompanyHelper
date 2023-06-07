import React, {useState} from 'react';
import SockJsClient from 'react-stomp';
import Messages from "./Messages";
import Input from "./Input";
import ChatAPI from "./ChatApi";


const SOCKET_URL = 'http://localhost:8080/ws-chat/';

const MessagesApp = () => {

    const [messages, setMessages] = useState([])


    let onConnected = () => {
        console.log("Connected!!")
    }

    let onMessageReceived = (msg) => {
        console.log('New Message Received!!', msg);
        setMessages(messages.concat(msg));
    }

    let onSendMessage = (msgText) => {
        ChatAPI.sendMessage({username: email}, msgText).then(res => {
            console.log('Sent', res);
        }).catch(err => {
            console.log('Error Occured while sending message to api');
        })
    }


    const token = localStorage.getItem('user')
    const email = localStorage.getItem('email')


    return (
        <div className="Messages">
            <>
                <SockJsClient
                    url={SOCKET_URL}
                    topics={['/topic/group']}
                    onConnect={onConnected}
                    onDisconnect={console.log("Disconnected!")}
                    onMessage={msg => onMessageReceived(msg)}
                    debug={false}
                />
                <Messages
                    messages={messages}
                    currentUser={email}
                />
                <Input onSendMessage={onSendMessage}/>
            </>
        </div>
    )
}

export default MessagesApp;