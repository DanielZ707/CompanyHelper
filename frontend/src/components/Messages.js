import React from 'react'

const Messages = ({ messages, currentUser }) => {

    let renderMessage = (message) => {
        const {sender, content} = message;
        const messageFromMe = currentUser.username === JSON.parse(sender).username;
        const className = messageFromMe ? "Messages-message currentUser" : "Messages-message";
        return (
            <li className={className}>
                <span
                    className="avatar-messages"
                    style={{ backgroundColor: null}}
                />
                <div className="Message-content">
                    <div className="username-messages">
                        {JSON.parse(sender).username}
                    </div>
                    <div className="text-messages">{content}</div>
                </div>
            </li>
        );
    };

    return (
        <ul className="messages-list">
            {messages.map(msg => renderMessage(msg))}
        </ul>
    )
}


export default Messages