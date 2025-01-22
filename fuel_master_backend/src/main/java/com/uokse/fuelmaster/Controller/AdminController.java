package com.uokse.fuelmaster.Controller;

import com.uokse.fuelmaster.DTO.AdminDTO;
import com.uokse.fuelmaster.DTO.UserDTO;
import com.uokse.fuelmaster.Service.impl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path="/save")
    public String saveAdmin(@RequestBody AdminDTO adminDTO ){
        String id = adminService.addAdmin(adminDTO);
        return ("Admin saved with ID: " + id);
    }

}
