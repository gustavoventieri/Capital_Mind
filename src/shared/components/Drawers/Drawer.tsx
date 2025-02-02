import { styled, Theme, CSSObject } from "@mui/material/styles";
import Box from "@mui/material/Box";
import MuiDrawer from "@mui/material/Drawer";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";

import PaidIcon from "@mui/icons-material/Paid";
import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import ReceiptIcon from "@mui/icons-material/Receipt";
import StackedLineChartIcon from "@mui/icons-material/StackedLineChart";
import LogoutIcon from "@mui/icons-material/Logout";
import Home from "@mui/icons-material/Home";
import PersonIcon from "@mui/icons-material/Person";

import { Avatar } from "@mui/material";
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
  const [openDefaultDrawer, setOpenDefaultDrawer] = useState(false);

  const handleDrawerToggle = () => {
    setOpenDefaultDrawer((prev) => !prev);
  };

  return (
    <Box display="flex">
      <Box
        position="fixed"
        top={0}
        left={0}
        width="100vw"
        height="100vh"
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
        {openDefaultDrawer && (
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
                width: 120,
                height: 120,
              }}
              src="https://yt3.ggpht.com/grfYgQadT8iNg9WPb-jkrKB-9224y_DBDXAOtV4Yt7cyQmtR47J_453uveQOTDsp_dRSH851TMM=s108-c-k-c0x00ffffff-no-rj"
            />
          </Box>
        )}
        {!openDefaultDrawer && (
          <IconButton
            sx={{
              width: "100%",
              borderRadius: 0,
              justifyContent: "flex-start",
              paddingLeft: 2.5,
            }}
            onClick={handleDrawerToggle}
          >
            <MenuIcon sx={{ fontSize: 32 }} />
          </IconButton>
        )}

        <List
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-start",
            height: "100%",
            justifyContent: "center",
            marginBottom: openDefaultDrawer ? 8 : 0,
          }}
        >
          {[
            {
              text: "Home",
              icon: <Home sx={{ fontSize: 32 }} />,
            },
            {
              text: "Crypto Currency",
              icon: <PaidIcon sx={{ fontSize: 32 }} />,
            },
            {
              text: "Stock",
              icon: <TrendingUpIcon sx={{ fontSize: 32 }} />,
            },
            {
              text: "Expense",
              icon: <ReceiptIcon sx={{ fontSize: 32 }} />,
            },
            {
              text: "Investment",
              icon: <StackedLineChartIcon sx={{ fontSize: 32 }} />,
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
                      textAlign: "left",
                      width: "100%",
                      marginLeft: 2,
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
          sx={{
            width: "100%",
            borderRadius: 0,
            justifyContent: "flex-start",
            paddingLeft: 2,
          }}
        >
          <PersonIcon sx={{ fontSize: 32 }} />

          {openDefaultDrawer && (
            <ListItemText
              sx={{
                marginTop: 1.3,
                textAlign: "left",
                width: "100%",
                fontWeight: "bold",
                color: "text.primary",
                marginLeft: 2,
              }}
            >
              Account
            </ListItemText>
          )}
        </IconButton>
        <IconButton
          sx={{
            width: "100%",
            borderRadius: 0,
            justifyContent: "flex-start",
            paddingLeft: 2,
          }}
        >
          <LogoutIcon sx={{ fontSize: 30, transform: "scaleX(-1)" }} />

          {openDefaultDrawer && (
            <ListItemText
              sx={{
                marginTop: 1.3,
                textAlign: "left",
                width: "100%",
                fontWeight: "bold",
                color: "text.primary",
                marginLeft: 2.3,
              }}
            >
              Log Out
            </ListItemText>
          )}
        </IconButton>
      </MiniDrawer>
    </Box>
  );
};
