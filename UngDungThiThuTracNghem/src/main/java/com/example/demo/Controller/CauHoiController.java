package com.example.demo.Controller;

import com.example.demo.Model.CauHoi;
import com.example.demo.Model.DeThi;
import com.example.demo.Repository.CauHoiRepository;
import com.example.demo.Repository.DeThiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cau-hoi")
public class CauHoiController {

    @Autowired
    private CauHoiRepository repository;

    @Autowired
    private DeThiRepository deThiRepository;

    @GetMapping("/tao")
    public String hienFormTaoBangQuery(@RequestParam int deId, Model model) {
        CauHoi ch = new CauHoi();
        ch.setDeId(deId);
        model.addAttribute("dienCauHoi", ch);
        model.addAttribute("deId", deId);
        return "tao-cau-hoi";
    }


    @PostMapping("/tao")
    public String luuCauHoi(@ModelAttribute("dienCauHoi") CauHoi ch) {
        repository.themCauHoi(ch);
        return "redirect:/cau-hoi/list";
    }

    @GetMapping("/list")
    public String hienDanhSach(Model model) {
        model.addAttribute("questions", repository.getAll());
        return "xem-de-thi";
    }

    @GetMapping("/sua/{id}")
    public String hienFormSua(@PathVariable Long id, Model model) {
        CauHoi ch = repository.timTheoId(id);
        model.addAttribute("dienCauHoi", ch);
        return "sua-cau-hoi";
    }

    @PostMapping("/sua")
    public String luuCauHoiSua(@ModelAttribute("dienCauHoi") CauHoi ch) {
        repository.capNhat(ch);
        DeThi deThi = deThiRepository.getByID(ch.getDeId());
        if (deThi != null) {
            for (int i = 0; i < deThi.getDanhSachCauHoi().size(); i++) {
                if (deThi.getDanhSachCauHoi().get(i).getId().equals(ch.getId())) {
                    deThi.getDanhSachCauHoi().set(i, ch);
                    break;
                }
            }
        }
        return "redirect:/de-thi/" + ch.getDeId();
    }

    @GetMapping("/xoa/{id}")
    public String xoaCauHoi(@PathVariable Long id, @RequestParam int deId) {
        repository.xoa(id);
        DeThi deThi = deThiRepository.getByID(deId);
        if (deThi != null) {
            deThi.getDanhSachCauHoi().removeIf(q -> q.getId().equals(id));
        }
        return "redirect:/de-thi/" + deId;
    }

    @GetMapping("/tao-cho-de/{deId}")
    public String hienFormTaoChoDe(@PathVariable int deId, Model model) {
        CauHoi ch = new CauHoi();
        ch.setDeId(deId);
        model.addAttribute("dienCauHoi", ch);
        return "tao-cau-hoi";
    }

    @PostMapping("/tao-cho-de")
    public String luuCauHoiChoDe(@ModelAttribute("dienCauHoi") CauHoi ch) {
        repository.themCauHoi(ch);
        DeThi deThi = deThiRepository.getByID(ch.getDeId());
        if (deThi != null) {
            deThi.getDanhSachCauHoi().add(ch);
        }
        return "redirect:/de-thi/" + ch.getDeId();
    }


}
