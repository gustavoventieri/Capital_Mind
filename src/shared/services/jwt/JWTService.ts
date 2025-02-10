import { jwtDecode } from "jwt-decode";

export const getUserIdFromJWT = (token: string) => {
  const decoded = jwtDecode(token);

  return decoded.sub;
};
