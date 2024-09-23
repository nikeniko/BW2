package Back_end.BW2.services;

import Back_end.BW2.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;
    
}
