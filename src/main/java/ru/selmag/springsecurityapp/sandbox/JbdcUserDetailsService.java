package ru.selmag.springsecurityapp.sandbox;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.Optional;

public class JbdcUserDetailsService extends MappingSqlQuery<UserDetails> implements UserDetailsService {

    public JbdcUserDetailsService(DataSource dataSource) {
        super(dataSource, """
                select 
                    u.username,
                    up.password,
                    array_agg(ua.authority) as authorities
                from t_user u 
                left join t_user_password up on up.id_user = u.id
                left join t_user_authority ua on ua.id_user = u.id
                where u.username = :username
                group by u.id, up.id
                """);
        this.declareParameter(new SqlParameter("username", Types.VARCHAR));
        this.compile();
    }

    @Override
    protected UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .authorities((String[]) rs.getArray("authorities").getArray())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(this.findObjectByNamedParam(Map.of("username", username)))
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
    }
}
