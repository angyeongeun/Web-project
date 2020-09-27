package com.cartrapido.main.web;

import com.cartrapido.main.chat.service.ChatRoomService;
import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    //tomcat error fix_new2



    //메인 페이지, zipcode입력 페이지 / ->firstPage 로 수정
    @GetMapping("/")
    public String index(Model model){
        return "/firstPage.html";
    }

    //로그인 페이지
    @GetMapping("/user/loginPage")
    public String loginPage(){
        return "/loginPage.html";
    }

    @PostMapping("/user/loginPage")
    public String loginPage2(){
        return "/loginPage.html";
    }

    //회원가입 페이지
    @PostMapping("/user/signUpPage")
    public String signUpPage(@RequestParam String address, Model model,HttpSession session){
        model.addAttribute("address",address);
        session.setAttribute("address",address);
        return "/signUpPage.html";
    }


    //로그인 다음 들어가는 곳
    @GetMapping("/clientMain")
    public String clientWeb(Model model,HttpSession httpSession) {
        /*SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        String email = sessionUser.getEmail();
        List<ChatRoomListResponsDto> list = chatRoomService.findAllChatRoom(email);
        model.addAttribute("chatRoom",chatRoomService.findAllChatRoom(email));
*/
        return "/clientWebBody/clientMain.html";
    }

    @GetMapping("/shopperMain")
    public String shopperWeb(Model model) {
       /* SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        String email = sessionUser.getEmail();
        model.addAttribute("chatRoom",chatRoomService.findAllChatRoom(email));
*/
        return "/shopperWebBody/firstPage.html";
    }


    @GetMapping("/user/loginFail")
    public String loginFail(){
        return "loginFail.html";
    }


//    //어드민 페이지
//    @GetMapping("/admin")
//    public String dispAdmin(){
//        return "/adminPage";
//    }

    @GetMapping("/user/denied")
    public String userDenied(){
        return "errorPage.html";
    }

    //회원탈퇴 처리
    @GetMapping("/withdrawalMember")
    public String withdrawalMember(HttpSession session){
        System.out.println("withdrawalMember 진입");
        SessionUser user = (SessionUser) session.getAttribute("user");
        String email = user.getEmail();
        memberService.deleteUser(email);

        return "redirect:/logout";
    }
}
