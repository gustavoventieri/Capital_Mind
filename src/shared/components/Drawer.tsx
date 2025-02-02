import { styled, useTheme, Theme, CSSObject } from "@mui/material/styles";
import Box from "@mui/material/Box";
import MuiDrawer from "@mui/material/Drawer";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import InboxIcon from "@mui/icons-material/MoveToInbox";
import MailIcon from "@mui/icons-material/Mail";
import { Divider, Drawer, useMediaQuery } from "@mui/material";
import { useState } from "react";

const drawerWidth = 220;

const openedMixin = (theme: Theme): CSSObject => ({
  width: drawerWidth,
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.enteringScreen,
  }),
  overflowX: "hidden",
});

const closedMixin = (theme: Theme): CSSObject => ({
  transition: theme.transitions.create("width", {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  overflowX: "hidden",
  width: `calc(${theme.spacing(7)} + 10px)`,
});

const MiniDrawer = styled(MuiDrawer, {
  shouldForwardProp: (prop) => prop !== "open",
})(({ theme, open }) => ({
  width: drawerWidth,
  flexShrink: 1,
  whiteSpace: "nowrap",

  ...(open && {
    ...openedMixin(theme),
    "& .MuiDrawer-paper": openedMixin(theme),
  }),
  ...(!open && {
    ...closedMixin(theme),
    "& .MuiDrawer-paper": closedMixin(theme),
  }),
}));

export const SmallDrawer = () => {
  const theme = useTheme();
  const [openMobileDrawer, setOpenMobileDrawer] = useState(false);
  const [openDefaultDrawer, setOpenDefaultDrawer] = useState(false);

  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));

  const handleDrawerToggle = () => {
    isMobile
      ? setOpenMobileDrawer((prev) => !prev)
      : setOpenDefaultDrawer((prev) => !prev);
  };

  return (
    <Box display="flex">
      {!isMobile && (
        <>
          <Box
            position="fixed"
            top={0}
            left={0}
            width="100vw"
            height="100vh"
            zIndex={999}
            display={openDefaultDrawer ? "block" : "none"}
            sx={{
              backgroundColor: "rgba(0, 0, 0, 0.5)",
            }}
            onClick={handleDrawerToggle}
          />

          <MiniDrawer
            open={openDefaultDrawer}
            variant={"permanent"}
            onClose={handleDrawerToggle}
          >
            {/* Imagem quando o Drawer está fechado */}
            {!openDefaultDrawer && (
              <Box
                sx={{
                  width: "100%",
                  height: 150, // Ajuste o tamanho conforme necessário
                  backgroundImage: "url('/path-to-your-closed-image.jpg')",
                  backgroundSize: "cover",
                  backgroundPosition: "center",
                }}
              />
            )}

            {/* Imagem quando o Drawer está aberto */}
            {openDefaultDrawer && (
              <Box
                sx={{
                  width: "100%",
                  height: 150, // Ajuste o tamanho conforme necessário
                  backgroundImage: "url('/path-to-your-open-image.jpg')",
                  backgroundSize: "cover",
                  backgroundPosition: "center",
                }}
              />
            )}

            <List
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "flex-start",
                height: "100%",
                justifyContent: "center",
              }}
            >
              {[
                {
                  text: "Inbox",
                  icon: <InboxIcon fontSize={"large"} />,
                },
                {
                  text: "Mail",
                  icon: <MailIcon fontSize={"large"} />,
                },
                {
                  text: "Mail",
                  icon: <MailIcon fontSize={"large"} />,
                },
                {
                  text: "Mail",
                  icon: <MailIcon fontSize={"large"} />,
                },
                {
                  text: "Mail",
                  icon: <MailIcon fontSize={"large"} />,
                },
              ].map(({ text, icon }) => (
                <ListItem
                  key={text}
                  disablePadding
                  sx={{
                    justifyContent: "flex-start",
                    width: "100%",
                  }}
                >
                  <ListItemButton
                    sx={{
                      display: "flex",
                      justifyContent: "flex-start",
                      width: "100%",
                    }}
                  >
                    <ListItemIcon
                      sx={{
                        minWidth: "unset",
                        justifyContent: "center",
                      }}
                    >
                      {icon}
                    </ListItemIcon>

                    {openDefaultDrawer && (
                      <ListItemText
                        sx={{
                          marginTop: 1.3,
                          textAlign: "center",
                          width: "100%",
                        }}
                      >
                        {text}
                      </ListItemText>
                    )}
                  </ListItemButton>
                </ListItem>
              ))}
            </List>

            <IconButton
              onClick={handleDrawerToggle}
              sx={{
                width: "100%",
                borderRadius: 0,
                justifyContent: "flex-start",
                paddingLeft: 2,
              }}
            >
              <MenuIcon fontSize="large" />

              {openDefaultDrawer && (
                <ListItemText
                  sx={{
                    marginTop: 1.3,
                    textAlign: "center",
                    width: "100%",
                    fontWeight: "bold",
                    color: "text.primary",
                  }}
                >
                  Close
                </ListItemText>
              )}
            </IconButton>
          </MiniDrawer>
        </>
      )}

      {isMobile && (
        <>
          <IconButton
            onClick={handleDrawerToggle}
            sx={{
              position: "fixed", // Fixando o botão no canto superior esquerdo
              top: 16,
              left: 16,
              zIndex: 1000, // Para garantir que o ícone fique acima de outros elementos
            }}
          >
            <MenuIcon fontSize="large" />
          </IconButton>

          <Drawer
            open={!isMobile ? false : openMobileDrawer}
            variant="temporary"
            onClose={handleDrawerToggle}
          >
            <Box
              sx={{
                width: 220,
                display: "flex",
                alignItems: "center", // Garante o alinhamento vertical centralizado
                height: "100%", // Para ocupar toda a altura do Drawer
              }}
              onClick={handleDrawerToggle}
            >
              <List>
                {["All mail", "Trash", "Spam"].map((text, index) => (
                  <ListItem key={text} disablePadding>
                    <ListItemButton>
                      <ListItemIcon
                        sx={{
                          minWidth: "unset", // Remove a largura mínima do ícone
                          display: "flex",
                          justifyContent: "center", // Centraliza os ícones horizontalmente dentro do item
                          alignItems: "center", // Garante o alinhamento vertical centralizado
                        }}
                      >
                        {index % 2 === 0 ? (
                          <InboxIcon fontSize="large" />
                        ) : (
                          <MailIcon fontSize="large" />
                        )}
                      </ListItemIcon>
                      <ListItemText
                        primary={text}
                        sx={{
                          marginTop: 1.5,
                          marginLeft: 6,
                          textAlign: "center",
                        }}
                      />
                    </ListItemButton>
                  </ListItem>
                ))}
              </List>
            </Box>
          </Drawer>
        </>
      )}
    </Box>
  );
};
