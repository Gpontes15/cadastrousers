import { useEffect, useState } from "react";
import { UserService, User } from "../../services/UserService";
const UserList = () => {
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    const data = await UserService.getUsers();
    setUsers(data);
  };

  const handleDelete = async (id: number) => {
    await UserService.deleteUser(id);
    loadUsers();
  };

  return (
    <div>
      <h2>Lista de Usuários</h2>
      <ul>
        {users.map((user) => (
          <li key={user.id}>
            {user.name} - {user.email} - {user.cpf}
            <button onClick={() => handleDelete(user.id!)}>Excluir</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default UserList;
