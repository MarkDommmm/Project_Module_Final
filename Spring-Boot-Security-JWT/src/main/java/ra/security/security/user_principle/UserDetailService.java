package ra.security.security.user_principle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.security.model.domain.Users;
import ra.security.service.IUserService;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userService.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("Username Not Found"));
        return UserPrinciple.build(users);
    }
}
