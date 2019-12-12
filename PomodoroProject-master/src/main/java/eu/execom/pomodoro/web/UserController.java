package eu.execom.pomodoro.web;

import eu.execom.pomodoro.exceptions.NotValidPasswordException;
import eu.execom.pomodoro.model.Pomodoro;
import eu.execom.pomodoro.model.User;
import eu.execom.pomodoro.payload.JWTLoginSuccessResponse;
import eu.execom.pomodoro.payload.LoginRequest;
import eu.execom.pomodoro.security.JwtTokenProvider;
import eu.execom.pomodoro.security.SecurityConstants;
import eu.execom.pomodoro.service.MapValidationErrorService;
import eu.execom.pomodoro.service.PomodoroService;
import eu.execom.pomodoro.service.UserService;
import eu.execom.pomodoro.validator.UserValidator;
import eu.execom.pomodoro.web.dto.UserDto;
import eu.execom.pomodoro.web.dto.UserRegistrationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/user")
public class UserController {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    private UserService userService;

    private PomodoroService pomodoroService;

    private JwtTokenProvider jwtTokenProvider;

    private UserValidator userValidator;

    private AuthenticationManager authenticationManager;

    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    public UserController(UserService userService, PomodoroService pomodoroService, JwtTokenProvider jwtTokenProvider,
                          UserValidator userValidator, AuthenticationManager authenticationManager,
                          MapValidationErrorService mapValidationErrorService) {
        this.userService = userService;
        this.pomodoroService = pomodoroService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userValidator = userValidator;
        this.authenticationManager = authenticationManager;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<User> users = userService.getAll();
        List<UserDto> userDtos = users.stream().map(UserDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return new ResponseEntity(new UserDto(user), HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> getByEmail(@RequestParam String email) {
        User user = userService.getByEmail(email);
        return new ResponseEntity(new UserDto(user), HttpStatus.OK);
    }

//    @GetMapping("/team/{id}")
//    public ResponseEntity<List<UserDto>> getUsersByTeamId(@PathVariable Long teamId) {
//        List<User> users = userService.getByTeamId(teamId);
//        List<UserDto> userDtos = users.stream().map(UserDto::new).collect(Collectors.toList());
//        return new ResponseEntity(userDtos, HttpStatus.OK);
//    }

//    @DeleteMapping("/team/{id}")
//    public ResponseEntity deleteUserByTeamId(@PathVariable Long teamId) {
//        userService.deleteByTeamId(teamId);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        User user = MODEL_MAPPER.map(userDto, User.class);
        User result = userService.save(user);

        return new ResponseEntity(new UserDto(result), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        validatePassword(userRegistrationDto);
        User user = MODEL_MAPPER.map(userRegistrationDto, User.class);
        Pomodoro pomodoro = pomodoroService.createNewPomodoro();
        user.setPomodoro(pomodoro);
        User createdUser = userService.save(user);

        return new ResponseEntity(new UserDto(createdUser), HttpStatus.CREATED);
    }

    private void validatePassword(@RequestBody UserRegistrationDto userRegistrationDto) {

        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getPasswordConfirmation())) {
            throw new NotValidPasswordException("Passwords doesn't match!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = getToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, token));
    }

    private String getToken(Authentication authentication) {
        return SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
    }

}
