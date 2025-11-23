package com.example.demo.Controller;

import com.example.demo.Model.CauHoi;
import com.example.demo.Model.DeThi;
import com.example.demo.Model.LuuKetQua;
import com.example.demo.Repository.DeThiRepository;
import com.example.demo.Repository.KetQuaThiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bai-thi")
public class BaiThiController {

    @Autowired
    private DeThiRepository deThiRepository;

    @Autowired
    private KetQuaThiRepository ketQuaThiRepository;


    @PostMapping("/nop")
    public String nopBai(@RequestParam int deId, @RequestParam Map<String, String> allParams, Model model) {
        DeThi deThi = deThiRepository.getByID(deId);
        int diem = 0;

        for (CauHoi q : deThi.getDanhSachCauHoi()) {
            String key = "dapAn_" + q.getId();
            String dapAnChon = allParams.get(key);
            if (dapAnChon != null && dapAnChon.equalsIgnoreCase(q.getChonDapAnDung())) {
                diem++;
            }
        }
        LuuKetQua kq = new LuuKetQua();
        kq.setTenDe(deThi.getTenDe());
        kq.setTongCau(deThi.getDanhSachCauHoi().size());
        kq.setSoCauDung(diem);
        kq.setDiem(diem);
        kq.setThoiGianNop(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        ketQuaThiRepository.luu(kq);

        model.addAttribute("ketQua", kq);
        model.addAttribute("tongCau", deThi.getDanhSachCauHoi().size());
        model.addAttribute("diem", diem);
        model.addAttribute("deThi", deThi);
        return "ket-qua-thi";
    }

    @GetMapping("/lam/{deId}")
    public String hienFormLamBai(@PathVariable int deId, Model model) {
        DeThi deThi = deThiRepository.getByID(deId);
        model.addAttribute("deThi", deThi);
        return "lam-bai-thi";
    }

}
