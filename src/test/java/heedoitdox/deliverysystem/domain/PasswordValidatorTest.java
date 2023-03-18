package heedoitdox.deliverysystem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordValidatorTest {
    @DisplayName("비밀번호는 최소 12자 이상 25자 이하여야한다.")
    @ParameterizedTest
    @ValueSource(strings = {"12345678910aaAA", "1234567890123456!!aaBB"})
    void validatePasswordSize(String password) {
        assertThat(PasswordValidator.validate(password)).isTrue();
    }

    @DisplayName("비밀번호는 영문 대문자, 영문 소문자, 숫자, 특수문자 중 3종류가 포함되어야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"12345678910aaAA", "1234567890123456!!aaBB", "binzzang810!", "zzang445453A", "abcABC1234!!!"})
    void validatePasswordCondition(String password) {
        assertThat(PasswordValidator.validate(password)).isTrue();
    }

    @DisplayName("비밀번호는 영문 대문자, 영문 소문자, 숫자, 특수문자 이외의 문자가 포함될 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"ㄱㄴㄷㄹㅁ1334!!AA", "😀binzzang810!"})
    void validatePasswordExcludeCondition(String password) {
        assertThat(PasswordValidator.validate(password)).isFalse();
    }
}