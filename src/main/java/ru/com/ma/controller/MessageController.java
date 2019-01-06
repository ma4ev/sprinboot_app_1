package ru.com.ma.controller;

import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.com.ma.domain.Message;
import ru.com.ma.domain.MessageFilter;
import ru.com.ma.domain.User;
import ru.com.ma.service.MessageService;


@Controller
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String greeting(){
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@ModelAttribute MessageFilter filter , Model model){
        Iterable<Message> messages;

        if(filter != null && !filter.isEmpty()){
            messages = messageService.getByFilter(filter);
        } else {
            messages = messageService.getList();
            filter = new MessageFilter();
        }

        model.addAttribute("isEmpty", Iterables.isEmpty(messages));
        model.addAttribute("messages", messages);
        model.addAttribute("new_message", new Message());
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/main")
    public String addMessage(@ModelAttribute Message message, @AuthenticationPrincipal User user){
        if(message != null){
            messageService.save(message, user);
        }
        return "index";
    }

}

