package com.example.demo.Repository;

import com.example.demo.Model.DeThi;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeThiRepository {
    private List<DeThi> deThi = new ArrayList<>();

    @PostConstruct
    public void init() {
        deThi.add(new DeThi(1, "Đề thi Toán", "Toán", LocalDate.of(2025, 10, 1)));
        deThi.add(new DeThi(2, "Đề thi Sử", "Sử", LocalDate.of(2025, 10, 10)));
        deThi.add(new DeThi(3, "Đề thi Văn", "Văn", LocalDate.of(2025, 10, 15)));
    }

    public List<DeThi> getAll() {
        return deThi;
    }

    public DeThi getByID(int id) {
        return deThi.stream()
                .filter(deThi1 -> deThi1.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private int nextId = 4;

    public List<DeThi> timKiem(String keyword) {
        return deThi.stream()
                .filter(de -> de.getTenDe().toLowerCase().contains(keyword.toLowerCase())
                        || de.getMonHoc().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<DeThi> locTheoNgay(String tuNgay, String denNgay) {
        LocalDate from = LocalDate.parse(tuNgay);
        LocalDate to = LocalDate.parse(denNgay);

        return deThi.stream()
                .filter(de -> !de.getNgayTao().isBefore(from) && !de.getNgayTao().isAfter(to))
                .collect(Collectors.toList());
    }
    public List<DeThi> locTheoMon(String monHoc) {
        return deThi.stream()
                .filter(de -> de.getMonHoc().equalsIgnoreCase(monHoc))
                .collect(Collectors.toList());
    }

}
