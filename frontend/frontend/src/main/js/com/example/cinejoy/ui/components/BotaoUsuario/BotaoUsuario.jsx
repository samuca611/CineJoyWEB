import { UserRound } from "lucide-react";

export default function BotaoUsuario() {
  return (
    <button className="floating-user" type="button" aria-label="Abrir conta">
      <UserRound size={20} />
    </button>
  );
}
