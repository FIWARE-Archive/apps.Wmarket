package org.fiware.apps.marketplace.bo.impl;

/*
 * #%L
 * FiwareMarketplace
 * %%
 * Copyright (C) 2012 SAP
 * Copyright (C) 2014 CoNWeT Lab, Universidad Politécnica de Madrid
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of copyright holders nor the names of its contributors
 *    may be used to endorse or promote products derived from this software 
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

import java.util.List;

import org.fiware.apps.marketplace.bo.UserBo;
import org.fiware.apps.marketplace.dao.UserDao;
import org.fiware.apps.marketplace.exceptions.UserNotFoundException;
import org.fiware.apps.marketplace.model.User;
import org.fiware.apps.marketplace.utils.NameGenerator;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userBo")
public class UserBoImpl implements UserBo {

	@Autowired
	private UserDao userDao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserBoImpl.class);
	
	public void setStoreDao (UserDao localuser){
		this.userDao = localuser;
	}
	
	@Override
	@Transactional(readOnly=false)
	public void save(User localuser) {
		// Some authentication methods require their own user name
		if (localuser.getUserName() == null) {
			localuser.setUserName(NameGenerator.getURLName(localuser.getDisplayName()));
		}
		userDao.save(localuser);
	}

	@Override
	@Transactional(readOnly=false)
	public void update(User localuser) {
		userDao.update(localuser);
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(User localuser) {
		userDao.delete(localuser);
	}

	@Override
	@Transactional
	public User findByName(String username) throws UserNotFoundException {
		return userDao.findByName(username);
	}
	
	@Override
	@Transactional
	public List<User> getUsersPage(int offset, int max) {
		return userDao.getUsersPage(offset, max);
	}

	@Override
	@Transactional
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public User getCurrentUser() throws UserNotFoundException {
		String userName;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// When OAuth2 is being used, we should cast the authentication to read the correct user name
		if (authentication instanceof ClientAuthenticationToken) {
			userName = ((ClientAuthenticationToken) authentication).getUserProfile().getId();
		} else {
			userName = authentication.getName();
		}
		
		logger.warn("User: {}", userName);
		return this.findByName(userName);
	}

}
