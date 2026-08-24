package com.springboot.scm.services;

import java.util.List;

import com.springboot.scm.entitis.Live;

public interface LiveService {
	
	Live saveLiveUrl(Live live);
	
	List<Live> getLiveUrl();
	
	void deleteLiveUrl(Long id);

}
