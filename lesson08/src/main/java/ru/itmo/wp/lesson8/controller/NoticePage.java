package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.domain.Notice;
import ru.itmo.wp.lesson8.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("notice")
    public String noticeGet(Model model, HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            setMessage(httpSession, "Notice available only for logged users!");
            return "redirect:/";
        }
        model.addAttribute("noticeForm", new Notice());
        return "NoticePage";
    }

    @PostMapping("notice")
    public String noticePost(@Valid @ModelAttribute("noticeForm") Notice notice,
                        BindingResult bindingResult,
                        HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        if (getUser(httpSession).isDisabled()) {
            unsetUser(httpSession);
            setMessage(httpSession, "You have been disabled");
            return "redirect:/";
        }

        noticeService.save(notice);

        setMessage(httpSession, "Notice has been published!");

        return "redirect:";
    }
}
