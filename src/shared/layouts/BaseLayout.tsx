import { Box, Typography, useMediaQuery, useTheme } from "@mui/material";
import { DrawerMobile, DrawerDefault } from "../components";
import { useThemeContext } from "../contexts";

interface IBaseLayoutProps {
  children: React.ReactNode;
  title?: string;
  titleView?: boolean;
}

export const BaseLayout: React.FC<IBaseLayoutProps> = ({
  children,
  title,
  titleView,
}) => {
  const theme = useTheme();

  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const { themeName } = useThemeContext();

  return (
    <>
      {/* Drawer condicional */}
      {(!isMobile && <DrawerDefault />) || (isMobile && <DrawerMobile />)}

      <Box paddingTop={isMobile ? 7.2 : 0} paddingLeft={isMobile ? 0 : 8.3}>
        {titleView && (
          <Typography
            overflow="hidden"
            whiteSpace="nowrap"
            textOverflow="ellipses"
            variant="h4"
            justifyContent="center"
            display="flex"
            paddingTop={isMobile ? 2.5 : 3}
            color={themeName === "dark" ? "text.primary" : "text.primary"}
            fontWeight="bold"
          >
            {title}
          </Typography>
        )}

        {children}
      </Box>
    </>
  );
};
