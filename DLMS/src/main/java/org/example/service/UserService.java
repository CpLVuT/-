package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmailService emailService;

    @Autowired
    public UserService(EmailService emailService) {
        this.emailService = emailService;
    }

    public boolean saveUser(User user) {
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    public boolean updateUser(User user) {
        if (user.getPassword() != null) {
            // 加密密码
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return updateById(user);
    }

    public boolean deleteUser(Long id) {
        return removeById(id);
    }

    public User getUserById(Long id) {
        return getById(id);
    }

    public User getUserByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return getOne(queryWrapper);
    }

    public List<User> getAllUsers() {
        return list();
    }

    public boolean loginUser(String email, String rawPassword) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User user = getOne(queryWrapper);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            // 登录成功
            return true;
        } else {
            // 登录失败
            return false;
        }
    }
    public UserDetails loadUserByEmail(String email) {
        User user = getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public void sendVerificationCode(String email) throws MessagingException {
        String code = generateVerificationCode();
        emailService.sendSimpleMessage(email, "Verification Code", "Your verification code is: " + code);
        // 存储验证码以供验证（可以存储在缓存中，示例中省略）
    }

    private String generateVerificationCode() {
        // 生成六位随机验证码
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }


    public Long getUserIdByEmail(String email) {
        User user = getUserByEmail(email);
        return user.getId();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void remindUsersToChangePassword() {
        List<User> users = list();
        for (User user : users) {
            try {
                emailService.sendSimpleMessage(user.getEmail(), "Password Change Reminder", "Please change your password this month.");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
