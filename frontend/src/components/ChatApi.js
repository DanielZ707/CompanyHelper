import Axios from "axios";


const token = localStorage.getItem('user');

const api = Axios.create({
    baseURL: 'http://localhost:8080/', headers: {
            Authorization: 'Bearer ' + token
        }
});

const ChatApi = {
    getMessages: (groupId) => {
        console.log('Calling get messages from API');
        return api.get(`messages/${groupId}`);
    },

    sendMessage: (username, text) => {
        let msg = {
            content: text,
            sender: username
        }
        return api.post(`send`, msg);
    }
}


export default ChatApi;