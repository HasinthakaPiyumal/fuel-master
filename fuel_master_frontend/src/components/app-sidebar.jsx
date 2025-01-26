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
import { NavProjects } from "./nav-projects"
import { NavUser } from "./nav-user"

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
      isActive: true,
      items: [
        {
          title: "Overview",
          url: "/admin/dashboard",
        },
        {
          title: "Analytics",
          url: "/admin/dashboard/analytics",
        },
        {
          title: "Reports",
          url: "/admin/dashboard/reports",
        },
      ],
    },
    {
      title: "Fuel Station Management",
      url: "/admin/fuel-stations",
      icon: GaugeCircle,
      items: [
        {
          title: "All Stations",
          url: "/admin/fuel-stations",
        },
        {
          title: "Add Station",
          url: "/admin/fuel-stations/add",
        },
        {
          title: "Station Reports",
          url: "/admin/fuel-stations/reports",
        },
      ],
    },
    {
      title: "Fuel Quota Management",
      url: "/admin/quota",
      icon: Fuel,
      items: [
        {
          title: "Quota Overview",
          url: "/admin/quota",
        },
        {
          title: "Allocations",
          url: "/admin/quota/allocations",
        },
        {
          title: "Usage History",
          url: "/admin/quota/history",
        },
        {
          title: "Settings",
          url: "/admin/quota/settings",
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
        {/* <TeamSwitcher teams={data.teams} /> */}
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
        <NavProjects projects={data.projects} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  )
}
