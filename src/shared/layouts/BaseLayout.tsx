import { Box, Button, useMediaQuery, useTheme } from "@mui/material";
import { DrawerMobile, SmallDrawer } from "../components";

interface IBaseLayoutProps {
  children: React.ReactNode;
}

export const BaseLayout: React.FC<IBaseLayoutProps> = ({ children }) => {
  const theme = useTheme();

  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));

  return (
    <>
      {/* Drawer condicional */}
      {(!isMobile && <SmallDrawer />) || (isMobile && <DrawerMobile />)}

      <Box paddingTop={isMobile ? 7.2 : 0} paddingLeft={isMobile ? 0 : 8.3}>
        {children}
      </Box>
    </>
  );
};
