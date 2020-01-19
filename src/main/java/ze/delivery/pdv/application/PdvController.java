package ze.delivery.pdv.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ze.delivery.commons.documentation.DefaultResponseDocumentation;
import ze.delivery.pdv.domain.Pdv;
import ze.delivery.pdv.domain.PdvService;

@CrossOrigin
@RestController
@RequestMapping("/v1/pdv")
public class PdvController {

    @Autowired
    private PdvService service;

    @GetMapping
    @DefaultResponseDocumentation
    @ApiOperation("Get all PDVs")
    public ResponseEntity findAll() {
        return service.findAll();
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
