"use client"

import {
  BarChart3,
  GaugeCircle,
  Home,
  Truck,
  Users,
} from "lucide-react"
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarRail,
} from "@/components/ui/sidebar"
import { NavMain } from "./nav-main"
import { NavUser } from "./nav-user"
import NavHeader from "./nav-header"
import PropTypes from 'prop-types'

// This is sample data.
const data = {
  navSuperAdmin: [
    {
      title: "Dashboard",
      url: "/admin/dashboard",
      icon: Home,
    },
    {
      title: "Employee Management",
      url: "/admin/employee-list",
      icon: Users,
    },
    {
      title: "Vehicle Management",
      url: "/admin/vehicle-type-list",
      icon: Truck,
      items: [
        {
          title: "Vehicle Types",
          url: "/admin/vehicle-type-list",
        },
        {
          title: "Add Vehicle Type",
          url: "/admin/vehicle-type-add",
        },
      ],
    },
    {
      title: "Station Management",
      url: "/admin/station-list",
      icon: GaugeCircle,
      items: [
        {
          title: "All Stations",
          url: "/admin/station-list",
        },
        {
          title: "Add Station",
          url: "/admin/station-add",
        },
        {
          title: "Station Master Add",
          url: "/admin/station-master-add",
        },
      ],
    },
    {
      title: "Users",
      url: "/admin/user-list",
      icon: BarChart3
    },
  ],
  navStationManager: [
    {
      title: "Dashboard",
      url: "/admin/dashboard",
      icon: Home,
    },
    {
      title: "Employee Management",
      url: "/admin/employee-list",
      icon: Users,
      items: [
        {
          title: "All Employees",
          url: "/admin/employee-list",
        },
        {
          title: "Add Employee",
          url: "/admin/employee-add",
        },
      ],
    }
  ]
}

export function AppSidebar({ user, userRole, ...props }) {
  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <NavHeader />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={userRole === "SUPER_ADMIN" ? data.navSuperAdmin : data.navStationManager} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser role={userRole} user={{ name: user.name, email: user.email, avatar: "/avatars/admin.jpg", role: userRole }} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  )
}

AppSidebar.propTypes = {
  user: PropTypes.shape({
    name: PropTypes.string.isRequired,
    email: PropTypes.string.isRequired,
  }).isRequired,
  userRole: PropTypes.string.isRequired
}
