package com.sist.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.web.model.Admin;
import com.sist.web.model.Response;
import com.sist.web.service.AdminService;
import com.sist.web.util.CookieUtil;
import com.sist.web.util.HttpUtil;
import com.sist.common.util.StringUtil;

@Controller("adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 하드코딩된 관리자 쿠키 이름
    private final String AUTH_COOKIE_NAME = "S_ADMIN_ID";

    // 로그인 처리
    @RequestMapping(value = "/board/loginProc", method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> loginProc(HttpServletRequest request, HttpServletResponse response) {
        Response<Object> ajax = new Response<Object>();

        String adminId = HttpUtil.get(request, "adminId");
        String adminPwd = HttpUtil.get(request, "adminPwd");

        if (!StringUtil.isEmpty(adminId) && !StringUtil.isEmpty(adminPwd)) {
            Admin admin = adminService.adminSelect(adminId);

            // 디버그 로그
            System.out.println("입력된 ID: [" + adminId + "]");
            System.out.println("입력된 PWD: [" + adminPwd + "]");

            if (admin != null) {
                boolean isMatch = adminPwd.trim().equals(admin.getAdminPwd().trim());
                System.out.println("DB에서 조회된 PWD: [" + admin.getAdminPwd() + "]");
                System.out.println("비밀번호 일치 여부: " + isMatch);

                if (isMatch) {
                    // 인코딩 없이 쿠키 직접 저장
                    CookieUtil.addCookie(response, "/", -1, AUTH_COOKIE_NAME, adminId);
                    ajax.setResponse(1, "success");
                } else {
                    ajax.setResponse(-1, "login failed");
                }
            } else {
                ajax.setResponse(-1, "admin not found");
            }
        } else {
            ajax.setResponse(400, "bad request");
        }

        return ajax;
    }

    // 로그아웃 처리
    @RequestMapping(value = "/board/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        if (CookieUtil.getCookie(request, AUTH_COOKIE_NAME) != null) {
            CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
        }
        return "redirect:/admin";
    }
}
