package ru.com.ma.controller;

import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.com.ma.domain.Message;
import ru.com.ma.domain.MessageFilter;
import ru.com.ma.domain.User;
import ru.com.ma.service.MessageService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Controller
public class MessageController {

    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

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
        return "main";
    }

    @PostMapping("/main")
    public String addMessage(
            @ModelAttribute Message message,
            @AuthenticationPrincipal(expression = "user") User user,
            @RequestParam("file") MultipartFile file
            ) throws IOException {
        if(file != null && !file.isEmpty()){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + '.' + file.getOriginalFilename();

            message.setFilename(resultFilename);
            file.transferTo(new File(uploadPath + "/" + resultFilename));
        }

        if(message != null){
            messageService.save(message, user);
        }
        return "redirect:main";
    }

}

