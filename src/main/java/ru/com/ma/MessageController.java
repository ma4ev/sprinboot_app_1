package ru.com.ma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.com.ma.domain.Message;
import ru.com.ma.service.MessageService;


@Controller
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @GetMapping
    public String main(Model model){
        Iterable<Message> messages = messageService.getList();
        model.addAttribute("messages", messages);
        return "main";
    }

}

