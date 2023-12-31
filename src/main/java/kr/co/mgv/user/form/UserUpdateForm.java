package kr.co.mgv.user.form;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserUpdateForm {

    private MultipartFile file;
    private String checkPassword;
    private String newPassword;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 올바르지 않습니다.")
    private String email;

    private String zipcode;
    private String address;
    private String reason;

    public UserUpdateForm(MultipartFile file, String checkPassword, String newPassword, String email, String zipcode, String address, String reason) {
        this.file = file;
        this.checkPassword = checkPassword;
        this.newPassword = newPassword;
        this.email = email;
        this.zipcode = zipcode;
        this.address = address;
        this.reason = reason;
    }
}
