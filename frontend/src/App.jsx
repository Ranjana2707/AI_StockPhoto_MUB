import { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import './index.css';

// API Base URL - Configure this to match your backend
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * AI Chat Assistant - Main Application Component
 * A full-stack chat application with React frontend and Spring Boot backend
 */
function App() {
  // State management
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [userId] = useState(() => `user_${Date.now()}`);
  
  const messagesEndRef = useRef(null);
  const inputRef = useRef(null);

  // Auto-scroll to bottom when new messages arrive
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  // Focus input on mount
  useEffect(() => {
    inputRef.current?.focus();
  }, []);

  /**
   * Send a message to the AI assistant
   */
  const sendMessage = async () => {
    if (!inputMessage.trim() || isLoading) return;

    const userMessage = inputMessage.trim();
    setInputMessage('');
    setIsLoading(true);
    setError(null);

    // Add user message to chat
    const userMsg = {
      id: Date.now(),
      text: userMessage,
      sender: 'user',
      timestamp: new Date().toISOString(),
    };
    setMessages(prev => [...prev, userMsg]);

    try {
      // Call the backend API
      const response = await axios.post(`${API_BASE_URL}/chat`, {
        question: userMessage,
        userId: userId,
      });

      // Add AI response to chat
      const aiMsg = {
        id: Date.now() + 1,
        text: response.data.answer,
        sender: 'ai',
        timestamp: response.data.timestamp || new Date().toISOString(),
      };
      setMessages(prev => [...prev, aiMsg]);
    } catch (err) {
      console.error('Error sending message:', err);
      
      // Handle error response
      const errorMessage = err.response?.data?.message 
        || err.response?.data?.details 
        || 'Failed to get response from AI. Please try again.';
      
      setError(errorMessage);
      
      // Add error message to chat
      const errorMsg = {
        id: Date.now() + 1,
        text: errorMessage,
        sender: 'error',
        timestamp: new Date().toISOString(),
      };
      setMessages(prev => [...prev, errorMsg]);
    } finally {
      setIsLoading(false);
    }
  };

  /**
   * Handle key press in input field
   */
  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  };

  /**
   * Clear all chat messages
   */
  const clearChat = async () => {
    setMessages([]);
    setError(null);
    
    try {
      await axios.delete(`${API_BASE_URL}/history?userId=${userId}`);
    } catch (err) {
      console.error('Error clearing chat history:', err);
    }
  };

  /**
   * Format timestamp for display
   */
  const formatTime = (timestamp) => {
    const date = new Date(timestamp);
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  };

  /**
   * Render message text with paragraph formatting
   */
  const renderMessageText = (text) => {
    return text.split('\n').map((line, index) => (
      <p key={index}>{line}</p>
    ));
  };

  return (
    <div className="app-container">
      {/* Header */}
      <header className="header">
        <div className="logo">
          <div className="logo-icon">💬</div>
          <span className="logo-text">AI Chat Assistant</span>
        </div>
        <div className="header-actions">
          <button className="btn btn-secondary" onClick={clearChat}>
            🗑️ Clear Chat
          </button>
        </div>
      </header>

      {/* Chat Container */}
      <div className="chat-container">
        {/* Messages Area */}
        <div className="messages-container">
          {messages.length === 0 ? (
            // Welcome Screen
            <div className="welcome-screen">
              <div className="welcome-icon">🤖</div>
              <h1 className="welcome-title">Welcome to AI Chat Assistant</h1>
              <p className="welcome-subtitle">
                Start a conversation with our AI assistant. 
                Ask questions, get help with coding, or just chat!
              </p>
            </div>
          ) : (
            // Chat Messages
            messages.map((message) => (
              <div 
                key={message.id} 
                className={`message ${message.sender === 'user' ? 'user' : message.sender === 'error' ? 'error' : 'ai'}`}
              >
                <div className="message-avatar">
                  {message.sender === 'user' ? '👤' : message.sender === 'error' ? '⚠️' : '🤖'}
                </div>
                <div className="message-content">
                  <div className="message-text">
                    {renderMessageText(message.text)}
                  </div>
                  <div className="message-time">
                    {formatTime(message.timestamp)}
                  </div>
                </div>
              </div>
            ))
          )}
          
          {/* Typing Indicator */}
          {isLoading && (
            <div className="message ai">
              <div className="message-avatar">🤖</div>
              <div className="message-content">
                <div className="typing-indicator">
                  <div className="typing-dots">
                    <div className="typing-dot"></div>
                    <div className="typing-dot"></div>
                    <div className="typing-dot"></div>
                  </div>
                  <span>AI is thinking...</span>
                </div>
              </div>
            </div>
          )}
          
          <div ref={messagesEndRef} />
        </div>

        {/* Input Area */}
        <div className="input-container">
          <div className="input-wrapper">
            <textarea
              ref={inputRef}
              className="input-field"
              placeholder="Type your message here..."
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyPress={handleKeyPress}
              disabled={isLoading}
              rows={1}
            />
            <button 
              className="send-button" 
              onClick={sendMessage}
              disabled={!inputMessage.trim() || isLoading}
            >
              {isLoading ? (
                <div className="loading-spinner"></div>
              ) : (
                <svg className="send-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                  <line x1="22" y1="2" x2="11" y2="13"></line>
                  <polygon points="22 2 15 22 11 13 2 9 22 2"></polygon>
                </svg>
              )}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
