package ze.delivery.pdv.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ze.delivery.pdv.domain.Pdv;

import java.util.UUID;

public interface PdvRepository extends JpaRepository<Pdv, UUID> {

    String update = "UPDATE SET coverageArea = GeomFromText(:coverage, 4326), adress = GeomFromText(:address, 4326) WHERE ID = :id";

    @Query(nativeQuery = true, value = update)
    Pdv saveGeometrical(String coverage, String address, Long id);

}
