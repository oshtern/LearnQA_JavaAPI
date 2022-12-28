package passwords;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder

public class PojoPassword {
    private String login;
    private String password;
}
