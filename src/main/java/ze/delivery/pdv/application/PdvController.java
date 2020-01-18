package ze.delivery.pdv.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Point;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ze.delivery.commons.documentation.DefaultResponseDocumentation;
import ze.delivery.pdv.domain.Pdv;
import ze.delivery.pdv.domain.PdvService;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/v1/pdv")
public class PdvController {

    @Autowired
    private PdvService service;

    private PdvRepository repository;

    PdvController(PdvRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @DefaultResponseDocumentation
    @ApiOperation("Get all PDVs")
    public List findAll() {
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    @DefaultResponseDocumentation
    @ApiOperation("Get PDV by id")
    public ResponseEntity findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping(path = {"/{longitude}/{latitude}"})
    @DefaultResponseDocumentation
    @ApiOperation("Get PDV by request position")
    public ResponseEntity findByRequestPosition(@PathVariable String longitude, @PathVariable String latitude) {
        return service.findByPoint(longitude, latitude);
    }

    @PostMapping
    @DefaultResponseDocumentation
    @ApiOperation("Create one PDV")
    public ResponseEntity create(@RequestBody Pdv pdv) {
        return service.save(pdv);
    }

}
