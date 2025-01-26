import PropTypes from "prop-types";
import logo from "../assets/images/logo.png";

const AppLayout = ({ children }) => {
  return (
    <div className="min-h-screen relative ">
      <div className="blur-[100px] fixed top-0 left-0 w-full h-full z-[-1] flex justify-center items-center">
        <div className="bg-[#2459F9] absolute w-96 translate-x-[80%] translate-y-[20%] h-96 rounded-full"></div>
        <div className="bg-[#F92428] absolute translate-y-[-20%] w-96 h-96 rounded-full"></div>
        <div className="bg-primary absolute translate-x-[-80%] translate-y-[20%] w-96 h-96 rounded-full"></div>
      </div>
      <div className="bg-[#FFF3F1] bg-opacity-75 fixed top-0 left-0 w-full h-full z-[-1]"></div>
      <div className="pt-[20px] px-[12px] sm:px-[20px] md:px-[40px] lg:px-[80px]">
        <div className="mb-8">
          <img src={logo} alt="Fuel Master Logo" className="w-[147px]" />
        </div>
        <main className="main-content">{children}</main>
      </div>
      <div className="sticky top-[100vh] text-center text-white bg-primary p-4">
        Copyright © 2025 GROUP 4
      </div>
    </div>
  );
};

AppLayout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default AppLayout;
