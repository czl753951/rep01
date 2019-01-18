package controller;

import domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.AccountService;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/12 21:02
 * @Version: 1.0
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/test1")
    public String test() {
        List<Account> list = accountService.findAll();
        System.out.println(list);
        return "success";
    }

    @RequestMapping("/test2")
    public ModelAndView test2() {
        List<Account> list = accountService.findAll();
        System.out.println(list);
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", list);
        mv.setViewName("success");
        return mv;
    }

    @RequestMapping("/test3")
    public String test3(Integer sid, Integer tid, Double money) {
        accountService.transfer(sid, tid, money);
        return "success";
    }
}
