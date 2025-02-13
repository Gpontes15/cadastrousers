import { useState } from "react";
import { UserService, User } from "../../services/UserService";

const UserForm = ({ onUserAdded }: { onUserAdded: () => void }) => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [cpf, setCPF] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const newUser: User = { name, email, cpf };
    await UserService.createUser(newUser);
    setName("");
    setEmail("");
    onUserAdded();
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Nome"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
      />
      <input
        type="email"
        placeholder="E-mail"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />
      <input type="cpf" 
      placeholder="CPF"
      value={cpf}
      onChange={(e) => setCPF(e.target.value)}
      required 
      />
      <button type="submit">Cadastrar</button>
    </form>
  );
};

export default UserForm;
