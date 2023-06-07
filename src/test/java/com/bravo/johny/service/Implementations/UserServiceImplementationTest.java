package com.bravo.johny.service.Implementations;

import com.bravo.johny.entity.RoleEntity;
import com.bravo.johny.entity.UserEntity;
import com.bravo.johny.repository.UserRepository;
import com.bravo.johny.utils.CustomDisplayNameGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@DisplayNameGeneration(CustomDisplayNameGenerator.ReplaceCamelCase.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplementationTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImplementation userServiceImplementation;


//    @DisplayName("Test load User by Username with non-empty string")
    @ParameterizedTest(name = "#{index} - Run test with args={0}")
    @CsvSource(value = {"'Joey'", "'Chandler'"})
    void loadUserByUsernamePositive(String userName) {

        UserEntity entity = new UserEntity();
        entity.setUserName(userName);
        entity.setRole(new RoleEntity(1,"User"));
        entity.setPassword("pwd");

        when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.of(entity));

        assertEquals(userName, userServiceImplementation.loadUserByUsername(userName).getUsername());
    }


//    @DisplayName("Test load User by Username with null or empty string")
    @ParameterizedTest(name = "#{index} - Run test with args={0}")
    @MethodSource("blankOrNullStrings")
    void loadUserByUsernameNegative(String userName) {

        lenient().when(userRepository.findByUserName(Mockito.isNull())).thenReturn(Optional.empty());
        lenient().when(userRepository.findByUserName(Mockito.anyString())).thenReturn(Optional.empty());

        var exception = assertThrows(ResponseStatusException.class, ()->userServiceImplementation.loadUserByUsername(userName));

        assertTrue(exception.getReason().startsWith("No User found with username"));
    }



    // -----------------------------  HELPER METHODS  --------------------------------------



    static Stream<String> blankOrNullStrings(){
        return Stream.of("", " ", null);
    }
}