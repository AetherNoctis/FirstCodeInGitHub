package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monhoc")
public class MonHoc {
    @Id
    @Column(name = "ma_mon")
    private String maMon;

    @Column(name = "ten_mon")
    private String tenMon;

    @Column(name = "so_tin_chi")
    private Integer soTinChi;

    @Column(name = "chuyen_nganh")
    private String chuyenNganh;

    @Column(name = "giang_vien")
    private String giangVien;

    @Column(name = "bat_buoc")
    private String batBuoc;
}
