import { useState } from "react";
import UserList from "./components/UserList";
import UserForm from "./components/UserForm";

const App = () => {
  const [refresh, setRefresh] = useState(false);

  return (
    <div>
      <h1>Gerenciamento de Usuários</h1>
      <UserForm onUserAdded={() => setRefresh(!refresh)} />
      <UserList key={refresh.toString()} />
    </div>
  );
};

export default App;
