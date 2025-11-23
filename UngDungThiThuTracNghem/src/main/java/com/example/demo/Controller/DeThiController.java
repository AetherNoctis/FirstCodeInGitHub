package com.example.demo.Controller;

import com.example.demo.Model.DeThi;
import com.example.demo.Repository.DeThiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DeThiController {
    @Autowired
    private DeThiRepository repository;

    @GetMapping("/de-thi")
    public String hienDanhSachDeThi(Model model) {
        model.addAttribute("deThiList", repository.getAll());
        return "trang-chu";
    }

    @GetMapping("/de-thi/{id}")
    public String xemDeThiRutGon(@PathVariable int id, Model model) {
        DeThi deThi = repository.getByID(id);
        if (deThi == null) {
            return "redirect:/de-thi";
        }
        model.addAttribute("deThi", deThi);
        return "xem-de-thi";
    }

    @GetMapping("/de-thi/tao")
    public String hienFormTaoDeThi(Model model) {
        model.addAttribute("deThi", new DeThi());
        return "tao-de-thi";
    }

    @GetMapping("/de-thi/xem/{id}")
    public String xemDeThi(@PathVariable int id, Model model) {
        DeThi deThi = repository.getByID(id);
        model.addAttribute("deThi", deThi);
        return "xem-de-thi";
    }


    @GetMapping("/de-thi/sua/{id}")
    public String hienFormSuaDe(@PathVariable int id, Model model) {
        DeThi deThi = repository.getByID(id);
        model.addAttribute("deThi", deThi);
        return "sua-de-thi";
    }

    @PostMapping("/de-thi/sua")
    public String luuDeThiSua(@ModelAttribute("deThi") DeThi deThiMoi) {
        DeThi deThiCu = repository.getByID(deThiMoi.getId());
        if (deThiCu != null) {
            deThiCu.setTenDe(deThiMoi.getTenDe());
            deThiCu.setMonHoc(deThiMoi.getMonHoc());
        }
        return "redirect:/de-thi";
    }

    @GetMapping("/de-thi/xoa/{id}")
    public String xoaDeThi(@PathVariable int id) {
        repository.getAll().removeIf(de -> de.getId().equals(id));
        return "redirect:/de-thi";
    }
    @GetMapping("/de-thi/tim-kiem")
    public String timKiemDeThi(@RequestParam String keyword, Model model) {
        List<DeThi> ketQua = repository.timKiem(keyword);
        model.addAttribute("deThiList", ketQua);
        model.addAttribute("tuKhoa", keyword);
        return "trang-chu";
    }
    @GetMapping("/de-thi/loc")
    public String locDeThiTheoNgay(@RequestParam String tuNgay,
                                   @RequestParam String denNgay,
                                   Model model) {
        List<DeThi> ketQua = repository.locTheoNgay(tuNgay, denNgay);
        model.addAttribute("deThiList", ketQua);
        model.addAttribute("tuNgay", tuNgay);
        model.addAttribute("denNgay", denNgay);
        return "trang-chu";
    }
    @GetMapping("/de-thi/loc-mon")
    public String locTheoMonHoc(@RequestParam String monHoc, Model model) {
        List<DeThi> ketQua = monHoc.isEmpty()
                ? repository.getAll()
                : repository.locTheoMon(monHoc);

        model.addAttribute("deThiList", ketQua);
        model.addAttribute("monHoc", monHoc);
        return "trang-chu";
    }
    @GetMapping("/de-thi/loc-tong-hop")
    public String locTongHop(@RequestParam(required = false) String keyword,
                             @RequestParam(required = false) String monHoc,
                             @RequestParam(required = false) String tuNgay,
                             @RequestParam(required = false) String denNgay,
                             @RequestParam(required = false) String sort,
                             Model model) {

        List<DeThi> danhSach = repository.getAll();


        if (keyword != null && !keyword.isBlank()) {
            danhSach = danhSach.stream()
                    .filter(de -> de.getTenDe().toLowerCase().contains(keyword.toLowerCase())
                            || de.getMonHoc().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }


        if (monHoc != null && !monHoc.isBlank()) {
            danhSach = danhSach.stream()
                    .filter(de -> de.getMonHoc().equalsIgnoreCase(monHoc))
                    .collect(Collectors.toList());
        }


        if (tuNgay != null && denNgay != null && !tuNgay.isBlank() && !denNgay.isBlank()) {
            LocalDate from = LocalDate.parse(tuNgay);
            LocalDate to = LocalDate.parse(denNgay);
            danhSach = danhSach.stream()
                    .filter(de -> !de.getNgayTao().isBefore(from) && !de.getNgayTao().isAfter(to))
                    .collect(Collectors.toList());
        }


        if (sort != null) {
            switch (sort) {
                case "tenAsc" -> danhSach.sort(Comparator.comparing(DeThi::getTenDe));
                case "tenDesc" -> danhSach.sort(Comparator.comparing(DeThi::getTenDe).reversed());
                case "ngayAsc" -> danhSach.sort(Comparator.comparing(DeThi::getNgayTao));
                case "ngayDesc" -> danhSach.sort(Comparator.comparing(DeThi::getNgayTao).reversed());
                case "soCauAsc" -> danhSach.sort(Comparator.comparingInt(de -> de.getDanhSachCauHoi().size()));
                case "soCauDesc" -> danhSach.sort(
                        Comparator.comparingInt((DeThi de) -> de.getDanhSachCauHoi().size()).reversed()
                );
            }
        }

        model.addAttribute("deThiList", danhSach);
        model.addAttribute("keyword", keyword);
        model.addAttribute("monHoc", monHoc);
        model.addAttribute("tuNgay", tuNgay);
        model.addAttribute("denNgay", denNgay);
        model.addAttribute("sort", sort);

        return "trang-chu";
    }

}
