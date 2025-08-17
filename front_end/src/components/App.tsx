import { Box } from "@radix-ui/themes";
import BetPanel from "./BetPanel";
import SliderDemo from "./slider";
import "./BetPanel.css";

export default function App() {
  return (
    <Box
      style={{
        minHeight: "100vh",
        backgroundColor: "#181717",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <BetPanel yourBet={0} currentBet={0} />
    </Box>
  );
}
