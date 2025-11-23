package com.example.demo.Repository;

import com.example.demo.Model.LuuKetQua;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KetQuaThiRepository {
    private List<LuuKetQua> ketQuaList = new ArrayList<>();
    public void luu(LuuKetQua kq) {
        ketQuaList.add(kq);
    }

    public List<LuuKetQua> getAll() {
        return ketQuaList;
    }
}
