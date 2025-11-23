package com.example.demo.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeThi {
    private Integer id;
    private String tenDe;
    private String monHoc;
    private List<CauHoi> danhSachCauHoi = new ArrayList<>();
    private LocalDate ngayTao;

    public DeThi(Integer id, String tenDe, String monHoc, LocalDate ngayTao) {
        this.id = id;
        this.tenDe = tenDe;
        this.monHoc = monHoc;
        this.ngayTao = ngayTao;
        this.danhSachCauHoi = new ArrayList<>();
    }
}