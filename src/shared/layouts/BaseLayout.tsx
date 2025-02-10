import { Box, Typography, useMediaQuery, useTheme } from "@mui/material";
import { DrawerMobile, DrawerDefault } from "../components";
import { useThemeContext } from "../contexts";
import { ReactNode } from "react";

interface IBaseLayoutProps {
  children: React.ReactNode;
  title?: string;
  toolsBar?: ReactNode;
}

export const BaseLayout: React.FC<IBaseLayoutProps> = ({
  children,
  title,
  toolsBar,
}) => {
  const theme = useTheme();

  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const { themeName } = useThemeContext();

  return (
    <>
      {(!isMobile && <DrawerDefault />) || (isMobile && <DrawerMobile />)}

      <Box paddingTop={isMobile ? 7.2 : 0} paddingLeft={isMobile ? 0 : 8.3}>
        {title && (
          <Box>
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
          </Box>
        )}
        {toolsBar && <Box marginTop={2}>{toolsBar}</Box>}

        <Box flex={1}>{children}</Box>
      </Box>
    </>
  );
};
