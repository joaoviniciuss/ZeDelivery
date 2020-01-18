package ze.delivery.pdv.application;

import io.swagger.annotations.ApiOperation;
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
    public ResponseEntity findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @DefaultResponseDocumentation
    @ApiOperation("Create one PDV")
    public Pdv create(@RequestBody Pdv pdv) {
        return service.save(pdv);
    }
}
