import { Button } from "@mui/material";

import { useThemeContext } from "../../shared/contexts";
import { BaseLayout } from "../../shared/layouts";

export const Home = () => {
  const { toggleTheme } = useThemeContext();
  return (
    <BaseLayout>
      <Button variant="contained" color="primary" onClick={toggleTheme}>
        Toggle Theme
      </Button>
    </BaseLayout>
  );
};
