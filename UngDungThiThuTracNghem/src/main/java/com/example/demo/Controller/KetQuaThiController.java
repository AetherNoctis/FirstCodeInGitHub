package com.example.demo.Controller;

import com.example.demo.Model.LuuKetQua;
import com.example.demo.Repository.KetQuaThiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ket-qua")
public class KetQuaThiController {

    @Autowired
    private KetQuaThiRepository ketQuaThiRepository;

    @GetMapping("/thong-ke")
    public String hienThongKe(Model model) {
        List<LuuKetQua> danhSach = ketQuaThiRepository.getAll();

        int tongBai = danhSach.size();
        int tongDiem = danhSach.stream().mapToInt(LuuKetQua::getDiem).sum();
        int diemCaoNhat = danhSach.stream().mapToInt(LuuKetQua::getDiem).max().orElse(0);
        int diemThapNhat = danhSach.stream().mapToInt(LuuKetQua::getDiem).min().orElse(0);
        double diemTrungBinh = tongBai > 0 ? (double) tongDiem / tongBai : 0;

        model.addAttribute("danhSach", danhSach);
        model.addAttribute("tongBai", tongBai);
        model.addAttribute("diemCaoNhat", diemCaoNhat);
        model.addAttribute("diemThapNhat", diemThapNhat);
        model.addAttribute("diemTrungBinh", diemTrungBinh);

        return "thong-ke-diem";
    }
}
