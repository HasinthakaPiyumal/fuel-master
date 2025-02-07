"use client"

import {
  BarChart3,
  Fuel,
  GaugeCircle,
  Home,
  LineChart,
  Settings2,
  Store,
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

// This is sample data.
const data = {
  user: {
    name: "Admin",
    email: "admin@fuelapp.com",
    avatar: "/avatars/admin.jpg",
  },
  teams: [
    {
      name: "Acme Inc",
      logo: Store,
      plan: "Enterprise",
    },
    {
      name: "Acme Corp.",
      logo: Truck,
      plan: "Startup",
    },
    {
      name: "Evil Corp.",
      logo: Users,
      plan: "Free",
    },
  ],
  navMain: [
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
        {
          title: "Vehicle Reports",
          url: "/admin/vehicle-type-report",
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
        {
          title: "Station Master Assign",
          url: "/admin/station-master-assign",
        },
      ],
    },
    {
      title: "Quota Management",
      url: "/admin/quota-management-list",
      icon: Fuel,
      items: [
        {
          title: "Quota Usage",
          url: "/admin/quota-management-list",
        },
        {
          title: "Settings",
          url: "/admin/quota-management-settings",
        },
      ],
    },
    {
      title: "Reports",
      url: "/admin/user-report",
      icon: BarChart3,
      items: [
        {
          title: "User Reports",
          url: "/admin/user-report",
        },
      ],
    },
  ],
  projects: [
    {
      name: "Design Engineering",
      url: "#",
      icon: Settings2,
    },
    {
      name: "Sales & Marketing",
      url: "#",
      icon: BarChart3,
    },
    {
      name: "Travel",
      url: "#",
      icon: LineChart,
    },
  ],
}

export function AppSidebar({ ...props }) {
  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <NavHeader />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  )
}
