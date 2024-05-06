/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Springweb.controller;

import Springweb.entity.ThanhVien;
import Springweb.entity.ThongTinSD;
import Springweb.repository.ThanhVienRepository;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author PC
 */
@Controller
public class AccountController {
    
     @Autowired
    private ThanhVienRepository thanhVienRepository; 
     
//     /login/dangnhap
  @PostMapping(value = "/login/dangnhap")
     public String dangnhap( @RequestParam("maTV") int maTV,
                                  @RequestParam("passWord") String passWord, 
                                  HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {
   
    if ( maTV == 123123 && passWord.equals("123123")) {
         return "redirect:/admin/";
    }     
         
    Iterable<ThanhVien> list = thanhVienRepository.inspectAccount(maTV, passWord);
   
    for (ThanhVien tv : list) {
          request.getSession().setAttribute("maTV", tv.getMaTV());
          request.getSession().setAttribute("hoTen", tv.getHoTen());
          
          return "redirect:/";
    }

    redirectAttributes.addFlashAttribute("thongBao", "Sai tài khoản mật khẩu !");
    redirectAttributes.addFlashAttribute("tk", maTV);
    redirectAttributes.addFlashAttribute("mk", passWord);
    
    
    return "redirect:/login";
    }
     
     @GetMapping(value = "/hoso")
     public String hoSo(    Model m,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {
         
       int maTV = (int) request.getSession().getAttribute("maTV");
       String hoTen = (String) request.getSession().getAttribute("hoTen");
       
      
       Optional<ThanhVien> thanhVien = thanhVienRepository.findById(maTV);
       ThanhVien tv = thanhVien.get();
       
       
       m.addAttribute("tk", maTV);
       m.addAttribute("hoTen", hoTen);   
       m.addAttribute("thanhVien", tv);   
       m.addAttribute("templateName", "hoso");
      return "sample";
 
    }
    
}
