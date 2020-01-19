package ze.delivery.pdv.domain;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ze.delivery.pdv.application.PdvRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdvService {

    private PdvRepository repository;

    PdvService(PdvRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResponseEntity save(Pdv entity) {
        try {
            if (!entity.containPoint(entity.getAddress()))
                return ResponseEntity.badRequest().build();
            final Pdv savedEntity = repository.save(entity);
            return ResponseEntity.ok().body(savedEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity findById(@PathVariable Long id) {
        try {
            return repository.findById(id)
                    .map(record -> ResponseEntity.ok().body(record))
                    .orElse(ResponseEntity.noContent().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity findAll() {
        try{
            final List<Pdv> allPdv = repository.findAll();
            return allPdv.size() > 0 ? ResponseEntity.ok().body(allPdv) : ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity findByPoint(String lgt, String lat) {

        try {
            if (null == lgt || lgt.isEmpty() || null == lat || lat.isEmpty())
                return ResponseEntity.badRequest().build();

            GeometryFactory gf = new GeometryFactory();
            Point point = gf.createPoint(new Coordinate(Double.parseDouble(lgt.trim()), Double.parseDouble(lat.trim())));
            List<Pdv> pdvsWithPoint = repository.findAll().stream().filter(pdv -> pdv.containPoint(point)).collect(Collectors.toList());

            Pdv pdvToReturn = null;
            Double minorDistance = null;

            if (pdvsWithPoint.size() == 1)
                return ResponseEntity.ok().body(pdvsWithPoint.get(0));
            else if (pdvsWithPoint.size() == 0)
                return ResponseEntity.noContent().build();

            for (Pdv pdv : pdvsWithPoint) {

                Double distance = pdv.getDistanceToPoint(point);

                if (null == minorDistance || distance.compareTo(minorDistance) < 0) {
                    minorDistance = distance;
                    pdvToReturn = pdv;
                }
            }

            return ResponseEntity.ok().body(pdvToReturn);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
