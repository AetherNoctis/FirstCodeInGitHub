package com.example.demo.Repository;

import com.example.demo.Model.CauHoi;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CauHoiRepository {
    private List<CauHoi> cauHoi = new ArrayList<>();
    private Long nextId = 1L;
     public void themCauHoi(CauHoi ch){
         ch.setId(nextId++);
         cauHoi.add(ch);
     }
     public List<CauHoi> getAll(){
         return cauHoi;
     }

    public CauHoi timTheoId(Long id) {
        return cauHoi.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public void capNhat(CauHoi ch) {
        for (int i = 0; i < cauHoi.size(); i++) {
            if (cauHoi.get(i).getId().equals(ch.getId())) {
                cauHoi.set(i, ch);
                break;
            }
        }
    }
    public void xoa(Long id) {
        cauHoi.removeIf(c -> c.getId().equals(id));
    }

    public List<CauHoi> getByDeId(Long deId) {
        return cauHoi.stream().filter(c -> c.getDeId().equals(deId)).toList();
    }

}
