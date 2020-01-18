package ze.delivery.pdv.domain;

import org.springframework.stereotype.Service;
import ze.delivery.pdv.application.PdvRepository;

@Service
public class PdvService {

    private PdvRepository repository;

    PdvService(PdvRepository repository) {
        this.repository = repository;
    }

    public Pdv save(Pdv pdv){
        return repository.save(pdv);
    }
}
