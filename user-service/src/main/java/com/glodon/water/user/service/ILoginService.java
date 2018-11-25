
package com.glodon.water.user.service;

public interface ILoginService {	

	boolean loginAccess(String username, String password, Integer loginTypeEnum);
   
}
