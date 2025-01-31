import Lottie from "lottie-react";
import fuelStationAnimation from "@/assets/animations/fuel-station-animation-large.json";

const FuelStationAnimation = () => {
  return (
    <Lottie animationData={fuelStationAnimation} className="w-full max-w-2xl" />
  );
};

export default FuelStationAnimation;
