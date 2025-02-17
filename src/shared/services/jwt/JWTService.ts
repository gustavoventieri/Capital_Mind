import { jwtDecode } from "jwt-decode";

export const getUserIdFromJWT = (token: string) => {
  const decoded = jwtDecode(token);

  return decoded.sub;
};

export const isAuthenticated = (token: string | null): boolean => {
  if (!token) return false;
  try {
    const decoded: any = jwtDecode(token);
    return decoded.exp * 1000 > Date.now();
  } catch (error) {
    return false;
  }
};
