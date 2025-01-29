"use client"

import { ChevronRight } from "lucide-react"
import PropTypes from 'prop-types';
import { Link, useLocation } from "react-router-dom";

import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger,
} from "@/components/ui/collapsible"
import {
  SidebarGroup,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarMenuSub,
  SidebarMenuSubButton,
  SidebarMenuSubItem,
} from "@/components/ui/sidebar"
import React from "react";

export function NavMain({
  items = [],
}) {
  const location = useLocation();
  const isActive = (items = []) => {
    return items.some(item => location.pathname.includes(item.url));
  }
  const activeUrl = location.pathname;

  return (
    <SidebarGroup>
      <SidebarMenu>
        {items.map((item) => (
          <Collapsible
            key={item.title}
            asChild
            defaultOpen={isActive(item.items) || activeUrl.includes(item.url)}
            className="group/collapsible"
          >
            <SidebarMenuItem>


              {item.items ? (
                <React.Fragment>
                  <CollapsibleTrigger asChild>
                    <SidebarMenuButton tooltip={item.title} isActive={isActive(item.items) || activeUrl.includes(item.url)}>
                      {item.icon && <item.icon />}


                      <span>{item.title}</span>
                      <ChevronRight className="ml-auto transition-transform duration-200 group-data-[state=open]/collapsible:rotate-90" />
                    </SidebarMenuButton>
                  </CollapsibleTrigger>

                  <CollapsibleContent>
                    <SidebarMenuSub>
                      {item.items?.map((subItem) => (
                        <SidebarMenuSubItem key={subItem.title}>
                          <SidebarMenuSubButton isActive={activeUrl.includes(subItem.url)} asChild>
                            <Link to={subItem.url}>
                              <span>{subItem.title}</span>
                            </Link>
                          </SidebarMenuSubButton>
                        </SidebarMenuSubItem>
                      ))}
                    </SidebarMenuSub>
                  </CollapsibleContent>
                </React.Fragment>
              ) : (
                <SidebarMenuButton tooltip={item.title} isActive={isActive(item.items) || activeUrl.includes(item.url)}>
                  {item.icon && <item.icon />}
                  <Link to={item.url}>{item.title}</Link>
                </SidebarMenuButton>




              )}
            </SidebarMenuItem>
          </Collapsible>

        ))}
      </SidebarMenu>
    </SidebarGroup>
  )
}

NavMain.propTypes = {
  items: PropTypes.arrayOf(PropTypes.shape({
    title: PropTypes.string.isRequired,
    icon: PropTypes.elementType,
    isActive: PropTypes.bool,
    items: PropTypes.arrayOf(PropTypes.shape({
      title: PropTypes.string.isRequired,
      url: PropTypes.string.isRequired
    }))
  }))
};
