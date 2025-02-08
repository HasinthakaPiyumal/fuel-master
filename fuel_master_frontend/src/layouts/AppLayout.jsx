import PropTypes from "prop-types";
import logo from "../assets/images/logo.svg";
import { Link, useNavigate } from "react-router-dom";
import { LogOutIcon } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import apiService from "@/services/api.service";

const AppLayout = ({ children }) => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const { data: allData, isLoading } = useQuery({
    queryKey: ["user"],
    queryFn: async () => {
      const response = await apiService.get("/user/authenticate");
      return response.data.data;
    },
    retry: false
  });
  const user = allData?.user;
  const logout = () => {
    queryClient.removeQueries(["user"]);
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    window.location.href = "/login";
  }
  return (
    <div className="min-h-screen relative ">
      <div className="blur-[100px] fixed top-0 left-0 w-full h-full z-[-1] flex justify-center items-center">
        <div className="bg-[#2459F9] absolute w-96 translate-x-[80%] translate-y-[20%] h-96 rounded-full animate-float-1"></div>
        <div className="bg-[#F92428] absolute translate-y-[-20%] w-96 h-96 rounded-full animate-float-2"></div>
        <div className="bg-primary absolute translate-x-[-80%] translate-y-[20%] w-96 h-96 rounded-full animate-float-3"></div>
      </div>
      <div className="bg-[#FFF3F1] bg-opacity-75 fixed top-0 left-0 w-full h-full z-[-1]"></div>
      <div className="pt-[20px] px-[12px] sm:px-[20px] md:px-[40px] lg:px-[80px]">
        <div className="mb-8 flex justify-between items-center">
          <Link to="/"><img src={logo} alt="Fuel Master Logo" className="w-[147px]" /></Link>

          {!isLoading && user && <Dialog>
            <DialogTrigger variant="ghost" size="icon">
              <Button variant="outline" size="icon">
                <LogOutIcon className="size-4" />
              </Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>Are you sure you want to logout?</DialogTitle>
                <DialogDescription>
                  This action cannot be undone. Are you sure you want to permanently logout?
                </DialogDescription>
              </DialogHeader>
              <DialogFooter>
                <Button type="submit" onClick={logout}>Confirm</Button>
              </DialogFooter>
            </DialogContent>
          </Dialog>}
        </div>
        <main className="main-content">{children}</main>
      </div>
      <div className="sticky top-[100vh] text-center text-white bg-primary p-4">
        Copyright © 2025 GROUP 3
      </div>
    </div>
  );
};

AppLayout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default AppLayout;
