package lk.ijse.gdse.springboot.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String companyName;
    private String role;
}
