package com.example.demo.Controller;

import com.example.demo.Model.MonHoc;
import com.example.demo.Repository.MonHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mon-hoc")
public class MonHocController {

    @Autowired
    private MonHocRepository monHocRepository;

    @GetMapping
    public String danhSach(Model model) {
        model.addAttribute("monHoc", new MonHoc());
        model.addAttribute("danhSachMonHoc", monHocRepository.findAll());
        return "mon-hoc";
    }

    @PostMapping("/add")
    public String them(@ModelAttribute MonHoc monHoc) {
        monHocRepository.save(monHoc);
        return "redirect:/mon-hoc";
    }

    @GetMapping("/delete/{maMon}")
    public String xoa(@PathVariable String maMon) {
        monHocRepository.deleteById(maMon);
        return "redirect:/mon-hoc";
    }

    @GetMapping("/tim-kiem")
    public String timKiem(@RequestParam("keyword") String keyword, Model model) {
        List<MonHoc> ketQua = new ArrayList<>();
        ketQua.addAll(monHocRepository.findByTenMonContainingIgnoreCase(keyword));
        ketQua.addAll(monHocRepository.findByMaMonContainingIgnoreCase(keyword));

        model.addAttribute("monHoc", new MonHoc());
        model.addAttribute("danhSachMonHoc", ketQua);
        return "mon-hoc";
    }
    @GetMapping("/tao")
    public String hienFormTaoMonHoc(Model model) {
        model.addAttribute("monHoc", new MonHoc());
        return "tao-mon-hoc";
    }

    @PostMapping("/tao")
    public String xuLyTaoMonHoc(@ModelAttribute MonHoc monHoc) {
        monHocRepository.save(monHoc);
        return "redirect:/de-thi/tao?maMon=" + monHoc.getMaMon(); //
    }

}

