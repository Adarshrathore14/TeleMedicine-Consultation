package com.telemedicine.authentication.configurations;
import com.telemedicine.authentication.entity.AuthenticationEntity;
import com.telemedicine.authentication.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
public class UserDetailsAuthentication implements UserDetailsService {
	@Autowired
	private AuthenticationRepository authenticationRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<AuthenticationEntity> userInformation;
		userInformation = authenticationRepository.findByUserName(userName);
		if(userInformation.isEmpty()){
			userInformation = authenticationRepository.findByEmail(userName);
		}
		return userInformation.map(UserInfoToUserService::new).orElseThrow(()->new UsernameNotFoundException("User Does Not Exists"));
	}
}