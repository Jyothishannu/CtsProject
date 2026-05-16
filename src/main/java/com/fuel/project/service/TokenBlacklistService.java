package com.fuel.project.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
	private final Set<String> blacklist =new HashSet<>();
	
	//Add to blacklist
	public void tokenBlacklist(String token) {
		blacklist.add(token);
	}
	
	//check whether the token is in blacklist
	public boolean isBlacklist(String token) {
		return blacklist.contains(token);
	}
	

}
