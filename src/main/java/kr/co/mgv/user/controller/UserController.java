package kr.co.mgv.user.controller;

import kr.co.mgv.user.form.UserUpdateForm;
import kr.co.mgv.user.service.UserService;
import kr.co.mgv.user.vo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequestMapping("/user/info")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping({"/",""})
    public String home(@AuthenticationPrincipal User user, Model model) {
        String userId = user.getId();
        String userName = user.getName();
        String userEmail = user.getEmail();

        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("userEmail", userEmail);

        return "view/user/home";
    }

    // 이메일 인증
    @GetMapping("/auth")
    public String emailAuthForm(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute("user", user);

        return "view/user/info/auth";
    }

    @PostMapping("/auth")
    public String success() {
        return "redirect:/user/info/form";
    }

    // 회원정보 수정
    @GetMapping("/form")
    public String myMGV(@AuthenticationPrincipal User user, Model model) {
        user = userService.getUserById(user.getId());
        long minDate = userService.getMindate(user.getUpdateDate());
        long pwdMinDate = userService.getMindate(user.getPwdUpdateDate());

        model.addAttribute("user", user);
        model.addAttribute("minDate", minDate);
        model.addAttribute("pwdMinDate", pwdMinDate);

        return "view/user/info/form";
    }

    // todo 회원정보 수정
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@AuthenticationPrincipal User user, UserUpdateForm form) {
        // todo 이메일 수정 할 때
        User checkEmail = userService.getUserByEmail(form.getEmail()); //


        // todo 이메일 수정 안할 때
        if (!user.getEmail().equals(form.getEmail())) {
            userService.updateUser(user.getId(), form.getEmail(), form.getZipcode(), form.getAddress());
//            session.invalidate();
            return ResponseEntity.ok("사용가능한 이메일 주소입니다.");
        } else {
            return ResponseEntity.badRequest().body("중복된 이메일 주소입니다.");
        }
    }

    // 비밀번호 변경
    @GetMapping("/update/password")
    public String pwdForm() {
        return "view/user/info/pwdForm";
    }

    @PostMapping("/update/password")
    public ResponseEntity<String> updatePwd(@AuthenticationPrincipal User user, UserUpdateForm form) {
        if (passwordEncoder.matches(form.getCheckPassword(), user.getPassword())) {
            userService.updatePassword(user.getId(), passwordEncoder.encode(form.getNewPassword()));
            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("현재 비밀번호가 일치하지 않습니다.");
        }
    }

    // 회원 탈퇴
    @GetMapping("/disabled")
    public String disableForm(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute("user", user);

        return "view/user/info/disabled";
    }

    // todo 사용자 이메일 체크
    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(@AuthenticationPrincipal User user, UserUpdateForm form) {
        if (form.getEmail().equals(user.getEmail())) {
            return ResponseEntity.ok(user.getEmail());
        } else {
            return ResponseEntity.badRequest().body("등록된 이메일 주소와 일치하지 않습니다.");
        }
    }

    // todo 회원 탈퇴
    @PostMapping("/disabled")
    public ResponseEntity<String> disableUser(@AuthenticationPrincipal User user, UserUpdateForm form) {
        if (passwordEncoder.matches(form.getCheckPassword(), user.getPassword())) {
            // todo service 작성


            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("현재 비밀번호가 일치하지 않습니다.");
        }

    }

    @GetMapping("/booking")
    public String booking() {
        return "view/user/booking/list";
    }

    @GetMapping("/ticket")
    public String ticekt() {
        return "view/user/ticket/list";
    }

    @GetMapping("/moviestory")
    public String moviestory() {
        return "view/user/moviestory/list";
    }

    @GetMapping("/inquiry")
    public String inquiry() {

        return "view/user/inquiry/list";
    }

}
