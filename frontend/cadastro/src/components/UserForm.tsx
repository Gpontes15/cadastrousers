import React, { useState } from 'react';
import axios from 'axios';

const UserForm: React.FC = () => {
  const [username, setUsername] = useState('');
  const [cpf, setCpf] = useState('');
  const [email, setEmail] = useState('');

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/users', { username, cpf, email });
      console.log('User created:', response.data);
    } catch (error) {
      console.error('There was an error creating the user!', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Username:</label>
        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
      </div>
      <div>
        <label>CPF:</label>
        <input type="text" value={cpf} onChange={(e) => setCpf.value)} required />
      </div>
      <div>
        <label>Email:</label>
        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
      </div>
      <button type="submit">Create User</button>
    </form>
  );
};

export default UserForm;