package com.example.newspeed.Dto;


import com.example.newspeed.dto.SignUpRequestDto;
import com.example.newspeed.entity.User;
import com.example.newspeed.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class EntityDtoTest {

    private Validator validator;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) factory.getValidator();
    }

    @Test
    @DisplayName("User SignUpRequestDto")
    void userDto(){
        //given-when
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUserId("aaaaa11111");
        signUpRequestDto.setPassword("Aaaaa11111!");
        signUpRequestDto.setUsername("가나다");
        signUpRequestDto.setEmail("aaa@naver.com");
        signUpRequestDto.setIntro("안녕하세요.");

        //then
        assertEquals(signUpRequestDto.getUserId(), "aaaaa11111");
        assertEquals(signUpRequestDto.getPassword(), "Aaaaa11111!");
        assertEquals(signUpRequestDto.getUsername(), "가나다");
        assertEquals(signUpRequestDto.getEmail(), "aaa@naver.com");
        assertEquals(signUpRequestDto.getIntro(), "안녕하세요.");
    }

    @Test
    @DisplayName("password 유효성 검사 테스트")
    void userValid() {
        //given
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUserId("aaaaa11111");
        signUpRequestDto.setPassword("Aaaaa11111");
        signUpRequestDto.setUsername("가나다");
        signUpRequestDto.setEmail("aaa@naver.com");
        signUpRequestDto.setIntro("안녕하세요.");

        //when
        Set<ConstraintViolation<SignUpRequestDto>> violations = validator.validate(signUpRequestDto);

        //then
        assertEquals("대소문자 포함 영문, 숫자, 특수문자를 포함하여 10자 이상 입력해주세요.", violations.iterator().next().getMessage());
    }
}
