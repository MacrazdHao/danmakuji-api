package cc.dmji.api.security;

import cc.dmji.api.entity.User;
import cc.dmji.api.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by echisan on 2018/5/17
 */
public class JwtUser implements UserDetails {

    private Long id;
    private String nick;
    private String pwd;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Byte isLock;
    private Byte isEmailVerify;
    // 单位为分钟
    private Timestamp lockTime;

    public JwtUser(Long id, String nick, String pwd, String email, Collection<? extends GrantedAuthority> authorities, Byte isLock, Byte isEmailVerify) {
        this.id = id;
        this.nick = nick;
        this.pwd = pwd;
        this.email = email;
        this.authorities = authorities;
        this.isLock = isLock;
        this.isEmailVerify = isEmailVerify;
    }

    public JwtUser(User user) {
        id = user.getUserId();
        nick = user.getNick();
        pwd = user.getPwd();
        email = user.getEmail();
        authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        isLock = user.getIsLock();
        isEmailVerify = user.getEmailVerified();
        lockTime = user.getLockTime();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return nick;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 如果已锁定
        if (isLock.equals(UserStatus.LOCK.getStatus())){
            Long lockTimeMillis = lockTime.getTime();
            return System.currentTimeMillis() > lockTimeMillis;
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    public Byte getIsLock() {
        return isLock;
    }

    public Byte getIsEmailVerify() {
        return isEmailVerify;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id='" + id + '\'' +
                ", nick='" + nick + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                ", isLock=" + isLock +
                ", isEmailVerify=" + isEmailVerify +
                '}';
    }
}
