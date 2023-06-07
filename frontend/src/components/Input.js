import React, { useState } from 'react'
import {Button, TextField} from "@mui/material";

const Input = ({ onSendMessage }) => {
    const [text, setText] = useState("")

    let onChange = (e) => {
        setText(e.target.value)
    }

    let onSubmit = () => {
        onSendMessage(text);
        setText("")
    }

    return (
        <div className="message-input">
            <TextField
                className="inputField"
                label="Type your message here..."
                placeholder="Enter your message and press ENTER"
                onChange={e => onChange(e)}
                margin="normal"
                value={text}
                style={{ height: "30px", width: "80%" }}
            />

            <Button variant="contained" color="primary" onClick={onSubmit}>
                Send
            </Button>
        </div>
    );
}


export default Input