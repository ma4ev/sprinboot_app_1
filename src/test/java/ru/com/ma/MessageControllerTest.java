package ru.com.ma;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.com.ma.controller.MessageController;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(ru.com.ma.config.WebSecurityConfig.class)
@WithMockUser(username="simpleUser",roles={"USER"})
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageController controller;

    @Test
    public void mainPage() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"navbarSupportedContent\"]/div[1]/span").string("qqq"));

    }

    @Test
    public void messageListTest() throws Exception{
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id=\"messages_table\"]/div").nodeCount(4));
    }

    @Test
    public void filterMessageTest() throws Exception{
        this.mockMvc.perform(get("/main").param("term", "tag1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id=\"messages_table\"]/div").nodeCount(2))
                .andExpect(xpath("//div[@id=\"messages_table\"]/div[@data-id=1]").exists())
                .andExpect(xpath("//div[@id=\"messages_table\"]/div[@data-id=2]").exists());
    }

    @Test
    public void addMessageToList() throws Exception{
        MockHttpServletRequestBuilder multipartBuilder = multipart("/main")
                .file("file", "123".getBytes())
                .param("text", "some_text")
                .param("tag", "some_tag");

        this.mockMvc.perform(multipartBuilder)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id=\"messages_table\"]/div").nodeCount(5))
                .andExpect(xpath("//div[@id=\"messages_table\"]/div[@data-id=10]").exists())
                .andExpect(xpath("//div[@id=\"messages_table\"]/div[@data-id=10]/div/span").string("some_text"))
                .andExpect(xpath("//div[@id=\"messages_table\"]/div[@data-id=10]/div/i").string("some_tag"));
    }
}
