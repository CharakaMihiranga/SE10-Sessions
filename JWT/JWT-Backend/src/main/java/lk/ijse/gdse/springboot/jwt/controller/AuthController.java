package lk.ijse.gdse.springboot.jwt.controller;

import lk.ijse.gdse.springboot.jwt.dto.AuthDto;
import lk.ijse.gdse.springboot.jwt.dto.ResponseDto;
import lk.ijse.gdse.springboot.jwt.dto.UserDto;
import lk.ijse.gdse.springboot.jwt.service.Impl.UserServiceImpl;
import lk.ijse.gdse.springboot.jwt.util.JwtUtil;
import lk.ijse.gdse.springboot.jwt.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
public class AuthController {
    private final JwtUtil jwtUtil;;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final ResponseDto responseDto;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDto responseDto) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.responseDto = responseDto;
    }
    @PostMapping("/authenticate") //http://localhost:8080/api/v1/auth/authenticate
    public ResponseEntity<ResponseDto> authenticate(@RequestBody UserDto userDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDto(VarList.Conflict, "Invalid Credentials", e.getMessage()));
        }
        UserDto loadedUser = userService.loadUserDetailsByUsername(userDto.getEmail());
        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        String token = jwtUtil.generateToken(loadedUser);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        AuthDto authDto = new AuthDto();
        authDto.setEmail(loadedUser.getEmail());
        authDto.setToken(token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(VarList.Created, "Success", authDto));
    }
    @PostMapping(value = "/register") //http://localhost:8080/api/v1/auth/register
    public ResponseEntity<ResponseDto> registerUser(@RequestBody UserDto userDto) {
        try {
            int res = userService.saveUser(userDto);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDto);
                    AuthDto authDto = new AuthDto();
                    authDto.setEmail(userDto.getEmail());
                    authDto.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDto(VarList.Created, "Success", authDto));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDto(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDto(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}
