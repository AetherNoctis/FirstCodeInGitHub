package com.example.demo.Controller;

import com.example.demo.Repository.DeThiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private DeThiRepository repository;

    @GetMapping("/trang-chu")
    public String trangChu(Model model){
        model.addAttribute("deThiList", repository.getAll());
        return "trang-chu";
    }
}
