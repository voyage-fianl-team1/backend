package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.ChangePasswordDto;
import com.sparta.matchgi.dto.ReviseUserRequestDto;
import com.sparta.matchgi.dto.SignupRequestDto;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    UserService userService;

    private static User user;

    private static UserDetailsImpl userDetails;

    private String nickname;

    private String email;

    private String password;


    @BeforeEach
    void setup() {
        user = new User(1L, "test@naver.com", "123", "test1", null, false);

    }

    @Test
    @DisplayName("회원가입")
    void registerUser() {

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCases {

            @Test
            @DisplayName("케이스")
            void createUser() {
                SignupRequestDto signupRequestDto = new SignupRequestDto(email, nickname, password);

                User user1 = User.builder()
                        .email(signupRequestDto.getEmail())
                        .nickname(signupRequestDto.getNickname())
                        .password(signupRequestDto.getPassword())
                        .build();

                assertEquals(email, user1.getEmail());
                assertEquals(nickname, user1.getNickname());
                assertEquals(password, user1.getPassword());
            }

        }

        @Nested
        @DisplayName("실패 케이스")
        class FailCases {

            @Nested
            @DisplayName("회원 Id")
            class userId {

                @Test
                @DisplayName("email 빈 값")
                void emailIsEmpty() {
                    email = "";
                    SignupRequestDto signupRequestDto = new SignupRequestDto(email, nickname, password);

                    User user2 = User.builder()
                            .email(signupRequestDto.getEmail())
                            .nickname(signupRequestDto.getNickname())
                            .password(signupRequestDto.getPassword())
                            .build();


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("이메일은 필수 입력 값입니다", exception.getMessage());
                }

                @Test
                @DisplayName("email 중복")
                void duplicate() {
                    email = "test@naver.com";
                    SignupRequestDto signupRequestDto = new SignupRequestDto(email, nickname, password);

                    User user3 = User.builder()
                            .email(signupRequestDto.getEmail())
                            .nickname(signupRequestDto.getNickname())
                            .password(signupRequestDto.getPassword())
                            .build();


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("중복된 이메일입니다.", exception.getMessage());
                }
            }
        }

        @Nested
        @DisplayName("암호변경 테스트")
        class ChangePassword {

            @Test
            @DisplayName("암호 변경")
            void changePassword() {

                ChangePasswordDto changePasswordDto = new ChangePasswordDto("456");
                SignupRequestDto signupRequestDto = new SignupRequestDto(email, nickname, password);

                User user4 = User.builder()
                        .email(signupRequestDto.getEmail())
                        .nickname(signupRequestDto.getNickname())
                        .password(signupRequestDto.getPassword())
                        .build();

                user.changePassword(changePasswordDto.getPassword());

                assertEquals(user.getPassword(), user4.getPassword());


            }
        }

        @Nested
        @DisplayName("닉네임변경 테스트")
        class ChangeNickname {

            @Test
            @DisplayName("닉네임 변경")
            void changeNickname() {

                ReviseUserRequestDto reviseUserRequestDto = new ReviseUserRequestDto("테테레테테테테테테니스");
                SignupRequestDto signupRequestDto = new SignupRequestDto(email, nickname, password);

                User user5 = User.builder()
                        .email(signupRequestDto.getEmail())
                        .nickname(signupRequestDto.getNickname())
                        .password(signupRequestDto.getPassword())
                        .build();

                user.updateNickname(reviseUserRequestDto);

                assertEquals(user.getNickname(), user5.getNickname());


            }
        }
    }
}