// ConfirmDialog.tsx

import React from "react";
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Button,
  Box,
  Typography,
  Icon,
} from "@mui/material";
import InfoOutlinedIcon from "@mui/icons-material/InfoOutlined";

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  onConfirm: () => void;
  title: string;
  content: string;
}

export const ConfirmDialog: React.FC<ConfirmDialogProps> = ({
  open,
  onClose,
  onConfirm,
  title,
  content,
}) => {
  return (
    <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
      <DialogTitle>
        <Box display="flex" alignItems="center" gap={1}>
          <InfoOutlinedIcon sx={{ color: "primary.main" }} />
          <Typography variant="h6" mt={0.5}>
            {title}
          </Typography>
        </Box>
      </DialogTitle>
      <DialogContent>
        <Typography> {content}</Typography>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          onClick={onClose}
          sx={{
            backgroundColor: "primary.main",
            "&:hover": {
              backgroundColor: "primary.dark",
            },
            height: "40px",
            color: "white",
            paddingTop: 1.4,
          }}
        >
          Cancel
        </Button>
        <Button
          variant="outlined"
          color="error"
          onClick={onConfirm}
          sx={{
            backgroundColor: "transparent",
            "&:hover": {
              backgroundColor: "red",
              color: "white",
            },
            height: "40px",
            paddingTop: 1.4,
          }}
        >
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
};
