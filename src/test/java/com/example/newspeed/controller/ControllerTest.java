package com.example.newspeed.controller;

import com.example.newspeed.config.WebSecurityConfig;
import com.example.newspeed.dto.CommentRequest;
import com.example.newspeed.dto.ContentRequestDto;
import com.example.newspeed.entity.User;
import com.example.newspeed.security.UserDetailsImpl;
import com.example.newspeed.service.CommentService;
import com.example.newspeed.service.ContentService;
import com.example.newspeed.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.awt.*;
import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = {ContentController.class, CommentController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        })
@MockBean(JpaMetamodelMappingContext.class)
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    ContentService contentService;

    @MockBean
    CommentService commentService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetup() {
        String userId = "aaaaa11111";
        String password = "Aaaaa1111!";
        String name = "가나다";
        String email = "aaaa@naver.com";
        String intro = "안녕하세요";
        User testUser = new User(userId, password, name, email, intro);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }


    @Test
    @DisplayName("게시글 전체 조회")
    void getContent() throws Exception {
        mvc.perform(get("/api/content"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 등록")
    void createContent() throws Exception {
        //given
        this.mockUserSetup();
        ContentRequestDto contentRequestDto = new ContentRequestDto();
        contentRequestDto.setContent("게시글 등록 테스트");

        //when-then
        mvc.perform(post("/api/content")
                .content(objectMapper.writeValueAsString(contentRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정")
    void updateContent() throws Exception {
        //given
        this.mockUserSetup();
        ContentRequestDto contentRequestDto = new ContentRequestDto();
        contentRequestDto.setContent("게시글 수정 테스트");

        //when-then
        mvc.perform(put("/api/content/1")
                        .content(objectMapper.writeValueAsString(contentRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteContent() throws Exception {
        //given
        this.mockUserSetup();

        //when-then
        mvc.perform(delete("/api/content/1")
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 조회")
    void getComment() throws Exception {
        mvc.perform(get("/api/comment/1"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("댓글 등록")
    void createComment() throws Exception {
        //given
        this.mockUserSetup();
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setComment("댓글 등록 테스트");

        //when-then
        mvc.perform(post("/api/content/1/comment")
                        .content(objectMapper.writeValueAsString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() throws Exception {
        //given
        this.mockUserSetup();
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setComment("댓글 수정 테스트");

        //when-then
        mvc.perform(put("/api/comment/1")
                        .content(objectMapper.writeValueAsString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {
        //given
        this.mockUserSetup();

        //when-then
        mvc.perform(delete("/api/comment/1")
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }



}
