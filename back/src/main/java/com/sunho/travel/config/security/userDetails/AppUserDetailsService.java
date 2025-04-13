package com.sunho.travel.config.security.userDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sunho.travel.domain.user.User;
import com.sunho.travel.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService{

	private final UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//내부적으로 authenticate()가 호출한다.
		User loginedUser = repository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not founded"));
		
		// 데이터가 준비되었으면 이제 UserDetails 그릇 객체를 만들어서 데이터 담아서 반환해주면 끝!!
		AppUserDetails appUserDetails = new AppUserDetails();
		appUserDetails.setId(loginedUser.getId());
		appUserDetails.setUsername(loginedUser.getName());
		appUserDetails.setPassword(loginedUser.getPassword());
        List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + loginedUser.getRole()));
        appUserDetails.setAuthorities(authorities);
		return appUserDetails;
	}
}