package ze.delivery.pdv.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ze.delivery.pdv.domain.Pdv;

import java.util.UUID;

public interface PdvRepository extends JpaRepository<Pdv, Long> { }
