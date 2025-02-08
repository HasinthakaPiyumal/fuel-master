import PropTypes from 'prop-types';
import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"
import { useAdmin } from '@/context/AdminContext';

const AdminLayout = ({ children }) => {
  const { user, userRole } = useAdmin();
  return (
    <SidebarProvider>
      <AppSidebar user={user} userRole={userRole} />
      <main className="w-full">
        <SidebarTrigger />
        <div className="flex flex-col p-4 md:p-8 w-full">
          {children}
        </div>
      </main>


    </SidebarProvider>

  )
};

AdminLayout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default AdminLayout;
