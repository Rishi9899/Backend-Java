package com.mybootapp.main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybootapp.main.model.Godown;
import com.mybootapp.main.model.Product;
import com.mybootapp.main.repository.GodownRepository;

@Service
public class GodownService {

	@Autowired
	private GodownRepository goDownRepository; 
	
	public Godown insert(Godown godown) {
		 
		return goDownRepository.save(godown);
	}

	public Godown getById(int godownId) {
		Optional<Godown> optional= goDownRepository.findById(godownId);
		if(!optional.isPresent()) {
			return null; 
		}
		return optional.get();
	}

}