package com.mobilpack.manager.Dao;

import com.mobilpack.manager.Model.AdminModel;

public interface Dao {
	public AdminModel LoginQuery(String id, String pw); 
}
	