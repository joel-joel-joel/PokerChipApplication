import * as React from "react";
import { Box, Flex } from "@radix-ui/themes";
import * as Slider from "@radix-ui/react-slider";
import "./BetPanel.css";

type BetPanelProps = {
  yourBet: number;
  currentBet: number;
};

export default function BetPanel({ yourBet, currentBet }: BetPanelProps) {
  // If you want the slider to be live, use state (optional)
  const [bet, setBet] = React.useState<number>(yourBet);

  return (
    <Box className="bet-panel">
      <Box className="bet-panel-inner">

      <div className="bet-top">
        <p className="bet-label">YOUR BET:</p>
        <p className="bet-label">CURRENT BET:</p>
        <p className="bet-amount">${bet}</p>
        <p className="bet-amount">${currentBet}</p>
      </div>

    {/* Slider */}
    <div className="slider-wrap">
      <Slider.Root
        className="slider-root"
        value={[bet]}
        max={1000}
        step={10}
        onValueChange={(v) => setBet(v[0] ?? 0)}
      >
        <Slider.Track className="slider-track">
          <Slider.Range className="slider-range" />
        </Slider.Track>
        <Slider.Thumb className="slider-thumb" aria-label="Bet amount" />
      </Slider.Root>
    </div>

  </Box>
</Box>
  );
}
