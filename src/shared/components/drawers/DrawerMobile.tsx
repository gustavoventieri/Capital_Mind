import {
  AppBar,
  Avatar,
  Box,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
} from "@mui/material";
import { cloneElement, useState } from "react";
import DarkModeIcon from "@mui/icons-material/DarkMode";
import LightModeIcon from "@mui/icons-material/LightMode";

import PaidIcon from "@mui/icons-material/Paid";
import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import ReceiptIcon from "@mui/icons-material/Receipt";
import StackedLineChartIcon from "@mui/icons-material/StackedLineChart";
import LogoutIcon from "@mui/icons-material/Logout";
import PersonIcon from "@mui/icons-material/Person";
import Home from "@mui/icons-material/Home";
import MenuIcon from "@mui/icons-material/Menu";
import CloseIcon from "@mui/icons-material/Close";

import { useThemeContext } from "../../contexts";
import { useLocation } from "react-router-dom";

export const DrawerMobile = () => {
  const [openMobileDrawer, setOpenMobileDrawer] = useState(false);
  const [openMenu, setOpenMenu] = useState(false);
  const { toggleTheme, themeName } = useThemeContext();

  const location = useLocation();

  const getItemStyle = (path: string) => {
    const isActive = location.pathname === path;
    return {
      backgroundColor: isActive ? "rgba(255, 189, 65, 0.3)" : "transparent", // Opacidade no fundo laranja
      color: isActive ? "orange" : themeName === "dark" ? "white" : "black", // Cor do ícone
      opacity: isActive ? 1 : 0.8, // Opacidade no ícone (opcional)
    };
  };

  const handleMenuToggle = () => {
    setOpenMenu((prev) => !prev);
  };

  const handleDrawerToggle = () => {
    setOpenMobileDrawer((prev) => !prev);
  };

  return (
    <>
      <Box width="100%">
        <AppBar sx={{ backgroundColor: "background.paper" }}>
          <Toolbar>
            <IconButton sx={{ mr: 2 }} onClick={handleDrawerToggle}>
              <MenuIcon sx={{ fontSize: 30 }} />
            </IconButton>

            <Box sx={{ ml: "auto", mr: 1 }}>
              <IconButton onClick={handleMenuToggle}>
                <Avatar
                  sx={{
                    width: 32,
                    height: 32,
                  }}
                  src="https://yt3.ggpht.com/grfYgQadT8iNg9WPb-jkrKB-9224y_DBDXAOtV4Yt7cyQmtR47J_453uveQOTDsp_dRSH851TMM=s108-c-k-c0x00ffffff-no-rj"
                />
              </IconButton>
            </Box>
          </Toolbar>
        </AppBar>
      </Box>

      {openMenu && (
        <Box
          sx={{
            position: "absolute",
            top: "60px",
            right: "4px",
            borderRadius: 3,
            backgroundColor: "background.paper",
          }}
        >
          <List>
            <ListItemButton
              href="/account"
              sx={{ ...getItemStyle("/account") }}
            >
              <ListItem disablePadding>
                <ListItemIcon>
                  <PersonIcon sx={{ color: getItemStyle("/account").color }} />
                </ListItemIcon>
                <ListItemText
                  primary="Account"
                  sx={{ mt: 1.3, color: "text.primary", mr: 3 }}
                />
              </ListItem>
            </ListItemButton>

            <ListItemButton onClick={() => console.log("Logout clicked")}>
              <ListItem disablePadding>
                <ListItemIcon>
                  <LogoutIcon
                    sx={{
                      transform: "scaleX(-1)",
                      color: themeName === "dark" ? "white" : "black",
                    }}
                  />
                </ListItemIcon>
                <ListItemText
                  primary="Log Out"
                  sx={{ mt: 1.3, color: "text.primary" }}
                />
              </ListItem>
            </ListItemButton>
            <ListItemButton onClick={handleMenuToggle}>
              <ListItem disablePadding>
                <ListItemIcon>
                  <CloseIcon
                    sx={{ color: themeName === "dark" ? "white" : "black" }}
                  />
                </ListItemIcon>
                <ListItemText
                  primary="Close"
                  sx={{ mt: 1.3, color: "text.primary", mr: 3 }}
                />
              </ListItem>
            </ListItemButton>
          </List>
        </Box>
      )}

      <Drawer
        open={openMobileDrawer}
        variant="temporary"
        onClose={handleDrawerToggle}
      >
        <Box
          sx={{
            width: "100%",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            marginTop: 3,
          }}
        >
          <Avatar
            sx={{
              width: 110,
              height: 110,
            }}
            src="https://yt3.ggpht.com/grfYgQadT8iNg9WPb-jkrKB-9224y_DBDXAOtV4Yt7cyQmtR47J_453uveQOTDsp_dRSH851TMM=s108-c-k-c0x00ffffff-no-rj"
          />
        </Box>

        <List
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-start",
            height: "100%",
            justifyContent: "center",
            marginBottom: 6,
          }}
        >
          {[
            {
              text: "Home",
              icon: <Home />,
              path: "/home",
            },
            {
              text: "Crypto Currency",
              icon: <PaidIcon />,
              path: "/",
            },
            {
              text: "Stock",
              icon: <TrendingUpIcon />,
              path: "/",
            },
            {
              text: "Expense",
              icon: <ReceiptIcon />,

              path: "/",
            },
            {
              text: "Investment",
              icon: <StackedLineChartIcon />,
              path: "/",
            },
          ].map(({ text, icon, path }) => (
            <ListItem
              key={text}
              disablePadding
              sx={{
                justifyContent: "flex-start",
                width: "100%",
              }}
            >
              <ListItemButton
                href={path}
                sx={{
                  display: "flex",
                  justifyContent: "flex-start",
                  width: "100%",
                  ...getItemStyle(path),
                }}
              >
                <ListItemIcon
                  sx={{
                    minWidth: "unset",
                    justifyContent: "center",
                  }}
                >
                  {cloneElement(icon, {
                    sx: { fontSize: 28, color: getItemStyle(path).color },
                  })}
                </ListItemIcon>

                <ListItemText
                  sx={{
                    marginTop: 1.3,
                    textAlign: "left",
                    width: "100%",
                    marginLeft: 2,
                  }}
                >
                  {text}
                </ListItemText>
              </ListItemButton>
            </ListItem>
          ))}
        </List>

        <IconButton onClick={toggleTheme} sx={{ paddingLeft: 2 }}>
          {themeName !== "dark" ? (
            <DarkModeIcon sx={{ fontSize: 28, color: "black" }} />
          ) : (
            <LightModeIcon sx={{ fontSize: 28, color: "white" }} />
          )}
          <ListItemText
            sx={{
              marginTop: 1.3,
              textAlign: "left",
              width: "100%",
              marginLeft: 3,
              color: "text.primary",
            }}
          >
            Switch Theme
          </ListItemText>
        </IconButton>

        <IconButton
          href="/account"
          sx={{
            width: "100%",
            borderRadius: 0,
            justifyContent: "flex-start",
            paddingLeft: 2,
            ...getItemStyle("/account"),
          }}
        >
          <PersonIcon
            sx={{
              fontSize: 28,
              color: getItemStyle("/account").color, // Cor do ícone
            }}
          />
          <ListItemText
            sx={{
              marginTop: 1.3,
              textAlign: "left",
              width: "100%",
              marginLeft: 3,
            }}
          >
            Account
          </ListItemText>
        </IconButton>

        <IconButton
          sx={{
            width: "100%",
            borderRadius: 0,
            justifyContent: "flex-start",
            paddingLeft: 2,
          }}
        >
          <LogoutIcon
            sx={{
              fontSize: 26,
              transform: "scaleX(-1)",
              color: themeName === "dark" ? "white" : "black",
            }}
          />

          <ListItemText
            sx={{
              marginTop: 1.3,
              textAlign: "left",
              width: "100%",
              marginLeft: 3.3,
              color: "text.primary",
            }}
          >
            Log Out
          </ListItemText>
        </IconButton>
      </Drawer>
    </>
  );
};
