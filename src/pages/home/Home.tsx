import { useAuthContext } from "../../shared/contexts";
import { BaseLayout } from "../../shared/layouts";

export const Home = () => {
  const { token, setToken, logout } = useAuthContext();

  return <BaseLayout>{<></>}</BaseLayout>;
};
