import Send from "@mui/icons-material/Send";
import Navbar from "../../components/navbar/Navbar";
import Sidebar from "../../components/sidebar/Sidebar";
import "./chat.scss";
import React, { useState } from "react";
import axios from "axios";

const Chat = () => {
    const [messages, setMessages] = useState([
        { sender: "bot", text: "Hello! How can I help you?" },
    ]);
    const [input, setInput] = useState("");
    const [isTyping, setIsTyping] = useState(false);

    const sendMessage = async () => {
        if (input.trim()) {
            setInput("");
            const userMessage = { sender: "user", text: input };
            setMessages((prev) => [...prev, userMessage]);
            try {
                setIsTyping(true);
                // Send user message to the backend
                const response = await axios.post(
                    "http://localhost:8080/agent/webhook/sendMessage",
                    { userMessage: input } // Match ChatRequest DTO format
                );
                
                setTimeout(() => {
                    setMessages((prev) => [
                        ...prev,
                        { sender: "bot", text: response.data }, // Simulated bot response
                    ]);
                    setIsTyping(false); // Hide "typing..." indicator
                }, 1000);


            } catch (error) {
                console.error("Error communicating with backend:", error);
                const errorReply = { sender: "bot", text: "Something went wrong, please try again later." };
                setInput("");

            }



        }
    };

    return (
        <div className="chatPage">
            <Navbar type="afterLogin" />
            <div className="pageLayout">
                <Sidebar />
                <div className="chatPageContainer">
                    <div className="chatBox">
                        <div className="topPart">
                            <div className="chat-container">
                                {messages.map((msg, index) => (
                                    <div
                                        key={index}
                                        className={`chat-bubble ${msg.sender === "user" ? "user" : "bot"}`}
                                    >
                                        {msg.sender === "bot" && msg.text.startsWith("<div") ? (
                                            <div dangerouslySetInnerHTML={{ __html: msg.text }} />
                                        ) : (
                                            <p>{msg.text}</p>
                                        )}
                                    </div>

                                ))}

                                {/* Display Typing Indicator */}
                                {isTyping && (
                                    <div className="chat-bubble bot">
                                        <p>Typing...</p>
                                    </div>
                                )}

                            </div>
                        </div>
                        <div className="bottomPart">
                            <div className="sendBoxPart">
                                <input type="text" value={input} onChange={(e) => setInput(e.target.value)} placeholder="Hello" className="textBoxPart"></input>
                                <div className="sendPart" onClick={sendMessage}><Send className="sendIcon" /></div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    );
};

export default Chat;