package Springweb.controller;


import Springweb.entity.ThanhVien;
import Springweb.repository.ThanhVienRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
@Controller
public class ThanhVienController {

    @Autowired
    private ThanhVienRepository thanhvienRepository;
    
    @GetMapping(value = "/thanhvien/all")
    public String getAll(Model m) {
        Iterable<ThanhVien> list = thanhvienRepository.findAll();
        m.addAttribute("list", list);
        m.addAttribute("templateName", "thanhvien_all");
        return "sample";
    }

    @GetMapping(value = {"thanhvien/edit/{id}"})
    public String edit(@PathVariable("id") int id, Model m) {
        Optional<ThanhVien> cus = thanhvienRepository.findById(id);
        cus.ifPresent(thanhvien -> m.addAttribute("thanhvien", cus));
        m.addAttribute("templateName", "thanhvien_edit");
        return "sample";
    }

    @GetMapping("/thanhvien/add")
    public String register(Model m) {
        ThanhVien cus = new ThanhVien();
        m.addAttribute("thanhvien", cus);
        m.addAttribute("templateName", "thanhvien_register");
        return "sample";
    }
    
    @PostMapping("/thanhvien/update")
    public String update(Model m, @ModelAttribute("thanhvien") ThanhVien thanhvien) {
        Optional<ThanhVien> tb = thanhvienRepository.findById(thanhvien.getMaTV());
        ThanhVien c;
        c = tb.get();
        c.setMaTV(thanhvien.getMaTV());
        c.setHoTen(thanhvien.getHoTen());
        c.setKhoa(thanhvien.getKhoa());
        c.setNganh(thanhvien.getNganh());
        c.setSdt(thanhvien.getSdt());
        c.setPassword(thanhvien.getPassword());
        c.setEmail(thanhvien.getEmail());
        thanhvienRepository.save(c);
        return "redirect:/thanhvien/all";
    }

    @GetMapping(value = {"/thanhvien/delete/{id}"})
    public String delete(Model m, @PathVariable("id") int id) {
        Optional<ThanhVien> thanhvienOptional = thanhvienRepository.findById(id);

        if (thanhvienOptional.isPresent()) {
            ThanhVien thanhvien = thanhvienOptional.get();
            thanhvienRepository.delete(thanhvien);
            return "redirect:/thanhvien/all";
        } else {
            // Handle the case where the thietbi with the given id doesn't exist
            return "redirect:/thanhvien/all";
        }
    }

    @PostMapping("/thanhvien/save")
    public String save(Model m, @ModelAttribute("thanhvien") ThanhVien thanhvien) {

        thanhvienRepository.save(thanhvien);

        return "redirect:/thanhvien/all";

    }
}