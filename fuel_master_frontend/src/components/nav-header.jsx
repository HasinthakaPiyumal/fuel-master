import logo from "@/assets/images/logo.png"

const NavHeader = () => {
    return (
        <div className="flex items-center justify-center">
            <img src={logo} alt="logo" className="h-12 object-contain" />
        </div>
    )
}



export default NavHeader